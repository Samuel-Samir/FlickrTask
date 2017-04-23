package com.example.android.flickrtask.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.android.flickrtask.R;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        ImageView splashImage = (ImageView) findViewById(R.id.splashImage);

        Animation animation = AnimationUtils.loadAnimation(this , R.anim.splash_animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SplashScreen.this , MainActivity.class));
                        SplashScreen.this.finish();
                    }
                } , 400);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashImage.startAnimation(animation);
    }
}
