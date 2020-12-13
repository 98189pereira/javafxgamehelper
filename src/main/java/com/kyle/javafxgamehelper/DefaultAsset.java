package com.kyle.javafxgamehelper;

/*
 *  Class:      DefaultAsset
 *  Author:     Kyle
 *  Desc:       Class for a simple asset
 */
public class DefaultAsset implements GameAsset {

    /*
     *  Constructor method
     *  Parameters: String[] sprites - the sprites this asset uses
     *              String name - the name of this asset (unique)
     */
    public DefaultAsset(String[] sprites, String name) {
        this.sprites = sprites;
        this.name = name;
    }// End of constructor method

    protected final String[] sprites;
    protected final String name;

    protected double xPos = 0;
    protected double yPos = 0;

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    @Override
    public double[] getPosition() {
        return new double[]{xPos, yPos};
    }

    @Override
    public String getSprite() {
        return sprites[0];
    }

    @Override
    public String getName() {
        return name;
    }
}
