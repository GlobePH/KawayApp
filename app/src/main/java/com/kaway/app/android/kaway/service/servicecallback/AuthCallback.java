package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

public class AuthCallback {
    @SerializedName("success")
    public boolean success;

    @SerializedName("key")
    public String key;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
