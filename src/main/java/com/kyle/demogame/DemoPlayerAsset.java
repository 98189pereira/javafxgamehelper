package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;
import javafx.scene.input.KeyEvent;

/*
 *  Class:      DemoPlayerAsset
 *  Author:     Kyle
 *  Desc:       player
 */
public class DemoPlayerAsset extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     *              double xPos,
     *              double yPos - spawn location
     */
    public DemoPlayerAsset(String name, double xPos, double yPos) {
        super(new String[] {
                "player.png",
                "player_l.png",
                "player_r.png"
        }, name);
        this.xPos = xPos;
        this.yPos = yPos;
    }// End constructor method

    protected static final double GROUND_HEIGHT = 25.0;
    protected static final double SIZE = 50.0;
    protected static final double GRAVITY = 1.5;
    protected static final double MOVEMENT_SPEED = 15.0;
    protected static final double DASH_DISTANCE = 250.0;
    protected static final double DASH_TIMEOUT = 250.0;
    protected static final double JUMP_POWER = -25.0;
    protected static final double EXTRA_GRAVITY = 3.0;

    protected double FLOOR;
    protected double velocity = 0.0;
    protected final long[] lastKeyPressed = {0, 0};
    protected final boolean[] keysPressed = {false, false, false};
    protected final boolean[] dashReady = {false, false, false};

    @Override
    public void keyEventHandler(KeyEvent keyEvent) {
        if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            long keyPressed;
            switch (keyEvent.getCode()) {
                case RIGHT:
                    keysPressed[0] = true;
                    keyPressed = System.currentTimeMillis();
                    if (keyPressed - lastKeyPressed[0] > DASH_TIMEOUT) {
                        lastKeyPressed[0] = keyPressed;
                        dashReady[0] = false;
                    }
                    else if(dashReady[0]) {
                        xPos += DASH_DISTANCE;
                        lastKeyPressed[0] -= DASH_TIMEOUT;
                        if(velocity > GRAVITY)
                            velocity = GRAVITY;
                    }
                    break;
                case LEFT:
                    keysPressed[1] = true;
                    keyPressed = System.currentTimeMillis();
                    if (keyPressed - lastKeyPressed[1] > DASH_TIMEOUT) {
                        lastKeyPressed[1] = keyPressed;
                        dashReady[1] = false;
                    }
                    else if(dashReady[1]) {
                        xPos -= DASH_DISTANCE;
                        lastKeyPressed[1] -= DASH_TIMEOUT;
                        if(velocity > GRAVITY)
                            velocity = GRAVITY;
                    }
                    break;
                case UP:
                    if(yPos == FLOOR)
                        velocity = JUMP_POWER;
                    if(dashReady[2]) {
                        velocity = JUMP_POWER;
                        dashReady[2] = false;
                    }
                    break;
                case DOWN:
                    keysPressed[2] = true;
                    break;
            }
        } else if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyEvent.getCode()) {
                case RIGHT:
                    keysPressed[0] = false;
                    dashReady[0] = true;
                    break;
                case LEFT:
                    keysPressed[1] = false;
                    dashReady[1] = true;
                    break;
                case UP:
                    dashReady[2] = true;
                    break;
                case DOWN:
                    keysPressed[2] = false;
            }
        }
    }

    private DemoUpdateData updateData = null;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(updateData == null) {
            updateData = (DemoUpdateData) data;
            updateData.setPlayerSize(SIZE);
            FLOOR = updateData.getScreenYBoundary() - SIZE - GROUND_HEIGHT;
        }
        velocity += GRAVITY;
        if(keysPressed[2])
            velocity += EXTRA_GRAVITY;
        yPos += velocity;
        if(yPos > FLOOR) {
            velocity = 0;
            yPos = FLOOR;
        }
        if(keysPressed[0])
            xPos += MOVEMENT_SPEED;
        if(keysPressed[1])
            xPos -= MOVEMENT_SPEED;
        if(xPos < 0)
            xPos = 0;
        if(xPos > updateData.getScreenXBoundary() - SIZE)
            xPos = updateData.getScreenXBoundary() - SIZE;
        updateData.setPlayerWorldHeight(yPos);
        updateData.setPlayerPosition(new double[]{xPos, yPos});
    }

    @Override
    public String getSprite() {
        String sprite = sprites[0];
        if(keysPressed[0])
            sprite = sprites[2];
        if(keysPressed[1])
            sprite = sprites[1];
        if(keysPressed[0] && keysPressed[1])
            sprite = sprites[0];
        return sprite;
    }

    @Override
    public double[] getPosition() {
        double yHeight = yPos;
        if(yHeight < updateData.getScreenYBoundary() / 2)
            yHeight = updateData.getScreenYBoundary() / 2;
        return new double[]{
                xPos + updateData.getGlobalScreenXOffset(),
                yHeight + updateData.getGlobalScreenYOffset(),
                SIZE,
                SIZE
        };
    }
}// End DemoPlayerAsset class
