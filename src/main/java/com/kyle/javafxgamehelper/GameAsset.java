package com.kyle.javafxgamehelper;

import javafx.scene.input.KeyEvent;

/*
 *  Interface:  GameAsset
 *  Author:     Kyle
 *  Desc:       Interface that all game assets must implement
 */
public interface GameAsset {
    //  get the position of the asset for drawing
    //  if it is visible
    double[] getPosition();

    //  get the sprite and name of the asset
    //  if sprite has not been loaded / is empty
    //  considered 'not visible'
    String getSprite();
    String getName();

    //  update is called at intervals to change values
    //  keyEventHandler responds to key events
    default void update(Object data, GameHandler handler) throws InterruptedException {}
    default void keyEventHandler(KeyEvent keyEvent) {}
}
