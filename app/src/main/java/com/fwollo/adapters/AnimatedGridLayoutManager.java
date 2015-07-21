package com.fwollo.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class AnimatedGridLayoutManager extends GridLayoutManager {

    public AnimatedGridLayoutManager(Context context) {
        super(context, 1);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}