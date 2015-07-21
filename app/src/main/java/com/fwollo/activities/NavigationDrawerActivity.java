package com.fwollo.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.fwollo.R;
import com.fwollo.fragments.MainActivityFragment;
import com.fwollo.fragments.TagListFragment;

import java.util.List;


/**
 * Created by neokree on 18/01/15.
 */
public class NavigationDrawerActivity extends BaseActivity {

    private TagListFragment tagListFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getCurrentFragment().createMenu(menu);
        return true;
    }

    @Override
    int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    void inflateViews() {
        tagListFragment = new TagListFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, tagListFragment)
                .commit();
    }

    @Override
    void viewsWereInflated() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateToolBar();
                invalidateOptionsMenu();
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                }
            }
        });
    }

    @Override
    String title() {
        return getCurrentFragment().getTitle();
    }

    private MainActivityFragment getCurrentFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            Fragment fragment = null;
            int index = fragments.size() - 1;
            while (fragment == null || index > 0) {
                fragment = fragments.get(index);
                index --;
            }

            if (fragment != null) {
                return (MainActivityFragment) fragment;
            }
        }

        return new MainActivityFragment();
    }
}
