package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

/*
 *  Class:      DemoGroundAsset
 *  Author:     Kyle
 *  Desc:       ground
 */
public class DemoGroundAsset extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoGroundAsset(String name) {
        super(new String[]{"ground.png"}, name);
    }// End constructor method

    protected static final double HEIGHT = 100;

    protected DemoUpdateData updateData = null;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(updateData == null) {
            updateData = (DemoUpdateData) data;
            yPos = updateData.getScreenYBoundary() - HEIGHT;
        }
    }

    @Override
    public double[] getPosition() {
        if(updateData.getScreenYBoundary() / 2 - updateData.getPlayerWorldHeight() > 50)
            return new double[] {xPos, 800};
        if(updateData.getPlayerWorldHeight() < updateData.getScreenYBoundary() / 2)
            return new double[] {
                    xPos, yPos - updateData.getPlayerWorldHeight() + updateData.getScreenYBoundary() / 2
            };
        return new double[] {
                xPos,
                yPos
        };
    }
}// End DemoGroundAsset class
