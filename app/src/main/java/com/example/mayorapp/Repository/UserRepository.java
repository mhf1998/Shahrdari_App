package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.User;
import com.example.mayorapp.data.Api;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class UserRepository {
    private Api api;

    @Inject
    public UserRepository(Api api) {
        this.api = api;
    }

    public Call<List<User>> getUsers(){
        return api.getUsers();
    }
}
