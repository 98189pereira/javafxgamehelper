package com.kyle.demogame;

import com.kyle.javafxgamehelper.GameHandler;

/*
 *  Class:      TrashCan
 *  Author:     Kyle
 *  Desc:       enemy subclass
 */
public class TrashCan extends Robot {

    public TrashCan(String[] sprites, String name) {
        super(sprites, name);
        SPAWN_LEFT = 200;
        SPAWN_RIGHT = 200;
        SPAWN_TOP = 500;
        SPAWN_BOTTOM = 100;
        MOVE_INTERVAL = 2500;
        MOVE_SPEED = 2.5;
        HB_TOP = -10;
        HB_BOTTOM = 60;
        HB_LEFT = 60;
        HB_RIGHT = -10;
        HIDE_SHOW_TIMEOUT = 1000;
    }

    protected double TOP_BOUNDARY = 500;
    protected double BOTTOM_BOUNDARY = 100;

    protected double FLOAT_SPEED = 1.0;
    protected int bobbing = 1;
    protected long lastBob = System.currentTimeMillis();
    protected int bobInterval = 1000;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        //  float up and down at intervals
        super.update(data, handler);
        if(System.currentTimeMillis() - lastBob > bobInterval) {
            bobbing *= -1;
            lastBob = System.currentTimeMillis();
        }
        yPos += FLOAT_SPEED * bobbing;
        if(yPos < TOP_BOUNDARY)
            bobbing = 1;
        if(yPos > handler.SCREEN_HEIGHT - BOTTOM_BOUNDARY)
            bobbing = -1;
    }// End of update()
}// End of TrashCan class
