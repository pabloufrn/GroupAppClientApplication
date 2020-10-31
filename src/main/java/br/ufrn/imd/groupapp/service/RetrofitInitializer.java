package br.ufrn.imd.groupapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {

    private final Retrofit retrofit;

    public RetrofitInitializer() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/").
                addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public GroupAppService groupAppService() {
        return retrofit.create(GroupAppService.class);
    }
}
