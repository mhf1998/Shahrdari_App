package com.example.mayorapp.Repository;

import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.data.Api;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class getSurveyRepository {
    private Api api;

    @Inject
    public getSurveyRepository(Api api) {
        this.api = api;
    }

    public Call<List<Survey>> getSurveys(String generic,String from, String to) {
        return api.getSurveys(generic,from,to);
    }
}
