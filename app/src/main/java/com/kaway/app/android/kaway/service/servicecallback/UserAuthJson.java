package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

public class UserAuthJson {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public UserAuthJson() {
    }

    public UserAuthJson(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
