package com.shuwtech.commonsdk.mediator.api;

import com.shuwtech.commonsdk.http.ApiService;
import com.shuwtech.commonsdk.http.response.BaseResponse;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Map;

public interface CommonApi {
    ApiService COMMON_API = new ApiService(Urls.BASE_URL);




}
