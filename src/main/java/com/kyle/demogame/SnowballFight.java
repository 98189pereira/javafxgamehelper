package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameAsset;
import com.kyle.javafxgamehelper.GameHelper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/*
 *  Class:      SnowballFight
 *  Author:     Kyle
 *  Desc:       demonstration of a canvas-based JavaFX
 *              game made using
 *              com.kyle.javafxgamehelper.GameHelper
 */
public class SnowballFight extends GameHelper {

    //  All the sprites used in the game
    public static final String[] gameSprites = {
            "bg.png", "player_r-1.png", "player_r0.png", "player_r1.png", "player_r2.png", "player_l-1.png",
            "player_l0.png", "player_l1.png", "player_l2.png", "can_l.png", "can_r.png", "can_lt.png",
            "can_rt.png", "bot_l.png", "bot_r.png", "bot_lt.png", "bot_rt.png", "ball.png", "lives.png",
            "heart.png", "0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png",
            "8.png", "9.png", "paused.png", "gameover.png", "particle.png"
    };

    //  The starting assets in the game
    public static final GameAsset[] gameAssets = {
            new DefaultAsset(new String[]{"bg.png"},"bg"),
            new Player(
                    new String[]{
                            "player_r-1.png",
                            "player_r0.png",
                            "player_r1.png",
                            "player_r2.png",
                            "player_l-1.png",
                            "player_l0.png",
                            "player_l1.png",
                            "player_l2.png"
                    },
                    "test asset"
            ),
            new DisplayManager("score display"),
            new BoxSpawner("spawner")
    };

    //  Additional data for processing asset updates
    public static final AssetData assetData = new AssetData();

    //  All that is required to start the game
    public static void main(String ... args) {
        setGameData("src/main/resources/index.html",
                "Snowball fight!", 960, 720,
                gameSprites, gameAssets, assetData);

        launch(args);
    }

    //  Pause the game or end the game
    public static void changeGameState(boolean paused) throws InterruptedException {
        game.endGame();
        Thread.sleep(1000);
        if(paused)
            game.stage.addEventHandler(KeyEvent.KEY_PRESSED, unpause);
        else
            game.stage.addEventHandler(KeyEvent.KEY_PRESSED, playAgain);
    }

    //  Restart game loops
    static EventHandler<KeyEvent> unpause = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
            game.beginGame();
            game.handler.removeAsset("game paused");
            assetData.getPlayer().canPause = true;
            game.stage.removeEventHandler(KeyEvent.KEY_PRESSED, this);
        }
    };

    //  Reset game data, then restart game loops
    static EventHandler<KeyEvent> playAgain = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent keyEvent) {
            game.beginGame();
            DefaultAsset player = (DefaultAsset) gameAssets[1];
            player.setxPos(0);
            player.setyPos(590);
            DisplayManager manager = (DisplayManager) gameAssets[2];
            manager.resetDisplay();
            assetData.resetGame();
            game.handler.setAssets(gameAssets);
            game.stage.removeEventHandler(KeyEvent.KEY_PRESSED, this);
        }
    };
}// End of SnowballFight main class
