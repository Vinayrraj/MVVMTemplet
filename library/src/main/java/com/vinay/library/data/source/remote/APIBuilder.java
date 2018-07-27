package com.vinay.library.data.source.remote;


import com.vinay.library.R;
import com.vinay.library.app.LibraryApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 28-03-2018.
 */

public class APIBuilder {

    private static APIBuilder _INSTANCE;

    private APIService service;

    private APIBuilder() {
        /* IGNORED */
    }

    public synchronized static APIBuilder getBuilder() {
        if (_INSTANCE == null) {
            _INSTANCE = new APIBuilder();
        }
        return _INSTANCE;
    }

    public APIService getService() {
        if (service == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(300, TimeUnit.SECONDS);
            clientBuilder.readTimeout(300, TimeUnit.SECONDS);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);

            // client.interceptors().add(new LoggingInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LibraryApplication.getContext().getString(R.string.api_base))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
            service = retrofit.create(APIService.class);
        }
        return service;
    }
}
