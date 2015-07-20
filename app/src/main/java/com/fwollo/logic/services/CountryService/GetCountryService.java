package com.fwollo.logic.services.CountryService;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.fwollo.logic.models.Country;
import com.fwollo.logic.services.BaseService;
import com.fwollo.logic.services.ServiceListener;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class GetCountryService extends BaseService<CountryList, GetCountryService.GetCountryTaskListener> {

    public GetCountryService(Context context) {
        super(CountryList.class);
        this.context = context;
    }

    @Override
    protected CountryList doInBackground() throws Exception {
        ArrayList <Country> countries = new ArrayList<>();
        try {
            countries = JSONUtils.getJSONObjectArray(context, "countries", "country_codes.json", Country.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new CountryList(countries, getDeviceCountryCode());
    }

    private String getDeviceCountryCode() {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return  tm.getNetworkCountryIso();
    }


    public static abstract class GetCountryTaskListener extends ServiceListener<CountryList> {

    }
}
