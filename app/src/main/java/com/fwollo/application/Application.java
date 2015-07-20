package com.fwollo.application;
import com.fwollo.logic.apiclient.ApiClient;
import com.fwollo.logic.cache.InMemoryCache;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.services.ExecutionService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.CacheManager;


/**
 * Created by vgogunsky on 3/12/15.
 */
public class Application extends android.app.Application {

    private static final int CACHE_DEFAULT = 100;
    private CacheManager cacheManager;
    private ApiClient apiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        cacheManager = createCacheManager();
        apiClient = createApiCLient();

        SpiceManager spiceManager = new SpiceManager(ExecutionService.class);
        spiceManager.start(getApplicationContext());
        DataManager.defaultManager().setSpiceManager(spiceManager);
        DataManager.defaultManager().setContext(getApplicationContext());
        DataManager.defaultManager().setApiClient(apiClient);
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    @Override
    public void onLowMemory() {
        cacheManager.removeAllDataFromCache();
        super.onLowMemory();
    }

    private CacheManager createCacheManager() {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new InMemoryCache(CACHE_DEFAULT));
        return cacheManager;
    }

    private ApiClient createApiCLient() {
        return new ApiClient(getApplicationContext());
    }
}
