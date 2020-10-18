package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.data.Api;

import javax.inject.Inject;

import retrofit2.Call;

public class InsertNewsRepository {
    private Api api;

    @Inject
    public InsertNewsRepository(Api api) {
        this.api = api;
    }

    public Call<MyResponse> insertNews(String subject,
                                       String tag,
                                       String pic,
                                       String body){
        return api.insertNews(subject,tag,pic,body);
    }
}
