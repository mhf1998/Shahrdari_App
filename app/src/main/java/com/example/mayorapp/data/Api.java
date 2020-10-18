package com.example.mayorapp.data;

import com.example.mayorapp.Model.News;
import com.example.mayorapp.Model.Option;
import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET(serverConstants.getNewsFromTo)
    Call<List<News>> getNewses(@Query("from") String from , @Query("to")String to);

    @GET(serverConstants.getSurveyFromTo)
    Call<List<Survey>> getSurveys(@Query("generic")String generic, @Query("from") String from, @Query("to") String to);

    @GET(serverConstants.getVoteOptionParticipate)
    Call<List<Option>> getOptions(@Query("v_id")String v_id);

    @GET(serverConstants.getUsers)
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST(serverConstants.addSurvey2op_URL)
    Call<MyResponse> addSurvey2Op(
            @Field("u_id") String u_id,
            @Field("subject")String subject,
            @Field("pic")String pic,
            @Field("gen")String gen,
            @Field("op1")String op1,
            @Field("op2")String op2,
            @Field("region")String region
            );
    @FormUrlEncoded
    @POST(serverConstants.addSurvey4op_URL)
    Call<MyResponse> addSurvey4Op(
            @Field("u_id") String u_id,
            @Field("subject")String subject,
            @Field("pic")String pic,
            @Field("gen")String gen,
            @Field("op1")String op1,
            @Field("op2")String op2,
            @Field("op3")String op3,
            @Field("op4")String op4,
            @Field("region")String region
    );
    @FormUrlEncoded
    @POST(serverConstants.addSurvey2opPIC_URL)
    Call<MyResponse> addSurvey2OpPic(
            @Field("u_id") String u_id,
            @Field("subject")String subject,
            @Field("pic")String pic,
            @Field("gen")String gen,
            @Field("op1")String op1,
            @Field("op2")String op2,
            @Field("region")String region
    );
    @FormUrlEncoded
    @POST(serverConstants.addSurvey4opPIC_URL)
    Call<MyResponse> addSurvey4OpPic(
            @Field("u_id") String u_id,
            @Field("subject")String subject,
            @Field("pic")String pic,
            @Field("gen")String gen,
            @Field("op1")String op1,
            @Field("op2")String op2,
            @Field("op3")String op3,
            @Field("op4")String op4,
            @Field("region")String region
    );
    @FormUrlEncoded
    @POST(serverConstants.insertNews)
    Call<MyResponse> insertNews(
            @Field("subject") String subject,
            @Field("tag")String tag,
            @Field("pic")String pic,
            @Field("body")String body
    );


    @GET(serverConstants.SignIn_URL)
    Call<MyResponse> signIn(
            @Query("username") String username,
            @Query("password") String password
    );
}
