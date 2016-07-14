package com.goroshevsky.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by vadim on 10-Jul-16.
 */
public class SplashActivity extends Activity {
    private String TAG = "Splash";
    private EditText textUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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


