package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

/*
 *  Class:      Snowball
 *  Author:     Kyle
 */
public class Snowball extends DefaultAsset {

    public Snowball(String[] sprites, String name,
                    double spawnX, double spawnY,
                    int direction, double yDrift) {
        super(sprites, name);
        xPos = spawnX;
        yPos = spawnY;
        DIRECTION = direction;
        DRIFT = yDrift;
    }

    public static final double RECOIL = 5.0;

    protected int DIRECTION;
    protected double DRIFT;
    protected double MOVE_SPEED = 45.0;

    protected AssetData assetData = null;

    @Override
    public void update(Object data, GameHandler handler) {
        if(assetData == null) {
            assetData = (AssetData) data;
            assetData.addProjectile(this);
        }
        xPos += DIRECTION * MOVE_SPEED;
        yPos += DRIFT;
        if(xPos > handler.SCREEN_WIDTH || xPos < 0 ||
                yPos > handler.SCREEN_HEIGHT || yPos < 0) {
            assetData.removeProjectile(this);
            handler.removeAsset(name);
        }
    }// End of update()
}// End of Snowball class
