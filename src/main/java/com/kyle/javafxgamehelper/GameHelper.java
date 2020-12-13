package com.kyle.javafxgamehelper;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/*
 *  Class:      GameHelper
 *  Author:     Kyle
 *  Desc:       Handles sprite rendering, etc.
 *              with only a few method calls,
 *              used to build canvas-based games
 *              more easily in JavaFX
 *  Instructions:
 *              1.  import com.kyle.javafxgamehelper
 *              2.  create a subclass of GameHelper
 *              3.  Define sprites and assets in the game
 *              4.  setGameData() with appropriate parameters
 *                  and call launch(args) in main()
 */
public class GameHelper extends Application {

    public static Object gameUpdateObject;
    public static GameApp game;

    //  Game data fields
    private static String canvasPath;
    private static String windowTitle;
    private static int screenWidth;
    private static int screenHeight;
    private static String[] sprites;
    private static GameAsset[] assets;

    /*
     *  Method:     setGameData
     *  Author:     Kyle
     *  Desc:       set game data required to run canvas game
     *  Params:     String canvasPath - the path to the canvas file
     *                  (/src/main/resources/game-canvas/index.html)
     *              String windowTitle - the title of the JavaFX app window
     *              int screenWidth,
     *              int screenHeight - the dimensions of the JavaFX window
     *                  (must fit canvas!)
     *              String[] sprites - the starting sprites used in the game
     *              GameAsset[] assets - the starting assets
     *              Object gameUpdateObject - an object sent to each assets'
     *              update() method containing additional game data
     */
    public static void setGameData(String canvasPath,
                                   String windowTitle, int screenWidth, int screenHeight,
                                   String[] sprites, GameAsset[] assets,
                                   Object gameUpdateObject) {
        GameHelper.canvasPath = canvasPath;
        GameHelper.windowTitle = windowTitle;
        GameHelper.screenWidth = screenWidth;
        GameHelper.screenHeight = screenHeight;
        GameHelper.sprites = sprites;
        GameHelper.assets = assets;
        GameHelper.gameUpdateObject = gameUpdateObject;
    }// End of setGameData()

    /*
     *  Method:     start
     *  Desc:       start() method implemented in javafx.application.Application
     *              sets up the JavaFX window for canvas game,
     *              attaches starting sprites/assets and
     *              begins the game
     */
    @Override
    public void start(Stage stage) {
        game = new GameApp(windowTitle, screenWidth, screenHeight);
        game.loadGameCanvas(canvasPath, stage, gameUpdateObject);
        game.addSprites(sprites);
        game.addAssets(assets);
        stage.show();
        game.beginGame();
    }// End of start()

    /*
     *  Method:     stop
     *  Desc:       stop() method implemented in javafx.application.Application
     *              makes sure all loops are stopped
     */
    @Override
    public void stop() throws Exception {
        game.endGame();
    }// End of stop()

    /*
     *  Class:      GameApp
     *  Author:     Kyle
     *  Desc:       Contains data for handling JavaFX canvas window
     *  Type Parameter:
     *              The object used in handling asset updates
     */
    public static class GameApp {

        /*
         *  Constructor Method
         *  Parameters: String windowTitle - the title of the JavaFX app window
         *              int screenWidth,
         *              int screenHeight - the dimensions of the window
         *                  (must fit canvas!)
         */
        public GameApp(String windowTitle, int screenWidth, int screenHeight) {
            title = windowTitle;
            SCREEN_WIDTH = screenWidth;
            SCREEN_HEIGHT = screenHeight;
        }// End of constructor method

        /*
         *  Method:     setFixedRate
         *  Author:     Kyle
         *  Desc:       set fixed rate param
         *              (default is 35ms)
         *  Returns:    void
         */
        public void setFixedRate(int newRate) {
            fixedRate = newRate;
        }// End of setFixedRate()

        //  Canvas window data
        public final String title;
        public final int SCREEN_WIDTH;
        public final int SCREEN_HEIGHT;
        public int fixedRate = 35;

        //  Object fields
        public final WebView appWindow = new WebView();
        public final WebEngine gameWindow = appWindow.getEngine();
        public final StackPane display = new StackPane();
        public Scene scene;
        public Stage stage;

        public Timer updateLoop;
        public GameHandler game;

        /*
         *  Method:     loadGameCanvas
         *  Author:     Kyle
         *  Desc:       load the pre-defined canvas
         *              script from the canvas file
         *              (/src/main/resources/game-canvas/index.html)
         *  Params:     String file - path to file from cwd
         *              Stage stage - javafx.stage.Stage passed to start() method
         *              HandlerUpdateType updateData - the object used to
         *              handle asset updates
         */
        public void loadGameCanvas(String file, Stage stage, Object updateData) {
            //  Get full path to load from
            final File index = new File(file);
            final String index_path = "file:///" + index.getAbsolutePath();

            //  Load file and attach to JavaFX app
            stage.setTitle(title);
            appWindow.setContextMenuEnabled(false);
            gameWindow.load(index_path);
            display.getChildren().add(appWindow);
            display.setStyle("-fx-padding: 0px;");
            scene = new Scene(display, SCREEN_WIDTH, SCREEN_HEIGHT);
            stage.setScene(scene);

            //  Instantiate GameHandler and attach update loop object
            game = new GameHandler(updateData);

            //  Attach GameHandler to JavaFX so that script can read assets
            gameWindow.getLoadWorker().stateProperty().addListener(
                    (observableValue, state, t1) -> {
                        if(t1 != Worker.State.SUCCEEDED) {
                            return;
                        }

                        JSObject window = (JSObject) gameWindow.executeScript("window");
                        window.setMember("mainHandler", game);
                    }
            );

            //  Attach key events to AssetHandler so that it can redirect them
            //  to the appropriate assets
            stage.addEventHandler(KeyEvent.ANY, game::handleAssets);
            this.stage = stage;
        }// End of loadGameCanvas()

        /*
         *  Method:     addSprites
         *  Author:     Kyle
         *  Desc:       attach starting sprites to game
         *  Params:     String[] sprites - contains names of sprite files
         */
        public void addSprites(String[] sprites) {
            for(String sprite : sprites) {
                game.addSprite(sprite);
            }
        }// End of addSprites()

        /*
         *  Method:     addAssets
         *  Author:     Kyle
         *  Desc:       attach starting assets to game
         *  Params:     GameAsset[] assets - the starting assets (in drawing order)
         */
        public void addAssets(GameAsset[] assets) {
            game.setAssets(assets);
        }// End of addAssets()

        /*
         *  Method:     beginGame()
         *  Author:     Kyle
         *  Desc:       load all assets into script and start game loops
         */
        public void beginGame() {
            //  Call functions in script
            gameWindow.getLoadWorker().stateProperty().addListener(
                    (observableValue, state, t1) -> {
                        if(t1 != Worker.State.SUCCEEDED) {
                            return;
                        }

                        gameWindow.executeScript("loadSprites()");
                        gameWindow.executeScript("startLoop()");
                    }
            );

            //  Set loop to update assets
            updateLoop = new Timer();
            TimerTask updateTask = new TimerTask() {

                @Override
                public void run() {
                    try {
                        game.updateAssets();
                    } catch (Exception ignored) {}
                }
            };
            updateLoop.scheduleAtFixedRate(updateTask, fixedRate, fixedRate);
        }// End of beginGame()

        /*
         *  Method:     endGame()
         *  Author:     Kyle
         *  Desc:       called to stop game loops (when game ends)
         */
        public void endGame() throws InterruptedException {
            //  Cancel asset update loop
            updateLoop.cancel();
        }// End of endgame()
    }// End of GameApp class
}// End of GameHelper class
