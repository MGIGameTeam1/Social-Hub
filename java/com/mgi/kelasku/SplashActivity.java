package com.mgi.kelasku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //startActivity(new Intent(this, LoginActivity.class));
       // startActivity(new Intent(this, HomeActivity.class));
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
