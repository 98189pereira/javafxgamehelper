package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

/*
 *  Class:      DisplayManager
 *  Author:     Kyle
 */
public class DisplayManager extends DefaultAsset {

    public DisplayManager(String name) {
        super(new String[]{""}, name);
    }

    protected static final double SCORE_DIGIT_SIZE = 64.0;
    protected static final double DISPLAY_TEXT_LOCATION = 32.0;
    protected static final double DISPLAY_TEXT_OFFSET = 32.0;

    protected DisplayScore[] displayScores = new DisplayScore[3];
    protected DefaultAsset[] displayLives;

    protected AssetData assetData = null;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(assetData == null) {
            //  first time set up displays
            assetData = (AssetData) data;
            DefaultAsset lives = new DefaultAsset(
                    new String[]{"lives.png"},
                    "lives"
            );
            lives.setxPos(DISPLAY_TEXT_OFFSET);
            lives.setyPos(DISPLAY_TEXT_LOCATION);
            handler.addAsset(lives);
            displayLives = new DefaultAsset[assetData.getLives()];
            for(int i = 0; i < displayLives.length; ++i) {
                displayLives[i] = new DefaultAsset(
                        new String[]{"heart.png"},
                        "livescounter" + i
                );
                displayLives[i].setxPos(SCORE_DIGIT_SIZE * (displayScores.length - i) + DISPLAY_TEXT_OFFSET);
                displayLives[i].setyPos(SCORE_DIGIT_SIZE);
                handler.addAsset(displayLives[i]);
            }
            for(int i = 0; i < displayScores.length; ++i) {
                displayScores[i] = new DisplayScore(
                        "scoredigit" + i,
                        SCORE_DIGIT_SIZE * (displayScores.length - i) + DISPLAY_TEXT_OFFSET,
                        0
                );
                handler.addAsset(displayScores[i]);
            }
        }// end conditional
        //  continually update
        if(assetData.getLives() < displayLives.length) {
            handler.removeAsset(displayLives[displayLives.length - assetData.getLives() - 1].getName());
        }
        displayScores[0].setSpriteNo(Math.floorDiv(assetData.getScore()%10,1));
        displayScores[1].setSpriteNo(Math.floorDiv(assetData.getScore()%100,10));
        displayScores[2].setSpriteNo(Math.floorDiv(assetData.getScore()%1000,100));
    }// End of update()

    //  reset displays if game restarts
    public void resetDisplay() {
        assetData = null;
    }
}// End of DisplayManager class
