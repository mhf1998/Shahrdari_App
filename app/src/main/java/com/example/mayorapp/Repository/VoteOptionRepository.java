package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.Option;
import com.example.mayorapp.data.Api;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class VoteOptionRepository {
    private Api api;

    @Inject
    public VoteOptionRepository(Api api) {
        this.api = api;
    }

    public Call<List<Option>> getOptions(String v_id) {
        return api.getOptions(v_id);
    }
}
