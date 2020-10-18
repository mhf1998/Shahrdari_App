package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.data.Api;

import javax.inject.Inject;

import retrofit2.Call;

public class SignInRepository {
    private Api api;

    @Inject
    public SignInRepository(Api api) {
        this.api = api;
    }

    public Call<MyResponse> signIn(String username, String password){
        return api.signIn(username,password);
    }
}
