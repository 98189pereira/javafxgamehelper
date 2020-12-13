package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;

/*
 *  Class:      DemoGroundAsset
 *  Author:     Kyle
 *  Desc:       sky
 */
public class DemoSkyAsset extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoSkyAsset(String name) {
        super(new String[]{"sky.png"}, name);
    }// End constructor method

    protected static final double WIDTH = 720;
    protected static final double HEIGHT = 720;

    @Override
    public double[] getPosition() {
        return new double[]{
                xPos,
                yPos,
                WIDTH,
                HEIGHT
        };
    }
}// End DemoSkyAsset class
