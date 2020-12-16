package com.kyle.demogame;

/*
 *  Class:      AssetData
 *  Author:     Kyle
 *  Desc:       additional data for asset updates
 */
public class AssetData {

    public void resetGame() {
        score = 0;
        lives = 3;
        projectiles = new Snowball[0];
        enemies = new Robot[0];
    }

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        ++score;
    }

    private int lives = 3;

    public int getLives() {
        return lives;
    }

    public void takeDamage() {
        --lives;
    }

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Snowball[] projectiles = new Snowball[0];

    public void addProjectile(Snowball projectile) {
        Snowball[] newProjectiles = new Snowball[projectiles.length + 1];
        System.arraycopy(projectiles, 0, newProjectiles, 0, projectiles.length);
        newProjectiles[newProjectiles.length - 1] = projectile;
        projectiles = newProjectiles;
    }

    public void removeProjectile(Snowball asset) {
        int index = -1;
        for(int i = 0; i  < projectiles.length; ++i) {
            if(projectiles[i].getName().equals(asset.getName())) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            Snowball[] newProjectiles = new Snowball[projectiles.length - 1];
            System.arraycopy(projectiles, 0, newProjectiles, 0, index);
            System.arraycopy(projectiles, index + 1, newProjectiles, index, newProjectiles.length - index);
            projectiles = newProjectiles;
        }
    }

    public Robot[] enemies = new Robot[0];

    public void addEnemy(Robot enemy) {
        Robot[] newEnemies = new Robot[enemies.length + 1];
        System.arraycopy(enemies, 0, newEnemies, 0, enemies.length);
        newEnemies[newEnemies.length - 1] = enemy;
        enemies = newEnemies;
    }

    public void removeEnemy(Robot enemy) {
        int index = -1;
        for(int i = 0; i  < enemies.length; ++i) {
            if(enemies[i].getName().equals(enemy.getName())) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            Robot[] newEnemies = new Robot[enemies.length - 1];
            System.arraycopy(enemies, 0, newEnemies, 0, index);
            System.arraycopy(enemies, index + 1, newEnemies, index, newEnemies.length - index);
            enemies = newEnemies;
        }
    }
}// End of AssetData class
