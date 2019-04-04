package com.shuwtech.commonsdk.model;

import com.google.gson.annotations.SerializedName;

public class UPYunToken {
    public String policy;
    public int status;
    @SerializedName("response_time")
    public int responseTime;
    public String operator;
    public String signature;
}