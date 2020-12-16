package com.kyle.demogame;

import com.kyle.javafxgamehelper.DefaultAsset;

/*
 *  Class:      DisplayScore
 *  Author:     Kyle
 *  Desc:       numbers
 */
public class DisplayScore extends DefaultAsset {

    public DisplayScore(String name, double xPos, double yPos) {
        super(new String[]{
                "0.png",
                "1.png",
                "2.png",
                "3.png",
                "4.png",
                "5.png",
                "6.png",
                "7.png",
                "8.png",
                "9.png"}
                , name);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    protected int spriteNo = 0;

    public void setSpriteNo(int spriteNo) {
        this.spriteNo = spriteNo;
    }

    @Override
    public String getSprite() {
        return sprites[spriteNo];
    }
}// End of DisplayScore class
