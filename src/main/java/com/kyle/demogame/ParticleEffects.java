package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;
import com.kyle.javafxgamehelper.GameHandler;

import java.util.Random;

/*
 *  Class:      ParticleEffects
 *  Author:     Kyle
 */
public class ParticleEffects extends DefaultAsset {

    public ParticleEffects(String[] sprites, String name, double xPos, double yPos, boolean parent) {
        super(sprites, name);
        this.parent = parent;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    protected static final Random RANDOM = new Random();

    protected static final int NO_PARTICLES = 20;

    protected double GRAVITY = 5;
    protected double FLOOR = 685;
    protected int BASE_PARTICLE_DURATION = 750;
    protected int EXTRA_PARTICLE_DURATION = 100;
    protected double MAX_VELOCITY = 100.0;
    protected double MIN_VELOCITY = -20.0;
    protected double MAX_SPEED = 25.0;
    protected double MIN_SPEED = 5.0;

    protected final boolean parent;

    protected long lastAt = -1;
    protected int noParticles = NO_PARTICLES;

    protected double velocity = RANDOM.nextDouble() * MAX_VELOCITY + MIN_VELOCITY;
    protected double speed = RANDOM.nextDouble() * MAX_SPEED + MIN_SPEED;
    protected boolean direction = RANDOM.nextBoolean();

    protected ParticleEffects[] childParticles = new ParticleEffects[NO_PARTICLES];

    @Override
    public void update(Object data, GameHandler handler) throws InterruptedException {
        //  parent spawns children, doesn't have own sprite
        if(parent) {
            //  initial spawn
            if(lastAt == -1) {
                for(int i = 0; i < NO_PARTICLES; ++i) {
                    childParticles[i] = new ParticleEffects(new String[]{
                            "particle.png"
                    }, name + ".particle." + i,
                            xPos, yPos, false);
                    handler.addAsset(childParticles[i]);
                }
                lastAt = System.currentTimeMillis();
            }
            //  particle decay
            if( ( System.currentTimeMillis() - lastAt > BASE_PARTICLE_DURATION && noParticles == NO_PARTICLES ) ||
                    ( System.currentTimeMillis() - lastAt > EXTRA_PARTICLE_DURATION && noParticles != NO_PARTICLES ) ) {
                --noParticles;
                handler.removeAsset(childParticles[noParticles].getName());
                lastAt = System.currentTimeMillis();
            }
            //  all gone
            if(noParticles < 1) {
                handler.removeAsset(name);
            }
        } else {
            //  as a child, move randomly
            velocity += GRAVITY;
            if(yPos < FLOOR)
                yPos += velocity;
            else if(yPos > FLOOR) {
                yPos = FLOOR;
                speed = 0;
            }
            xPos += speed * ((direction) ? 1 : -1);
        }// end conditional
    }// End of update()
}// End of ParticleEffects class
