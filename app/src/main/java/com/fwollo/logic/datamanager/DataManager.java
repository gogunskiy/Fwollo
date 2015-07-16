package com.fwollo.logic.datamanager;

import android.content.Context;

import com.fwollo.logic.services.CountryService;
import com.fwollo.logic.services.TagService;
import com.fwollo.logic.services.UserService;

public class DataManager {

    private Context context;
    private CountryService countryService = new CountryService();
    private UserService userService = new UserService();
    private TagService tagService = new TagService();


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

    public UserService getUserService() {
        return userService;
    }

    public TagService getTagService() {
        return tagService;
    }
}
