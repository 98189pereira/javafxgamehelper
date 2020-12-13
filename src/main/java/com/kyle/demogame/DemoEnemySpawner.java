package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      DemoEnemySpawner
 *  Author:     Kyle
 *  Desc:       spawns enemies
 */
public class DemoEnemySpawner extends DefaultAsset {

    /*
     *  Constructor method
     *  Parameters: String name - unique asset name
     */
    public DemoEnemySpawner(String name) {
        super(new String[]{""}, name);
    }// End constructor method

    protected static final Random random = new Random();

    protected final int MAX_INTERVAL = 5000;
    protected final int MIN_INTERVAL = 2500;

    protected int waitInterval = random.nextInt(MAX_INTERVAL) + MIN_INTERVAL;
    protected long lastSpawnTime = System.currentTimeMillis();

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(System.currentTimeMillis() - lastSpawnTime > waitInterval) {
            handler.addAsset(
                    new DemoEnemyAsset("enemy" + lastSpawnTime),
                    handler.getNoAssets() - 2
            );
            waitInterval = random.nextInt(MAX_INTERVAL) + MIN_INTERVAL;
            lastSpawnTime = System.currentTimeMillis();
        }
    }
}// End DemoEnemySpawner class
