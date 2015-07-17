package com.fwollo.logic.services;

import com.fwollo.logic.models.Country;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TagService extends BaseService {

    private List<Country> tags = new ArrayList<>();

    public TagService() {
       super();
    }


    public void update(ServiceCallBack callBack) {
        try {
            tags = JSONUtils.getJSONObjectArray(context, "countries", "country_codes.json", Country.class);
            isReady = true;
            callBack.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        isReady = false;
        tags = null;
    }

    public List<Country> getTags() {
        return tags;
    }
}
