package com.example.loans.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.loans.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteSource {

    private static ApiService API = null;
    private static volatile Retrofit RETROFIT = null;

    private synchronized static Retrofit getRetrofit() {
        if (RETROFIT == null) {
            synchronized (RemoteSource.class) {
                if (RETROFIT == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(loggingInterceptor);
                    }

                    OkHttpClient client  = builder.build();
                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .serializeNulls()
                            .create();

                    RETROFIT = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }
        return RETROFIT;
    }

    private static ApiService initApiService() {
        if (API == null) {
            synchronized (RemoteSource.class) {
                if (API == null) {
                    API = getRetrofit().create(ApiService.class);
                }
            }
        }
        return API;
    }

    public static ApiService getApiService() {
        return initApiService();
    }
}
