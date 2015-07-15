package com.fwollo.utils;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    static public <T> T  getObjectFromString(String jsonString,  Class<T> classOfT)
    {
        Gson gson = new Gson();
        T object = gson.fromJson(jsonString, classOfT);

        return object;
    }

    static public String getStringFromObject(Object object) {

        Gson gson = new Gson();
        String jsonRepresentation = gson.toJson(object);
        return jsonRepresentation;
    }

    static public <T> List<T> getJSONObjectArray(Context context, String key, String asset,  Class<T> classOfT) throws JSONException {
        String jsonString = FileUtils.getStringFromAsset(asset, context);
        JSONArray jsonArray = new JSONObject(jsonString).getJSONArray(key);
        List<T> array = new ArrayList<>(jsonArray.length());
        for(int i=0; i < jsonArray.length(); i++) {
            array.add(getObjectFromString(jsonArray.getString(i), classOfT));
        }
        return array;
    }

}
