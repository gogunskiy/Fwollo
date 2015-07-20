package com.fwollo.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

class TitleIndicatorAdapter extends PagerAdapter {

    private ArrayList <String> items;

    public TitleIndicatorAdapter(ArrayList <String> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }


    public Fragment getItem(int position) {
        return new Fragment();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return position;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}