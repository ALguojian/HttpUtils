package com.shuwtech.commonsdk.mediator.event;

public class WeexRefreshEvent {
    public static final String KEY_WEEX_REFRESH = "key_weex_refresh";

    public String key;
    public String[] urls;

    public WeexRefreshEvent(String key,String[] urls) {
        this.key = key;
        this.urls = urls;
    }
}
