package com.fwollo.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.fwollo.R;


public class LaunchActivity extends BaseActivity {

    @Override
    int layoutId() {
        return R.layout.activity_launch;
    }

    @Override
    void inflateViews() {
        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    void viewsWereInflated() {

    }

    @Override
    String title() {
        return "";
    }

    @Override
    protected void initiateToolBar() {

    }
}
