package com.fwollo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.fwollo.R;


public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initiateToolBar();
        inflateViews();
        viewsWereInflated();
    }

    protected void initiateToolBar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(title());
    }

    abstract int layoutId();
    abstract void inflateViews();
    abstract void viewsWereInflated();
    abstract String title();
}
