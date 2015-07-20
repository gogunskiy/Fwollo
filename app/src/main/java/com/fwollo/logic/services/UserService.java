package com.fwollo.logic.services;

import com.fwollo.logic.models.User;

public class UserService extends BaseService <User, UserService.GetUserTaskListener> {

    public UserService() {
        super(User.class);
    }

    @Override
    protected User doInBackground() throws Exception {

        User currentUser;

        currentUser = new User();
        currentUser.setId("1");
        currentUser.setFirstName("John");
        currentUser.setLastName("Smith");
        currentUser.setNickName("napoleon");

        return currentUser;
    }


    public static abstract class GetUserTaskListener extends ServiceListener<User> {

    }
}
