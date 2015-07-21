package com.fwollo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.fwollo.R;
import com.fwollo.activities.BaseActivity;

public class NavigationUtils {

    public static void showModalFragment(FragmentActivity activity, Fragment fragment, int containerId) {
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                .add(containerId, fragment, "")
                .addToBackStack("")
                .commit();
    }

    public static void hideModalFragment(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                .remove(fragment)
                .commit();
    }

    public static void showChildFragment(FragmentActivity activity, Fragment fragment, int containerId) {
        activity.getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_left_out)
                .addToBackStack("")
                .add(containerId, fragment, "")
                .commit();
    }

    public static void hideChildFragment(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
               // .setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_right_in, R.anim.push_right_out)
                .remove(fragment)
                .commit();
    }

    public static void hideFragment(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }
}
