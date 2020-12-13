package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      DemoCloudSpawner
 *  Author:     Kyle
 *  Desc:       spawns clouds
 */
public class DemoCloudSpawner extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoCloudSpawner(String name) {
        super(new String[]{""}, name);
    }// End constructor method

    protected static final Random random = new Random();

    protected static final int MAX_INTERVAL = 5000;
    protected static final int MIN_INTERVAL = 500;

    protected int waitInterval = random.nextInt(MAX_INTERVAL) + MIN_INTERVAL;
    protected long lastSpawnTime = System.currentTimeMillis();

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(System.currentTimeMillis() - lastSpawnTime > waitInterval) {
            handler.addAsset(
                    new DemoCloudAsset("cloud" + lastSpawnTime),
                    handler.getNoAssets() - 2
            );
            waitInterval = random.nextInt(MAX_INTERVAL) + MIN_INTERVAL;
            lastSpawnTime = System.currentTimeMillis();
        }
    }
}// End DemoCloudSpawner class
