package com.shuwtech.commonsdk.http.interceptor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shuwtech.commonsdk.utils.JsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 请求的body转换器，对post或者put请求body转换成json格式，适配服务端
 * */
public class PostBody2JsonInterceptor implements Interceptor {

    private static final String HEADER_CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(transferRequest(chain.request()));
    }

    private Request transferRequest(Request request) {
        if (!request.method().equals("POST")
            && !request.method().equals("PUT")) {
            return request;
        }

        RequestBody requestBody = request.body();
        String body = body2String(requestBody);

        if (TextUtils.isEmpty(body) || JsonUtils.isJSONValid(body)) {
            return request;
        } else {
            Request.Builder builder = request.newBuilder();
            String content = body2JsonString(body);
            RequestBody newBody = RequestBody.create(MEDIA_TYPE_JSON, content);
            if (request.method().equals("PUT")) {
                builder.put(newBody);
            } else {
                builder.post(newBody);
            }

            try {
                builder.removeHeader(HEADER_CONTENT_LENGTH);
                builder.addHeader(HEADER_CONTENT_LENGTH, newBody.contentLength() + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.removeHeader(HEADER_CONTENT_TYPE);
            builder.addHeader(HEADER_CONTENT_TYPE, MEDIA_TYPE_JSON.toString());

            return builder.build();
        }
    }

    private String body2String(RequestBody body) {
        try {
            final Buffer buffer = new Buffer();
            body.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String body2JsonString(String body) {
        if (!body.contains("=")) return body;

        String[] fields = body.split("&");
        Map<String, String> map = new HashMap<>();
        for (String field : fields) {
            String[] kv = field.split("=", 2);
            try {
                String key = URLDecoder.decode(kv[0], "UTF-8");
                String value = URLDecoder.decode(kv[1], "UTF-8");
                map.put(key, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new Gson().toJson(map);
    }
}
