package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.data.Api;

import javax.inject.Inject;

import retrofit2.Call;

public class AddSurvey2OpRepository {
    private Api api;

    @Inject
    public AddSurvey2OpRepository(Api api) {
        this.api = api;
    }

    public Call<MyResponse> addSurvey2Op(String u_id,
                                         String subject,
                                         String pic,
                                         String gen,
                                         String op1,
                                         String op2,
                                         String region)
    {
        return api.addSurvey2Op(u_id,subject,pic,gen,op1,op2,region);
    }
}
