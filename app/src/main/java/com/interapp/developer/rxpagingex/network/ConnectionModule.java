package com.interapp.developer.rxpagingex.network;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Preethi Valsalan on 2019-08-18
 * Class which provides the all nework related methods DI
 */
@Module
public class ConnectionModule {

    private String apiUrl;

    public ConnectionModule(String api) {
        this.apiUrl = api;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addNetworkInterceptor(logging);
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideApiRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    IFlickrService getFlickerService(Retrofit retrofit) {
        return  retrofit.create(IFlickrService.class);
    }
}
