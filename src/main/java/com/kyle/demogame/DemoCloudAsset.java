package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      DemoCloudAsset
 *  Author:     Kyle
 *  Desc:       cloud
 */
public class DemoCloudAsset extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoCloudAsset(String name) {
        super(new String[] {
                "cloud_1.png",
                "cloud_2.png",
                "cloud_3.png"
        }, name);
    }// End constructor method

    protected static final Random random = new Random();

    protected static final double MIN_HEIGHT = 300;

    protected final int CLOUD_NO = random.nextInt(3);
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
    }

    @Override
    public String getSprite() {
        return sprites[CLOUD_NO];
    }

    @Override
    public double[] getPosition() {
        return new double[]{
                xPos + updateData.getGlobalScreenXOffset(),
                yPos + updateData.getGlobalScreenYOffset()
        };
    }
}// End DemoCloudAsset class
