package com.fwollo.logic.apiclient;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceActivity;

import com.fwollo.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MockedClient implements Client {

    private Context context;

    public MockedClient(Context context) {
        this.context = context;
    }

    @Override
    public Response execute(Request request) throws IOException {

        String url = request.getUrl();

        if (url.contains("/tags")) {
            String jsonString = FileUtils.getStringFromAsset("tags.json", context);
            return new Response(url, 200, "", new ArrayList<Header>(), new TypedByteArray("application/json", jsonString.getBytes()));
        }


        return null;
    }
}
