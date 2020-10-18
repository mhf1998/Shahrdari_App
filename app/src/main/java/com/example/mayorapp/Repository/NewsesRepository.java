package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.News;
import com.example.mayorapp.data.Api;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class NewsesRepository {
    private Api api;

    @Inject
    public NewsesRepository(Api api) {
        this.api = api;
    }

    public Call<List<News>> getNewses(String from,String to) {
        return api.getNewses(from,to);
    }
}
