package com.fwollo.logic.models;

public class Country {
private  String code;

    private String id;
    private String phoneCode;
    private String name;
    private String isDefault;

    public boolean isDefault() {
        return "yes".equalsIgnoreCase(isDefault);
    }

    public String getCode() {
        return code;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getName() {
        return name;
    }
}
