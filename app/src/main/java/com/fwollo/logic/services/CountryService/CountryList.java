package com.fwollo.logic.services.CountryService;

import com.fwollo.logic.models.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryList {
    private List<Country> countries = new ArrayList<>();
    private Country selectedCountry;
    private String defaultCountryCode;


    public CountryList(ArrayList<Country> countries, String defaultCountryCode) {
        this.countries = countries;
        this.defaultCountryCode = defaultCountryCode;
        this.selectedCountry = getDefaultCountry();

    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    private Country getDefaultCountry() {
        for (Country country : countries) {
            if (country.getCode().equalsIgnoreCase(defaultCountryCode)) {
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

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
}
