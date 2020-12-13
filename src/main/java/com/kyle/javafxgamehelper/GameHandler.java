package com.kyle.javafxgamehelper;

import javafx.scene.input.KeyEvent;

/*
 *  Class:      GameHandler
 *  Author:     Kyle
 *  Desc:       Handles assets and game events
 *  Type Parameter:
 *              The object used in handling asset updates
 */
public class GameHandler {

    /*
     *  Constructor method
     *  Parameters: UpdateDataType updateData - the object used to
     *              handle asset updates
     *              double screen_width,
     *              double screen_height - canvas dimensions
     */
    public GameHandler(Object updateData, double screen_width, double screen_height) {
        this.updateData = updateData;
        SCREEN_WIDTH = screen_width;
        SCREEN_HEIGHT = screen_height;
    }// End of constructor method

    final Object updateData;

    public final double SCREEN_WIDTH;
    public final double SCREEN_HEIGHT;

    String[] sprites = new String[0];
    GameAsset[] assets = new GameAsset[0];

    public void addSprite(String sprite) {
        String[] newSprites = new String[sprites.length + 1];
        System.arraycopy(sprites, 0, newSprites, 0, sprites.length);
        newSprites[newSprites.length - 1] = sprite;
        sprites = newSprites;
    }

    public void removeSprite(String sprite) {
        int pos = -1;
        for(int i = 0; i < sprites.length; ++i) {
            if(sprites[i].equals(sprite)) {
                pos = i;
                break;
            }
        }
        if(pos > -1) {
            String[] newSprites = new String[sprites.length - 1];
            System.arraycopy(sprites, 0, newSprites, 0, pos);
            System.arraycopy(sprites, pos + 1, newSprites, pos, newSprites.length - pos);
            sprites = newSprites;
        }
    }

    public void addAsset(GameAsset asset) {
        GameAsset[] newAssets = new GameAsset[assets.length + 1];
        System.arraycopy(assets, 0, newAssets, 0, assets.length);
        newAssets[newAssets.length - 1] = asset;
        assets = newAssets;
    }

    public void addAsset(GameAsset asset, int index) {
        GameAsset[] newAssets = new GameAsset[assets.length + 1];
        System.arraycopy(assets, 0, newAssets, 0, index);
        newAssets[index] = asset;
        System.arraycopy(assets, index, newAssets, index + 1, assets.length - index);
        assets = newAssets;
    }

    public void removeAsset(String name) {
        int pos = -1;
        for(int i = 0; i < assets.length; ++i) {
            if(assets[i].getName().equals(name)) {
                pos = i;
                break;
            }
        }
        if(pos > -1) {
            GameAsset[] newAssets = new GameAsset[assets.length - 1];
            System.arraycopy(assets, 0, newAssets, 0, pos);
            System.arraycopy(assets, pos + 1, newAssets, pos, newAssets.length - pos);
            assets = newAssets;
        }
    }

    public void setAssets(GameAsset[] assets) {
        this.assets = new GameAsset[assets.length];
        System.arraycopy(assets, 0, this.assets, 0, assets.length);
    }

    public void updateAssets() throws InterruptedException {
        for(GameAsset asset : assets) {
            asset.update(updateData, this);
        }
    }

    public void handleAssets(KeyEvent keyEvent) {
        for(GameAsset asset : assets) {
            asset.keyEventHandler(keyEvent);
        }
    }

    public int getNoSprites() {
        return sprites.length;
    }

    public char[] getSpriteName(int index) {
        return sprites[index].toCharArray();
    }

    public int getNoAssets() {
        return assets.length;
    }

    public char[] getAssetSprite(int index) {
        return assets[index].getSprite().toCharArray();
    }

    public double[] getAssetPosition(int index) {
        return assets[index].getPosition();
    }
}// End of GameHandler class
