package com.kyle.demogame;

/*
 *  Class:      DemoUpdateData
 *  Author:     Kyle
 *  Desc:       contains additional data for update() methods to use
 */
public class DemoUpdateData {

    /*
     *  Constructor method
     *  Parameters: double screenXBoundary,
     *              double screenYBoundary - the canvas dimensions (egdes)
     */
    public DemoUpdateData(double screenXBoundary, double screenYBoundary) {
        this.screenXBoundary = screenXBoundary;
        this.screenYBoundary = screenYBoundary;
    }// End constructor method

    public double getScreenXBoundary() {
        return screenXBoundary;
    }

    public double getScreenYBoundary() {
        return screenYBoundary;
    }

    private final double screenXBoundary;
    private final double screenYBoundary;

    public double getGlobalScreenXOffset() {
        return globalScreenXOffset;
    }

    public void setGlobalScreenXOffset(double globalScreenXOffset) {
        this.globalScreenXOffset = globalScreenXOffset;
    }

    public double getGlobalScreenYOffset() {
        return globalScreenYOffset;
    }

    public void setGlobalScreenYOffset(double globalScreenYOffset) {
        this.globalScreenYOffset = globalScreenYOffset;
    }

    private double globalScreenXOffset = 0.0;
    private double globalScreenYOffset = 0.0;

    public double getPlayerWorldHeight() {
        return playerWorldHeight;
    }

    public void setPlayerWorldHeight(double playerWorldHeight) {
        this.playerWorldHeight = playerWorldHeight;
    }

    private double playerWorldHeight;

    public double[] getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(double[] playerPosition) {
        this.playerPosition = playerPosition;
    }

    private double[] playerPosition;

    public double getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(double playerSize) {
        this.playerSize = playerSize;
    }

    private double playerSize;
}// End DemoUpdateData class
