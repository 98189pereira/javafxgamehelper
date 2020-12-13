package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameAsset;
import com.kyle.javafxgamehelper.GameHelper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/*
 *  Class:      DemoGame
 *  Author:     Kyle
 *  Desc:       demonstration of a canvas-based JavaFX
 *              game made using
 *              com.kyle.javafxgamehelper.GameHelper
 */
public class DemoGame extends GameHelper {

    //  The starting sprites used in the game
    static final String[] gameSprites = {
            "enemy.png",
            "player.png",
            "player_l.png",
            "player_r.png",
            "cloud_1.png",
            "cloud_2.png",
            "cloud_3.png",
            "ground.png",
            "sky.png",
            "gameover.png"
    };

    //  The starting assets used in the game
    //  (in canvas drawing order)
    static final GameAsset[] gameAssets = {
            new DemoSkyAsset("sky"),
            new DemoGroundAsset("ground"),
            new DemoCloudSpawner("cloud spawner"),
            new DemoEnemySpawner("enemy spawner"),
            new DemoPlayerAsset("player", 200, 645)
    };

    //  An optional object to send additional
    //  game data to each assets' update() method
    static final DemoUpdateData gameUpdateData = new DemoUpdateData(720, 720);

    //  set the game's data and start the JavaFX application
    public static void main(String ... args) {
        setGameData("src/main/resources/index.html",
                "Demo Game", 720, 720,
                gameSprites, gameAssets, gameUpdateData);
        launch(args);
    }// End of main()

    /*
     *  Method:     pauseGame
     *  Author:     Kyle
     *  Desc:       pause the game and wait for
     *              the user to continue
     */
    public static void pauseGame() throws InterruptedException {
        game.endGame();
        Thread.sleep(1000);
        game.stage.addEventHandler(KeyEvent.KEY_PRESSED, waitForKey);
    }// End of pauseGame()

    /*
     *  EventHandler    waitForKey
     *  Desc:           if any key is pressed, reset the game and
     *                  start over
     */
    static EventHandler<KeyEvent> waitForKey = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
            game.beginGame();
            game.handler.removeAsset("game over");
            DefaultAsset player = (DefaultAsset) gameAssets[gameAssets.length - 1];
            player.setxPos(200);
            player.setyPos(645);
            game.handler.setAssets(gameAssets);
            game.stage.removeEventHandler(KeyEvent.KEY_PRESSED, this);
        }
    };
}// End DemoGame class
