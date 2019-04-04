package com.shuwtech.commonsdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @SerializedName("_id")
    public String id;
}
