package br.ufrn.imd.groupapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {

    private Retrofit retrofit;

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/").
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public GroupAppService groupAppService() {
        return retrofit.create(GroupAppService.class);
    }
}
