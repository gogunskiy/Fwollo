package com.fwollo.logic.services.TagService;

import com.fwollo.logic.apiclient.ApiClient;
import com.fwollo.logic.models.Country;
import com.fwollo.logic.services.BaseService;
import com.fwollo.logic.services.ServiceListener;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TagService extends BaseService <TagList, TagService.GetTagTaskListener> {

    private String userId;

    public TagService(ApiClient apiClient, String userId) {
        super(TagList.class);
        this.apiClient = apiClient;
        this.userId = userId;
    }

    @Override
    protected TagList doInBackground() throws Exception {
        return apiClient.getTags(userId);
    }

    public static abstract class GetTagTaskListener extends ServiceListener<TagList> {

    }
}
