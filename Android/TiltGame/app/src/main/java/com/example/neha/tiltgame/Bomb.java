package com.example.neha.tiltgame;

import android.graphics.Rect;
import android.hardware.camera2.params.MeteringRectangle;

import java.lang.Math;

/**
 * Created by 10010422 on 3/30/2017.
 */

public class Bomb {

    private int x; //starting x position
    private int y; //starting y position
    private int speed=5;
    private int screenWidth;
    private int imageWidth;
    private int imageHeight;
    private Rect rectangle;
    boolean damaged=false;
    boolean score=true;

    public Bomb(int screenWidth, int imageWidth, int imageHeight){
        this.screenWidth=screenWidth;
        this.imageWidth=imageWidth;
        this.imageHeight=imageHeight;
        x=(int)(Math.random()*(580));
        y=0;
        rectangle=new Rect(x, y, x+imageWidth, y+imageHeight);
        run();
    }

    public void run(){
        y+=speed;
        rectangle.set(x, y, x+imageWidth, y+imageHeight);
    }

    public void reSet(){
        if (damaged){
            damaged=false;
        }
        x=(int)(Math.random()*(750));
        y=0;
        rectangle.set(x, y, x+imageWidth, y+imageHeight);
        score=true;
        run();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public boolean getScore(){
        return score;
    }

    public boolean getDamaged(){
        return damaged;
    }

    public void setDamaged(boolean damaged){
        this.damaged=damaged;
    }

    public void setSpeed (int speed) { this.speed=speed; }

    public void setScore (boolean score) { this.score=score; }
}

