package com.fwollo.logic.services;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.fwollo.logic.models.Country;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryService extends BaseService {

    private List<Country> countries = new ArrayList<>();
    private Country selectedCountry;

    public CountryService() {
       super();
    }


    public void update(ServiceCallBack callBack) {
        try {
            countries = JSONUtils.getJSONObjectArray(context, "countries", "country_codes.json", Country.class);
            selectedCountry = getDefaultCountry();
            isReady = true;
            callBack.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Country getDefaultCountry() {
        String deviceCountryCode = getDeviceCountryCode();
        for (Country country : countries) {
            if (country.getCode().equalsIgnoreCase(deviceCountryCode)) {
                return country;
            }
        }

        return getCountryMarkedAsDefault();
    }

    private Country getCountryMarkedAsDefault() {
        for (Country country : countries) {
            if (country.isDefault()) {
                return country;
            }
        }

        return null;
    }


    public void reset() {
        isReady = false;
        countries = null;
    }


    public List<Country> getCountries() {
        return countries;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }


    private String getDeviceCountryCode() {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return  tm.getNetworkCountryIso();
    }
}
