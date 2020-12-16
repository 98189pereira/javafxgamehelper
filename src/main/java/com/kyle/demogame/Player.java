package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;
import javafx.scene.input.KeyEvent;

import java.util.Random;

/*
 *  Class:      Player
 *  Author:     Kyle
 */
public class Player extends DefaultAsset {

    public Player(String[] sprites, String name) {
        super(sprites, name);
        velocity = 0.0;
    }

    protected static final Random RANDOM = new Random();

    protected double SIZE = 100.0;

    protected double GRAVITY = 5;
    protected double GROUND = 590;
    protected double MOVE_SPEED = 12.0;
    protected double JUMP_POWER = -35.0;
    protected double ENEMY_RECOIL = 50.0;
    protected double ENEMY_BOUNCE = -25.0;

    protected double SNOWBALL_DISTANCE = 70.0;
    protected double SNOWBALL_Y_OFFSET = 30.0;
    protected double SNOWBALL_DRIFT = 10.0;
    protected double SNOWBALL_SPREAD = 25.0;

    //  Hitboxes
    protected double HB_TOP = 10;
    protected double HB_BOTTOM = 60;
    protected double HB_LEFT = 60;
    protected double HB_RIGHT = 10;

    protected int MOVING_ANIMATION_RATE = 100;
    protected int RESTING_ANIMATION_RATE = 500;

    protected int spriteCount = 0;
    protected int spriteSet = 0;
    protected long lastChange = 0;

    protected double velocity;
    protected boolean[] keysPressed = {false,false,false};
    protected int direction = 1;
    protected boolean recoiling = false;

    protected boolean pause = false;
    protected boolean canPause = true;
    protected AssetData assetData = null;

    @Override
    public void keyEventHandler(KeyEvent keyEvent) {
        if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyEvent.getCode()) {
                case RIGHT:
                    keysPressed[1] = true;
                    direction = 1;
                    break;
                case LEFT:
                    keysPressed[0] = true;
                    direction = -1;
                    break;
                case UP:
                    if(yPos == GROUND) {
                        velocity = JUMP_POWER;
                    }
                    spriteCount = 0;
                    break;
                case SPACE:
                    keysPressed[2] = true;
                    break;
                case P:
                    if(canPause)
                        pause = true;
                    break;
            }
        } else if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (keyEvent.getCode()) {
                case RIGHT:
                    keysPressed[1] = false;
                    spriteCount = 0;
                    break;
                case LEFT:
                    keysPressed[0] = false;
                    spriteCount = 0;
                    break;
                case SPACE:
                    keysPressed[2] = false;
                    break;
            }
        }
    }// End of keyEventHandler()

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        if(assetData == null) {
            assetData = (AssetData) data;
            assetData.setPlayer(this);
        }
        if(pause) {
            handler.addAsset(
                    new DefaultAsset(new String[]{
                            "paused.png"
                    }, "game paused")
            );
            pause = false;
            canPause = false;
            SnowballFight.changeGameState(true);
        }
        //  Flying back after hit enemy
        if(!recoiling) {
            //  Check if touching any enemy
            for (Robot enemy : assetData.enemies) {
                double[] position = enemy.getPosition();
                if (position[0] > xPos + HB_RIGHT && position[0] < xPos + HB_LEFT &&
                        position[1] < yPos + HB_BOTTOM && position[1] > yPos + HB_TOP) {
                    assetData.takeDamage();
                    xPos += ENEMY_RECOIL * ((position[0] < xPos) ? 1 : -1);
                    velocity = ENEMY_BOUNCE;
                    recoiling = true;
                    if (assetData.getLives() < 0) {
                        handler.addAsset(
                                new DefaultAsset(new String[]{
                                        "gameover.png"
                                }, "game over")
                        );
                        SnowballFight.changeGameState(false);
                        pause = false;
                    }
                    break;
                }
            }// End checking loop
        }
        //  Moving left or right and close to ground
        if(System.currentTimeMillis() - lastChange > MOVING_ANIMATION_RATE &&
                (keysPressed[0] || keysPressed[1]) && yPos > GROUND - SIZE) {
            ++spriteCount;
            if(spriteCount > 2)
                spriteCount = 0;
            lastChange = System.currentTimeMillis();
        }
        //  No keys pressed
        if(System.currentTimeMillis() - lastChange > RESTING_ANIMATION_RATE &&
                !keysPressed[0] && !keysPressed[1] && !keysPressed[2]) {
            ++spriteCount;
            if(spriteCount > 1)
                spriteCount = 0;
            spriteSet = (direction == 1) ? 0 : 4;
            lastChange = System.currentTimeMillis();
        }
        //  update positions
        velocity += GRAVITY;
        yPos += velocity;
        if(getyPos() > GROUND) {
            velocity = 0;
            yPos = GROUND;
        }
        if(yPos == GROUND && recoiling)
            recoiling = false;
        if(keysPressed[0]) {
            spriteSet = 5;
            xPos -= MOVE_SPEED;
            if(recoiling)
                xPos += 2 * MOVE_SPEED;
        }
        if(keysPressed[1]) {
            spriteSet = 1;
            xPos += MOVE_SPEED;
            if(recoiling)
                xPos -= 2 * MOVE_SPEED;
        }
        if(xPos > handler.SCREEN_WIDTH - SIZE) {
            xPos = handler.SCREEN_WIDTH - SIZE;
        }
        if(xPos < 0) {
            xPos = 0;
        }
        //  throw snowballs
        if(keysPressed[2]) {
            spawn(assetData, handler);
        }
    }// End of update()

    private void spawn(AssetData assetData, GameHandler handler) {
        double spawnDistance = (direction == 1) ? SNOWBALL_DISTANCE : 0;
        if(assetData.getScore() < 30) {
            handler.addAsset(new Snowball(
                    new String[]{"ball.png"},
                    "ball" + System.currentTimeMillis(),
                    xPos + direction * spawnDistance,
                    yPos + SNOWBALL_Y_OFFSET, direction,
                    (RANDOM.nextDouble() - 0.5) * SNOWBALL_DRIFT
            ));
            xPos -= Snowball.RECOIL * direction;
        } else if(assetData.getScore() < 70){
            for(int i = -1; i < 2; ++i) {
                handler.addAsset(new Snowball(
                        new String[]{"ball.png"},
                        "ball" + System.currentTimeMillis() + "_" + i,
                        xPos + direction * spawnDistance,
                        yPos + SNOWBALL_Y_OFFSET + i * SNOWBALL_SPREAD, direction,
                        ( RANDOM.nextDouble() - 0.5 ) * SNOWBALL_DRIFT +
                                i * SNOWBALL_SPREAD
                ));
            }
            xPos -= Snowball.RECOIL * direction * 3;
        } else {
            for(int i = -2; i < 3; ++i) {
                handler.addAsset(new Snowball(
                        new String[]{"ball.png"},
                        "ball" + System.currentTimeMillis() + "a" + i,
                        xPos + direction * spawnDistance,
                        yPos + SNOWBALL_Y_OFFSET + i * SNOWBALL_SPREAD, direction,
                        ( RANDOM.nextDouble() - 0.5 ) * SNOWBALL_DRIFT +
                                i * SNOWBALL_SPREAD
                ));
            }
            spawnDistance = (direction != 1) ? SNOWBALL_DISTANCE : 0;
            for(int i = -2; i < 3; ++i) {
                handler.addAsset(new Snowball(
                        new String[]{"ball.png"},
                        "ball" + System.currentTimeMillis() + "b" + i,
                        xPos + direction * spawnDistance * -1,
                        yPos + SNOWBALL_Y_OFFSET + i * SNOWBALL_SPREAD, direction * -1,
                        ( RANDOM.nextDouble() - 0.5 ) * SNOWBALL_DRIFT +
                                i * SNOWBALL_SPREAD
                ));
            }
        }
    }// End spawn()

    @Override
    public double[] getPosition() {
        return new double[]{
                getxPos(),
                getyPos(),
                SIZE,
                SIZE
        };
    }

    @Override
    public String getSprite() {
        return sprites[spriteCount + spriteSet];
    }
}// End of Player class
