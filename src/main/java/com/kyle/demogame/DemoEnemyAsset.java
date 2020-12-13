package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      DemoEnemyAsset
 *  Author:     Kyle
 *  Desc:       enemy
 */
public class DemoEnemyAsset extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoEnemyAsset(String name) {
        super(new String[]{"enemy.png"}, name);
    }// End constructor method

    protected static final Random random = new Random();

    protected static final double MIN_HEIGHT = 100;
    protected static final double SIZE = 75.0;

    protected final double MOVEMENT_SPEED = random.nextDouble() * 5.0 + 0.5;
    protected final int MOVEMENT_DIRECTION = ( random.nextBoolean() ) ? 1 : -1;

    protected double yHeight;

    private DemoUpdateData updateData = null;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(updateData == null) {
            updateData = (DemoUpdateData) data;
            if(MOVEMENT_DIRECTION == 1)
                xPos = 0;
            else
                xPos = updateData.getScreenXBoundary();
            yHeight = random.nextDouble() *
                    ( updateData.getScreenYBoundary() +
                            updateData.getPlayerWorldHeight() -
                            MIN_HEIGHT -
                            updateData.getScreenYBoundary()
                    );
        }
        if(updateData.getPlayerWorldHeight() < updateData.getScreenYBoundary() / 2)
            yPos = yHeight - updateData.getPlayerWorldHeight() + updateData.getScreenYBoundary() / 2;
        else
            yPos = yHeight;
        xPos += MOVEMENT_SPEED * MOVEMENT_DIRECTION;
        if(xPos < 0 || xPos > updateData.getScreenXBoundary())
            handler.removeAsset(name);
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
        boolean topRight = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX += playerSize;
        boolean topLeft = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX -= playerSize;
        playerY += playerSize;
        boolean bottomRight = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        playerX += playerSize;
        boolean bottomLeft = (playerX > xPos && playerX < xPos + SIZE) &&
                (playerY > yPos && playerY < yPos + SIZE);
        return topRight || topLeft || bottomRight || bottomLeft;
    }

    @Override
    public String getSprite() {
        return sprites[0];
    }

    @Override
    public double[] getPosition() {
        return new double[]{
                xPos + updateData.getGlobalScreenXOffset(),
                yPos + updateData.getGlobalScreenYOffset(),
                SIZE,
                SIZE
        };
    }
}// End DemoEnemyAsset class
