package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

public class BoxSpawner extends DefaultAsset {

    public BoxSpawner(String name) {
        super(new String[]{""}, name);
    }

    protected static final Random RANDOM = new Random();

    protected static final String[][] spawnedSprites = {
        new String[]{"",
                "can_l.png",
                "can_r.png",
                "can_lt.png",
                "can_rt.png"
        },
        new String[]{"",
                "bot_l.png",
                "bot_r.png",
                "bot_lt.png",
                "bot_rt.png"
        }
    };

    protected int MAX_SPAWN_TIME = 2500;
    protected int MIN_SPAWN_TIME = 500;
    protected int SPAWN_RATE_CHANGE = 10;

    protected double waitTime = RANDOM.nextDouble() * MAX_SPAWN_TIME + MIN_SPAWN_TIME;
    protected long lastTime = System.currentTimeMillis();

    protected AssetData assetData = null;

    @Override
    public void update(Object data, GameHandler handler) {
        if(assetData == null) {
            assetData = (AssetData) data;
        }
        if(System.currentTimeMillis() - lastTime > waitTime) {
            waitTime = RANDOM.nextDouble() * MAX_SPAWN_TIME + MIN_SPAWN_TIME;
            int spawnRandom = RANDOM.nextInt(10);
            if(spawnRandom < 6) {
                handler.addAsset(new TrashCan(
                        spawnedSprites[0],
                        "can" + lastTime
                ));
            } else {
                handler.addAsset(new Robot(
                        spawnedSprites[1],
                        "bot" + lastTime
                ));
            }
            lastTime = System.currentTimeMillis();
        }
        int newSpawnTime = MAX_SPAWN_TIME - assetData.getScore() * SPAWN_RATE_CHANGE;
        MAX_SPAWN_TIME = Math.max(newSpawnTime, MIN_SPAWN_TIME);
    }
}
