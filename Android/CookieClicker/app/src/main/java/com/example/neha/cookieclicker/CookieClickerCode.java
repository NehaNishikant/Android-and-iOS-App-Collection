package com.example.neha.cookieclicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import static com.example.neha.cookieclicker.R.id.rel;

public class CookieClickerCode extends AppCompatActivity {

    ImageView cookieHolder;
    RelativeLayout lay;
    AtomicInteger clicks;
    TextView score;
    TextView test;
    int grandmaClicks;
    int clicksPerSecond;
    ImageView GrandmaHolder;
    TextView powerClicks;

    final String tag="tag"; //stores clicks
    final String tag2="tag2"; //stores grandmas left
    final String tag3="tag3"; //stores clicks per second

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(tag, clicks.get());
        outState.putInt(tag2, grandmaClicks);
        outState.putInt(tag3, clicksPerSecond);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie_clicker_code);

        cookieHolder = (ImageView) findViewById(R.id.cookieHolderId);
        cookieHolder.setImageResource(R.drawable.cookie);

        score = (TextView) findViewById(R.id.score);
        test=(TextView)findViewById(R.id.textView);
        powerClicks=(TextView)findViewById(R.id.grandmaClicksId);

        lay = (RelativeLayout) findViewById(rel);

        clicks=new AtomicInteger();
        clicks.set(0);
        grandmaClicks=0;
        clicksPerSecond=0;

        if (savedInstanceState!=null){
            clicks.set(savedInstanceState.getInt(tag));
        }

        powerClicks.setText("Grandmas: "+grandmaClicks);

        GrandmaHolder=(ImageView)findViewById(R.id.GrandmaHolderId);
        GrandmaHolder.setImageResource(R.drawable.darkgrandma);

        cookieHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make and set animation for and to cookie click
                ScaleAnimation pop = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                pop.setDuration(200);
                cookieHolder.startAnimation(pop);
                //increments score
                clicks.getAndIncrement();
                //update score
                score.setText("Cookies: " + clicks);

                //adds +1 textview
                final TextView point = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE); //verb = where it is going to be in relation to something else (above). Textview is the "subject"
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                point.setText("+1");
                lay.addView(point, params);

                //make animation for +1
                int r = (int) (Math.random() * 10) - 5; //immediately transfers to (r, 0)
                TranslateAnimation floaty = new TranslateAnimation(Animation.RELATIVE_TO_SELF, r, Animation.RELATIVE_TO_SELF, r, Animation.RELATIVE_TO_SELF, r, Animation.RELATIVE_TO_SELF, r - 6);
                floaty.setDuration(1000);
                point.startAnimation(floaty);

                //set floaty animation to +1 and delete once done
                floaty.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        lay.removeView(point);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

        //thread that checks for grandmas
        Thread powerUps = new Thread("gorgdon2") {
            public void run() {

                while (true){ //continuous

                    //sets and displays # of grandmas
                    grandmaClicks=clicks.get()/50;
                    powerClicks.post(new Runnable() {
                        @Override
                        public void run() {
                            powerClicks.setText("Grandmas: "+grandmaClicks);
                        }
                    });

                    //shows grandmas
                    if (grandmaClicks>0) {
                        GrandmaHolder.post(new Runnable() {
                            @Override
                            public void run() {
                                GrandmaHolder.setImageResource(R.drawable.grandma);
                            }
                        });

                        //allows grandmas to be used
                        GrandmaHolder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //uses one grandma up and adds powerup effect
                                grandmaClicks--;
                                clicks.getAndAdd(-50);
                                clicksPerSecond += 1;

                                //add a grandma programatically
                                final ImageView grandmaClone = new ImageView(getApplicationContext());
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE); //verb = where it is going to be in relation to something else (above). Textview is the "subject"
                                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); //verb = where it is going to be in relation to something else (above). Textview is the "subject"
                                lay.addView(grandmaClone, params);
                                grandmaClone.setImageResource(R.drawable.grandmaclone);
                                //makes a transition for the grandma
                                TranslateAnimation powerUp = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5);
                                powerUp.setDuration(3000);
                                grandmaClone.startAnimation(powerUp);
                                //set animation
                                grandmaClone.setAnimation(powerUp);
                                lay.removeView(grandmaClone);

                                //resets grandma to dark if grandma count goes below 0
                                if (grandmaClicks <= 0) {
                                    GrandmaHolder.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            GrandmaHolder.setImageResource(R.drawable.darkgrandma);
                                        }
                                    });
                                }
                            }

                        });

                    }

                    //shows clicks/per second
                    test.post(new Runnable() {
                        @Override
                        public void run() {
                            test.setText("Cookies per second: "+clicksPerSecond);
                        }
                    });

                    //adds clicks per second
                    try {
                        clicks.getAndAdd(clicksPerSecond);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        score.post(new Runnable() {
                            @Override
                            public void run() {
                                score.setText("HELP");
                            }
                        });
                    }

                    //updates click
                    score.post(new Runnable() {
                        @Override
                        public void run() {
                            score.setText("Cookies: "+clicks.get());
                        }
                    });
             }

            }

        }; //wow @ mini class

        //runs thread
        powerUps.start();
    }
}


