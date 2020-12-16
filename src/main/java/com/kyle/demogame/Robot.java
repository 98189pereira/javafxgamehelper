package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      Robot
 *  Author:     Kyle
 *  Desc:       Base Enemy Class
 */
public class Robot extends DefaultAsset {

    public Robot(String[] sprites, String name) {
        super(sprites, name);
    }

    protected static final Random RANDOM = new Random();

    protected double MOVE_INTERVAL = 2500;
    protected double MOVE_SPEED = 1.5;

    protected double SPAWN_LEFT = 200;
    protected double SPAWN_RIGHT = 200;
    protected double SPAWN_TOP = 626;
    protected double SPAWN_BOTTOM = 94;

    protected double RIGHT_BOUNDARY = 50;
    protected double LEFT_BOUNDARY = 50;

    protected double HB_TOP = -10;
    protected double HB_BOTTOM = 60;
    protected double HB_LEFT = 60;
    protected double HB_RIGHT = -10;

    protected double CHASE_DISTANCE = 100.0;

    protected long HIDE_SHOW_TIMEOUT = 1000;

    protected long createdTime;

    protected int direction = 1;
    protected long lastTime = 0;
    protected boolean waiting = true;

    protected boolean invulnerable = true;
    protected boolean inPosition = false;

    protected AssetData assetData = null;

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(assetData == null) {
            assetData = (AssetData) data;
            createdTime = System.currentTimeMillis();
            xPos = RANDOM.nextDouble() * ( handler.SCREEN_WIDTH - SPAWN_LEFT - SPAWN_RIGHT ) + SPAWN_RIGHT;
            yPos = RANDOM.nextDouble() * ( handler.SCREEN_HEIGHT - SPAWN_BOTTOM - SPAWN_TOP ) + SPAWN_TOP;
            inPosition = true;
        }
        //  time period just after spawn
        if(!invulnerable) {
            //  check if touching any snowballs
            for (Snowball projectile : assetData.projectiles) {
                double[] position = projectile.getPosition();
                if (position[0] > xPos + HB_RIGHT && position[0] < xPos + HB_LEFT &&
                        position[1] < yPos + HB_BOTTOM && position[1] > yPos + HB_TOP) {
                    assetData.removeEnemy(this);
                    assetData.increaseScore();
                    handler.removeAsset(name);
                    handler.addAsset(new ParticleEffects(
                            new String[]{""},
                            "particles" + System.currentTimeMillis(),
                            xPos, yPos, true
                    ));
                    break;
                }
            }// end checking loop
        }
        if(System.currentTimeMillis() - createdTime > HIDE_SHOW_TIMEOUT && invulnerable) {
            invulnerable = false;
            assetData.addEnemy(this);
        }
        //  update positions
        if(System.currentTimeMillis() - lastTime > MOVE_INTERVAL) {
            if(xPos > assetData.getPlayer().getxPos() + CHASE_DISTANCE)
                direction = -1;
            else if(xPos < assetData.getPlayer().getxPos() - CHASE_DISTANCE ||
                    xPos > assetData.getPlayer().getxPos())
                direction = 1;
            else
                direction = -1;
            lastTime = System.currentTimeMillis();
            waiting = RANDOM.nextBoolean();
        }
        if(!waiting) {
            xPos += MOVE_SPEED * direction;
        }
        if(xPos < LEFT_BOUNDARY ||
                xPos > handler.SCREEN_WIDTH - RIGHT_BOUNDARY)
            direction *= -1;
    }// End of update()

    @Override
    public String getSprite() {
        return sprites[(direction == 1) ? 0 : 1 + ((invulnerable) ? 2 : 0) - ((inPosition) ? 0 : 1)];
    }
}// End of Robot enemy class
