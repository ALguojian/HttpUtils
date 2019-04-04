package com.shuwtech.commonsdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 又拍云返回结果model
 * */
public class UPYunResult {

    @SerializedName("image-type")
    public String imagetype;
    @SerializedName("image-frames")
    public int imageframes;
    @SerializedName("image-height")
    public int imageheight;
    public int code;
    @SerializedName("file_size-height")
    public int fileSize;
    @SerializedName("image-width")
    public int imagewidth;
    public String url;
    public int time;
    public String message;
    public String mimetype;
}