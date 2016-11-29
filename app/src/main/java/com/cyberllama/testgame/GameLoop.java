package com.cyberllama.testgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Date;
import java.util.Random;

public class GameLoop implements Runnable {

    private final int num_balls = 50;

    //Up to ten losses of physics (game will begin to lag).
    private final long max_allowed_skips = 25;
    private final long sim_time_step_ms = 5;
    private final long frame_time_ms = 20;
    private final SurfaceHolder surfaceHolder;

    private boolean running = true;
    private final Ball[] balls;
    private Paint paint;
    private Bitmap ballBitmap;




    public GameLoop(SurfaceHolder holder, Resources resources){

        this.surfaceHolder = holder;

        Random rand = new Random();

        this.balls = new Ball[num_balls];
        for(int i = 0; i < num_balls; ++i){
            balls[i] = new Ball(rand);
        }

        paint = new Paint();

        ballBitmap = BitmapFactory.decodeResource(resources, R.drawable.muuud);
    }



    @Override
    public void run(){

        //Keep time of 'game world'.
        long simulation_time = new Date().getTime();
        long frame_start_time = simulation_time;

        while(running){

            //Put in separate classes.
            processInput();
            simulation_time = doPhysics(simulation_time, frame_start_time);
            drawGraphics();

            frame_start_time = waitForNextFrame(frame_start_time);
        }
    }

    public void pleaseStop() {
        running = false;
    }





    private long waitForNextFrame( long frame_start_time){

        long next_frame_start_time = new Date().getTime();

        long how_long_we_took = next_frame_start_time - frame_start_time;
        long wait_time = frame_time_ms - how_long_we_took;

        if(wait_time > 0) {
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return next_frame_start_time;
    }






    private void drawGraphics(){

        //Gives us access to canvas.
        Canvas canvas = surfaceHolder.lockCanvas();

        //Check if canvas is busy.
        if(canvas == null){
            return;
        }

        try{
            synchronized (surfaceHolder) {
                actuallyDrawGraphics(canvas);
            }
        }finally {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void actuallyDrawGraphics(Canvas canvas){

        canvas.drawColor(Color.WHITE);

        for(Ball ball : balls){
            canvas.drawBitmap(
                    ballBitmap,
                    -16f + ((ball.getX()/100f) * canvas.getWidth()),
                    -16f + ((ball.getY()/100f) * canvas.getHeight()),
                    paint
            );
        }
    }





    private long doPhysics(long simulation_time, long frame_start_time){


        //Catch up physics if drawing took to long. (FPS handler).
        for(int skipped = 0; skipped < max_allowed_skips; ++ skipped){

            if(simulation_time >= frame_start_time){
                break;
            }

            moveBalls();
            simulation_time += sim_time_step_ms;
        }

        return simulation_time;
    }

    private void moveBalls(){
        for(Ball ball : balls){
            ball.step();
        }
    }







    private void processInput() {

    }



}
