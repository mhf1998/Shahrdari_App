package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.data.Api;

import javax.inject.Inject;

import retrofit2.Call;

public class AddSurvey4OpRepository {
    private Api api;

    @Inject
    public AddSurvey4OpRepository(Api api) {
        this.api = api;
    }

    public Call<MyResponse> addSurvey4Op(String u_id,
                                         String subject,
                                         String pic,
                                         String gen,
                                         String op1,
                                         String op2,
                                         String op3,
                                         String op4,
                                         String region)
    {
        return api.addSurvey4Op(u_id,subject,pic,gen,op1,op2,op3,op4,region);
    }

}
