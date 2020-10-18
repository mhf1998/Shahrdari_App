package com.example.mayorapp.data;

import android.util.Log;

import com.example.mayorapp.Model.News;
import com.example.mayorapp.Model.Option;
import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private static final String baseUrl=serverConstants.ROOT_URL;
    private Api api;
    private static MyRetrofit myRetrofit;

    public MyRetrofit() {
        Gson gson=new GsonBuilder()
                .setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api=retrofit.create(Api.class);
    }

    public static MyRetrofit getInstance(){
        if (myRetrofit == null) {
            myRetrofit=new MyRetrofit();
        }
        return myRetrofit;
    }

    public Call<List<News>> getNewses(String from, String to){
        Log.i("retrofit-get-news", "getNewses: class myRetrofit");
        return api.getNewses(from,to);
    }

    public Call<List<Survey>> getSurveys(String generic , String from , String to){
        return api.getSurveys(generic,from,to);
    }

    public Call<List<User>> getUsers(){
        return api.getUsers();
    }

    public Call<List<Option>> getOptions(String v_id){
        return api.getOptions(v_id);
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
    public Call<MyResponse> addSurvey4OpPic(String u_id,
                                            String subject,
                                            String pic,
                                            String gen,
                                            String op1,
                                            String op2,
                                            String op3,
                                            String op4,
                                            String region)
    {
        return api.addSurvey4OpPic(u_id,subject,pic,gen,op1,op2,op3,op4,region);
    }

    public Call<MyResponse> insertNews(String subject,
                                       String tag,
                                       String pic,
                                       String body){
        return api.insertNews(subject,tag,pic,body);
    }

    public Call<MyResponse> signIn(String username,String password){
        return api.signIn(username,password);
    }

}
