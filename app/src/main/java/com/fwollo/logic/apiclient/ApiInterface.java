package com.fwollo.logic.apiclient;

import com.fwollo.logic.services.TagService.TagList;

import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiInterface {
    @GET("/tags")
    TagList getTags(@Query("userId") String userId
    ) throws RetrofitError;
}
