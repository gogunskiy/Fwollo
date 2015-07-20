package com.fwollo.logic.services;

import android.content.Context;

import com.fwollo.logic.apiclient.ApiClient;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Parent task to be executed in background. Results can be cached
 * 
 * @param <Result> task result type
 */
public abstract class BaseService<Result, T> extends SpiceRequest<Result> {

    private String cacheKey;
    private long expires = DurationInMillis.ALWAYS_EXPIRED;

    protected ApiClient apiClient;
    protected Context context;


    public BaseService(Class<? super Result> clazz) {
        super((Class<Result>) clazz);
    }

    /**
     * Enable caching for this task instance results.
     *
     * @param expiresMillis caching expiry in millis. If not expired result will
     *            be found in cache it will be used as result
     * @param cacheKeys     values that uniquely determine this task instance
     */
    public void enableCaching(long expiresMillis, Object... cacheKeys) {
        expires = expiresMillis;
        cacheKey = createCacheKey(cacheKeys);
    }

    /**
     * Disable caching for this task
     */
    public void disableCaching() {
        this.cacheKey = null;
    }

    /**
     * Check if this task implementation supports caching
     *
     * @return true if result can be cached
     */
    public boolean isCacheEnabled() {
        return cacheKey != null;
    }

    /**
     * Return expiry timeout for previous task results.
     * If not expired result will be found
     * in cache it will be used as result
     */
    public long getExpiresInMillis() {
        return expires;
    }

    /**
     * Used inside RoboSpice to unique identify task
     * Can be null if cache is disabled
     *
     * @return cache key
     */
    public Object getCacheKey() {
        return cacheKey;
    }


    /**
     * Executed in background thread
     *
     * @return result
     */
    protected abstract Result doInBackground() throws Exception;

    @Override
    public final Result loadDataFromNetwork() throws Exception {
        return doInBackground();
    }

    private String createCacheKey(Object[] cacheKeys) {
        StringBuilder sb = new StringBuilder(getClass().getName());
        for (Object key : cacheKeys) {
            if(key != null) {
                sb.append(key.toString());
            }
        }
        return sb.toString();
    }

}
