package com.fwollo.application;
import com.fwollo.logic.datamanager.DataManager;


/**
 * Created by vgogunsky on 3/12/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.defaultManager().bindContext(getApplicationContext());
    }

}
