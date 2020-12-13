package com.kyle.demogame;

import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      DemoEnemyAsset
 *  Author:     Kyle
 *  Desc:       enemy
 */
public class DemoEnemyAsset extends DemoCloudAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoEnemyAsset(String name) {
        super(name);
        MIN_HEIGHT = 100;
        SIZE = 50;
    }// End constructor method

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        super.update(data, handler);
        if(touchingPlayer()) {
            handler.removeAsset(name);
            handler.addAsset(new DemoGameOver("game over"));
            DemoGame.pauseGame();
        }
    }

    private boolean touchingPlayer() {
        double[] playerPosition = updateData.getPlayerPosition();
        double playerSize = updateData.getPlayerSize();
        double playerX = playerPosition[0],
                playerY = playerPosition[1];
        boolean topLeft = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX += playerSize;
        boolean topRight = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX -= playerSize;
        playerY += playerSize;
        boolean bottomLeft = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX += playerSize;
        boolean bottomRight = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        return topLeft || topRight || bottomLeft || bottomRight;
    }

    @Override
    public String getSprite() {
        return "enemy.png";
    }
}// End DemoEnemyAsset class
