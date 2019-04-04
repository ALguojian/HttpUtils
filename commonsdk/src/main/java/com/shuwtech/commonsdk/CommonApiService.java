package com.shuwtech.commonsdk;

import com.shuwtech.commonsdk.http.ApiService;
import com.shuwtech.commonsdk.http.response.BaseResponse;
import com.shuwtech.commonsdk.mediator.api.Urls;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

/**
 *common api
 *
 * @author alguojian
 * @date 2019/1/2
 */
public interface CommonApiService {

    CommonApiService INSTANCE = new ApiService(Urls.BASE_URL).create(CommonApiService.class);

    /**
     * 我的收藏
     */
    @POST("1_4/ActivityListUser.aspx")
    public Flowable<BaseResponse<List<ListBean>>> myCollectionActivityList(@Body Map<String, String> map);
}
