package com.cyberllama.testgame;


import android.content.res.Resources;
import android.view.SurfaceHolder;

public class Game {

    private Thread thread;
    private GameLoop gameLoop;

    public Game(SurfaceHolder holder, Resources resources) {
        gameLoop = new GameLoop(holder, resources);
        thread = new Thread(gameLoop);
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        gameLoop.pleaseStop();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
