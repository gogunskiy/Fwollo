package com.fwollo.logic.datamanager;

import android.content.Context;

import com.fwollo.logic.apiclient.ApiClient;
import com.fwollo.logic.services.BaseService;
import com.fwollo.logic.services.CountryService.CountryList;
import com.fwollo.logic.services.ServiceListener;
import com.octo.android.robospice.SpiceManager;

public class DataManager {

    private SpiceManager spiceManager = null;

    private CountryList countryList = null;


    private static DataManager shared = null;
    private Context context;
    private ApiClient apiClient;

    public static DataManager defaultManager() {
        if (shared == null) {
            shared = new DataManager();
        }
        return shared;
    }

    public void setSpiceManager(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }

    public CountryList getCountryList() {
        return countryList;
    }

    public void setCountryList(CountryList countryList) {
        this.countryList = countryList;
    }

    public final <T, E extends ServiceListener<T>> void runTaskInBackground(BaseService<T, E> task, E listener) {
        if (task.isCacheEnabled()) {
            spiceManager.execute(task, task.getCacheKey(), task.getExpiresInMillis(), listener);
        } else {
            spiceManager.execute(task, listener);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}
