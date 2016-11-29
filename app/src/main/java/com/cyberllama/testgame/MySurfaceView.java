package com.cyberllama.testgame;


import android.content.Context;
import android.content.res.Resources;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    private final Resources resources;
    private Game game;

    public MySurfaceView(Context context, Resources resources){

        super(context);
        game = null;
        this.resources = resources;

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        game = new Game(holder, resources);
        game.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if(game != null){
            game.stop();
        }

        game = null;
    }
}
