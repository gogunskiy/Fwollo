package com.fwollo.logic.services;

import android.app.Application;
import android.content.Context;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;


public class ExecutionService extends SpiceService {

    /**
     * To allow Robospice run tasks without network access
     */
    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return new NetworkStateChecker() {
            @Override
            public boolean isNetworkAvailable(Context context) {
                return true;
            }

            @Override
            public void checkPermissions(Context context) { }
        };
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        return ((com.fwollo.application.Application)getApplication()).getCacheManager();
    }
}
