package com.example.neha.tiltgame;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TiltGameCode extends AppCompatActivity{
    //Code from this program has been used from "Beginning Android Games" by Mario Zechner
    //Review SurfaceView, Canvas, continue

    GameSurface gameSurface;
    MediaPlayer mpl;
    SoundPool soundPool;
    int chimesID;
    int blargID;
    CountDownTimer timer;
    int secondsLeft;
    //boolean going=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this); //instead ofinflating xml, inflates instance of surface view
        setContentView(gameSurface); //thus add this line

        mpl=MediaPlayer.create(this, R.raw.jauntyjoesgamemusic);

        SoundPool.Builder builder = new SoundPool.Builder(); //creates builder
        builder.setMaxStreams(3); // how many sound effects can be played at the same time?
        soundPool = builder.build(); //create a SoundPool using the builder
        chimesID = soundPool.load(this,R.raw.chimes,1);
        blargID = soundPool.load(this,R.raw.ding,1);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameSurface.pause(); //thus add this line
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameSurface.resume(); //thus add this line
    }

    //----------------------------GameSurface Below This Line--------------------------
    public class GameSurface extends SurfaceView implements Runnable, SensorEventListener{

        Thread gameThread;
        SurfaceHolder holder;
        volatile boolean running = false;
        Bitmap myImage;
        Bitmap EnemyImage;
        Paint paintProperty;

        int value=5;
        int position=100;
        int baseline=1000;
        int score=0;
        int fast=8;

        int screenWidth;
        int screenHeight;
        int accelerometer=0;

        boolean scoreAdded;

        Bomb bomb;

        public GameSurface(Context context) {
            super(context);
            holder=getHolder();
            myImage = BitmapFactory.decodeResource(getResources(),R.drawable.rose);
            EnemyImage= BitmapFactory.decodeResource(getResources(),R.drawable.pesticide);

            Display screenDisplay = getWindowManager().getDefaultDisplay();

            bomb=new Bomb(screenWidth, EnemyImage.getWidth(), EnemyImage.getHeight());

            Point sizeOfScreen = new Point();
            screenDisplay.getSize(sizeOfScreen);
            screenWidth=sizeOfScreen.x; //public variable. thats why you dont geed s get method
            screenHeight=sizeOfScreen.y;

            paintProperty= new Paint();
            paintProperty.setTextSize(100);
        }

        @Override
        public void run() {
            while (running == true){

                if (holder.getSurface().isValid() == false)
                    continue;

                Canvas canvas= holder.lockCanvas(); //"freezes canvas for one frame
                canvas.drawRGB(0,255,0);
                //canvas.drawText("Hello World",50,200,paintProperty);
                canvas.drawText("Score "+score,200,200,paintProperty);
                canvas.drawText("Seconds Left "+secondsLeft,200,400,paintProperty);
                //canvas.drawText("Acc: "+accelerometer,200,600,paintProperty);

                mpl.start();
                mpl.setVolume((float)0.1, (float)0.1);

                SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
                Sensor mySensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                manager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);

                canvas.drawBitmap( myImage,position+value,baseline,null); //ima, x, y, idk
                //canvas.drawRect( bomb.getRectangle(), paintProperty);
                canvas.drawBitmap( EnemyImage, bomb.getX(), bomb.getY(), null);

                position=position+value;

                holder.unlockCanvasAndPost(canvas);

                this.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (fast==8){
                            fast=16;
                        } else fast=8;
                        bomb.setSpeed(fast);
                        return false;
                    }
                });

                if (bomb.getY()<screenHeight){
                    bomb.run();
                } else bomb.reSet();

                if (bomb.getRectangle().intersect(new Rect(position, baseline, position+myImage.getWidth(), baseline+myImage.getHeight()))){
                    bomb.setDamaged(true);
                    myImage = BitmapFactory.decodeResource(getResources(),R.drawable.damagedrose);
                    soundPool.play(blargID,1,1,1,0,1);
                    bomb.setScore(false);
                } else if ((bomb.getScore())&&(bomb.getY()>baseline)){
                    score++;
                    soundPool.play(chimesID,1,1,1,0,1);
                    bomb.setScore(false);
                }
                if (!bomb.getDamaged()){
                    myImage = BitmapFactory.decodeResource(getResources(),R.drawable.rose);
                }
            }
            mpl.pause();
            Canvas canvas=holder.lockCanvas();
            canvas.drawText("Final score "+score,200,600,paintProperty);
            holder.unlockCanvasAndPost(canvas);

        }

        public void resume(){
            running=true;
            timer=new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    secondsLeft=(int)millisUntilFinished/1000;
                }

                @Override
                public void onFinish() {
                    running=false;
                }
            }.start();
            gameThread=new Thread(this);
            gameThread.start();
        }

        public void pause() {
            running = false;
            while (true) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                }
            }
        }


        @Override
        public void onSensorChanged(SensorEvent event) {
            accelerometer=(int)event.values[0];
            if (event.values[0]>1){
                if (event.values[0]<4){
                    value=-5;
                } else value=-10;
                if (position<0){
                    value=0;
                }
            } else if (event.values[0]<-1){
                if (event.values[0]>-4){
                    value=5;
                } else value=10;
                if (position>screenWidth-myImage.getWidth()){
                    value=0;
                }
            } else value=0;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    }//GameSurface
}//Activity

