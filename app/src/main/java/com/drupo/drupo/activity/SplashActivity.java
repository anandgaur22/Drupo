package com.drupo.drupo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.drupo.drupo.R;
import com.drupo.drupo.utils.PrefrenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                PrefrenceManager prefrenceManager = new PrefrenceManager(getApplicationContext());

                if (prefrenceManager.validateSession()) {

                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(i);
                    finish();

                }

            }
        }, 3000);

    }

}
