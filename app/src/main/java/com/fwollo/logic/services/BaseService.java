package com.fwollo.logic.services;

import android.content.Context;

import com.fwollo.logic.models.Country;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseService {

    protected Context context;
    protected boolean isReady;

    public void setContext(Context context) {
        this.context = context;
    }

    public BaseService() {

    }

    public void work(ServiceCallBack callBack) {
        isReady = true;
    }

    public void reset() {
        isReady = false;
    }

    public interface ServiceCallBack {
        void onSuccess();
    }

}
