package com.fwollo.logic.services;

import android.content.Context;

import com.fwollo.logic.models.Country;
import com.fwollo.logic.models.User;
import com.fwollo.utils.JSONUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UserService extends BaseService {

    private User currentUser;

    public UserService() {
        super();

        currentUser = new User();
        currentUser.setId("1");
        currentUser.setFirstName("John");
        currentUser.setLastName("Smith");
        currentUser.setNickName("napoleon");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
