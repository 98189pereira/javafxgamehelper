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
     */
    public GameHandler(Object updateData) {
        this.updateData = updateData;
    }// End of constructor method

    final Object updateData;

    String[] sprites = new String[0];
    GameAsset[] assets = new GameAsset[0];

    public void addSprite(String sprite) {
        String[] newSprites = new String[this.sprites.length + 1];
        System.arraycopy(this.sprites, 0, newSprites, 0, this.sprites.length);
        newSprites[newSprites.length - 1] = sprite;
        this.sprites = newSprites;
    }

    public void removeSprite(String sprite) {
        int pos = -1;
        for(int i = 0; i < this.sprites.length; ++i) {
            if(this.sprites[i].equals(sprite)) {
                pos = i;
                break;
            }
        }
        if(pos > -1) {
            String[] newSprites = new String[this.sprites.length - 1];
            System.arraycopy(this.sprites, 0, newSprites, 0, pos);
            System.arraycopy(this.sprites, pos + 1, newSprites, pos, newSprites.length - pos);
            this.sprites = newSprites;
        }
    }

    public void addAsset(GameAsset asset) {
        GameAsset[] newAssets = new GameAsset[this.assets.length + 1];
        System.arraycopy(this.assets, 0, newAssets, 0, this.assets.length);
        newAssets[newAssets.length - 1] = asset;
        this.assets = newAssets;
    }

    public void addAsset(GameAsset asset, int index) {
        GameAsset[] newAssets = new GameAsset[this.assets.length + 1];
        System.arraycopy(this.assets, 0, newAssets, 0, index);
        newAssets[index] = asset;
        System.arraycopy(this.assets, index, newAssets, index + 1, this.assets.length - index);
        this.assets = newAssets;
    }

    public void removeAsset(String name) {
        int pos = -1;
        for(int i = 0; i < this.assets.length; ++i) {
            if(this.assets[i].getName().equals(name)) {
                pos = i;
                break;
            }
        }
        if(pos > -1) {
            GameAsset[] newAssets = new GameAsset[this.assets.length - 1];
            System.arraycopy(this.assets, 0, newAssets, 0, pos);
            System.arraycopy(this.assets, pos + 1, newAssets, pos, newAssets.length - pos);
            this.assets = newAssets;
        }
    }

    public void setAssets(GameAsset[] assets) {
        this.assets = assets;
    }

    public void updateAssets() throws InterruptedException {
        for(GameAsset asset : this.assets) {
            asset.update(updateData, this);
        }
    }

    public void handleAssets(KeyEvent keyEvent) {
        for(GameAsset asset : this.assets) {
            asset.keyEventHandler(keyEvent);
        }
    }

    public int getNoSprites() {
        return this.sprites.length;
    }

    public char[] getSpriteName(int i) {
        return this.sprites[i].toCharArray();
    }

    public int getNoAssets() {
        return this.assets.length;
    }

    public char[] getAssetSprite(int i) {
        return this.assets[i].getSprite().toCharArray();
    }

    public double[] getAssetPosition(int i) {
        return this.assets[i].getPosition();
    }
}// End of GameHandler class
