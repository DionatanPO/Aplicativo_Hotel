package com.example.app.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.example.app.R;

public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 1200;
    ImageView img, img2,img0, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img0 = findViewById(R.id.anim_0);
        Animation animation0 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animacao);
        img0.startAnimation(animation0);

        img = findViewById(R.id.anim_1);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animacao);
        img.startAnimation(animation1);

        img2 = findViewById(R.id.img2);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animacao);
        img2.startAnimation(animation2);

        img3 = findViewById(R.id.anim_3);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animacao);
        img3.startAnimation(animation3);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(Splash.this,
                        LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
