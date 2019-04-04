package com.shuwtech.commonsdk.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 收获地址model
 * */
public class OrderPosition implements Serializable {
    public String area;
    public String city;
    public String province;
    public String address;
    public String consignee;
    public String mobile;

    public String getWholeAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(province).append(city);
        if (!TextUtils.isEmpty(area)) sb.append(area);
        sb.append(address);
        return sb.toString();
    }
}
