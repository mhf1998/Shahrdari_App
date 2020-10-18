package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.data.Api;

import javax.inject.Inject;

import retrofit2.Call;

public class AddSurvey2OpPicRepository {
    private Api api;

    @Inject
    public AddSurvey2OpPicRepository(Api api) {
        this.api = api;
    }

    public Call<MyResponse> addSurvey2OpPic(String u_id,
                                            String subject,
                                            String pic,
                                            String gen,
                                            String op1,
                                            String op2,
                                            String region)
    {
        return api.addSurvey2OpPic(u_id,subject,pic,gen,op1,op2,region);
    }

}
