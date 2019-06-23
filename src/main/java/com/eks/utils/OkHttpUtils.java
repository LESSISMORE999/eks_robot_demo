package com.eks.utils;

import com.google.gson.JsonElement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class OkHttpUtils {
    private static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    public static JsonElement sendRequest(String urlString) throws IOException {
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        ResponseBody responseBody = response.body();
        if(responseBody == null){
            throw new RuntimeException("Response is empty.");
        }
        String responseBodyString = responseBody.string();
        return GsonUtils.convertStringToJsonElement(responseBodyString);
    }
}
