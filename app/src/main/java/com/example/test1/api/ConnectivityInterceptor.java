package com.example.test1.api;

import android.content.Context;

import com.example.test1.exception.NoInternetConnectionException;
import com.example.test1.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor{
    private Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if(!Utils.checkNetwork(context)) {
            throw new NoInternetConnectionException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
