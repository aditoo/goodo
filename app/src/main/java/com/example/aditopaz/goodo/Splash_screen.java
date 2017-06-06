package com.example.aditopaz.goodo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"archristy.ttf");


        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startNextActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    private void startNextActivity(){
       /* New Handler to start the Menu-Activity
        * and close this Splash-Screen after some seconds.
        */
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

        /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                GoodoDoc.loadGoodoDocData(settings);
                if (settings.contains("user_id")) {

                    //sent intent to main screen. use by:  new Intent(Splash.this,MapsActivity.class);
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    //the user DID NOT LOGIN
                    // then we will run the service after the first login
                    Intent mainIntent = new Intent(Splash_screen.this, SignUp.class);
                    Splash_screen.this.startActivity(mainIntent);
                    Splash_screen.this.finish();
                }
            }
        }, 1500);
    }
}
