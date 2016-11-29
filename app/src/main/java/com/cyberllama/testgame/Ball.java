package com.cyberllama.testgame;


import java.util.Random;

public class Ball {

    private float x;
    private float y;
    private float vx;
    private float vy;

    public Ball(Random rand){

        x = rand.nextFloat() * 10f;
        y = rand.nextFloat() * 10f;
        vx = -0.5f + rand.nextFloat();
        vy = -0.5f + rand.nextFloat();
    }

    public void step(){
        x += vx;
        y += vy;

        if(x < 0f || x > 90f){
            vx = -vx;
        }
        if(y < 0f || y > 93f){
            vy = -vy;
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }

}
