package com.kyle.demogame;

import com.kyle.javafxgamehelper.GameHandler;

/*
 *  Class:      DemoEnemySpawner
 *  Author:     Kyle
 *  Desc:       spawns enemies
 */
public class DemoEnemySpawner extends DemoCloudSpawner {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoEnemySpawner(String name) {
        super(name);
        MAX_INTERVAL = 5000;
        MIN_INTERVAL = 2500;
    }// End constructor method

    @Override
    public void spawn(GameHandler handler, long lastSpawnTime) {
        handler.addAsset(
                new DemoEnemyAsset("enemy" + lastSpawnTime),
                handler.getNoAssets() - 2
        );
    }
}// End DemoEnemySpawner class
