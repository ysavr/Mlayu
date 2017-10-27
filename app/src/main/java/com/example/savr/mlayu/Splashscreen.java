package com.example.savr.mlayu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.savr.mlayu.Login.Login;

public class Splashscreen extends AppCompatActivity {
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logo = (ImageView) findViewById(R.id.logosplash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        logo.startAnimation(animation);
        final Intent toLogin = new Intent(this, Login.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(toLogin);
                    finish();
                }
            }
        };
        timer.start();
    }
}
