package com.goroshevsky.calculator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by vadim on 10-Jul-16.
 */

public class SplashActivity extends AppCompatActivity {
    private String TAG = "Splash";
    private EditText textUserName;
    public final int REQUEST_LOCATION = 0;

    static{
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_LOCATION);
            }
        }
        textUserName = (EditText) findViewById(R.id.userNameEditText);
    }

    public void onButtonGoClick(View view) {
        Log.d(TAG, "GO pressed");
        if (!(TextUtils.isEmpty(textUserName.getText()))) {
            final Editable userNameText = textUserName.getEditableText();
            MainActivity.USER_NAME = userNameText.toString();
            Log.d(TAG, "User Name is " + MainActivity.USER_NAME);
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), R.string.name_is_not_set, Toast.LENGTH_LONG).show();
            Log.w(TAG, "User Name is not set!");
        }
    }
}


