package com.example.educationalgorithms;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPRequest {
    private String prefix;
    public PHPRequest(String p){
        prefix = p;
    }
    public void doRequest(Activity a, String file, ContentValues params, RequestHandler rh) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (params != null && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    requestBody.add(key, params.getAsString(key));
                }
            }
        }

        Request request = new Request.Builder().url(prefix + file).post(requestBody.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    final String responseData = response.body().string();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rh.processResponse(responseData);
                        }
                    });
                }
            }
        });
    }
}
