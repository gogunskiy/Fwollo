package com.fwollo.logic.datamanager;

import android.content.Context;

import com.fwollo.logic.services.CountryService;

public class DataManager {

    private Context context;
    private CountryService countryService = new CountryService();

    private static DataManager shared = null;

    public static DataManager defaultManager() {
        if (shared == null) {
            shared = new DataManager();
        }
        return shared;
    }

   public void bindContext(Context context) {
       this.context = context;
       countryService.setContext(context);
   }

    public CountryService getCountryService() {
        return countryService;
    }
}
