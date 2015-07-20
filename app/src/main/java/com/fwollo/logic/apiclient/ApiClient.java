package com.fwollo.logic.apiclient;

import android.content.Context;

import com.fwollo.logic.services.TagService.TagList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class ApiClient {
    public static final String BASE_URL = "http://api.myservice.com";

    private Context context;
    private ApiInterface apiInterface;

    public ApiClient(Context context) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new MockedClient(context))
                .build();

        apiInterface = restAdapter.create(ApiInterface.class);
    }

    public TagList getTags(String userId) throws RetrofitError {
        return apiInterface.getTags(userId);
    }


}
