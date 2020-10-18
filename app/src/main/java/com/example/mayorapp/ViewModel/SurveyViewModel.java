package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.Repository.getSurveyRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyViewModel extends ViewModel {
    private getSurveyRepository getSurveyRepository;
    public MutableLiveData<List<Survey>> mutableLiveData=new MutableLiveData<List<Survey>>();
    public List<Survey> surveyList1=new ArrayList<Survey>();
    public List<Survey> surveyList2=new ArrayList<Survey>();
    static public String from="";
    static public String to="";
    public String generic="1";

    @Inject
    public SurveyViewModel(com.example.mayorapp.Repository.getSurveyRepository getSurveyRepository) {
        this.getSurveyRepository = getSurveyRepository;
    }

    public LiveData<List<Survey>> getSurveys(String generic, String from, String to , String key){
        if (key.equals("create")){
            Log.i("create:loadlist", "getSurveys: ");
            loadListSurvey();
        }else if (key.equals("onclick")) {
            loadListSurvey(generic,from,to);
        }
        return mutableLiveData;
    }


    public void loadListSurvey(String generic,String from, String to){
        this.from=from;
        this.to=to;
        this.generic=generic;
        Log.i("surveyViewModelClass", "getsurveys: ");
        if (generic.equals("2")==false) {
            getSurveyRepository.getSurveys(generic, from, to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    mutableLiveData.setValue(response.body());
                    Log.i("onResponse", response.body().toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });
        }
        else {
            //final boolean key=false;
            getSurveyRepository.getSurveys("0", from, to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    surveyList1=response.body();
                    getSurveyRepository.getSurveys("1", SurveyViewModel.from,SurveyViewModel.to).enqueue(new Callback<List<Survey>>() {
                        @Override
                        public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                            if (!response.isSuccessful()) {
                                Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                                return;
                            }
                            surveyList2=response.body();
                            //mutableLiveData.setValue(response.body());
                            for (Survey survey: surveyList2){
                                surveyList1.add(survey);
                            }
                            mutableLiveData.setValue(surveyList1);
                            Log.i("onResponse", response.body().toString() + response.toString());
                        }

                        @Override
                        public void onFailure(Call<List<Survey>> call, Throwable t) {
                            Log.e("onFailure-retrofit", t.getMessage());
                        }
                    });

                    //mutableLiveData.setValue(surveyList1);
                    Log.i("onResponse", response.body().toString() + response.toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });
            /*getSurveyRepository.getSurveys("1", from, to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    surveyList2=response.body();
                    for (Survey survey:surveyList2){
                        surveyList1.add(survey);
                    }

                    mutableLiveData.setValue(surveyList1);
                    Log.i("onResponse", response.body().toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });*/

        }
        }

        /*MyRetrofit.getInstance().getSurveys(generic,from,to).enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/


    public void loadListSurvey(){
        Log.i("surveyViewModelClass", "getSurveys(): ");
        if (this.generic.equals("2")==false) {
            getSurveyRepository.getSurveys(this.generic, this.from, this.to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    mutableLiveData.setValue(response.body());
                    Log.i("onResponse", response.body().toString() + response.toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });
        }
        else {
            getSurveyRepository.getSurveys("0", this.from, this.to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    surveyList1=response.body();
                    getSurveyRepository.getSurveys("1", SurveyViewModel.from,SurveyViewModel.to).enqueue(new Callback<List<Survey>>() {
                        @Override
                        public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                            if (!response.isSuccessful()) {
                                Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                                return;
                            }
                            surveyList2=response.body();
                            //mutableLiveData.setValue(response.body());
                            for (Survey survey: surveyList2){
                                surveyList1.add(survey);
                            }
                            mutableLiveData.setValue(surveyList1);
                            Log.i("onResponse", response.body().toString() + response.toString());
                        }

                        @Override
                        public void onFailure(Call<List<Survey>> call, Throwable t) {
                            Log.e("onFailure-retrofit", t.getMessage());
                        }
                    });

                    //mutableLiveData.setValue(surveyList1);
                    Log.i("onResponse", response.body().toString() + response.toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });
            /*getSurveyRepository.getSurveys("1", this.from, this.to).enqueue(new Callback<List<Survey>>() {
                @Override
                public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Code: ", "onResponse: " + String.valueOf(response.code()));
                        return;
                    }
                    surveyList2=response.body();
                    //mutableLiveData.setValue(response.body());
                    for (Survey survey: surveyList2){
                        surveyList1.add(survey);
                    }
                    mutableLiveData.setValue(surveyList1);
                    Log.i("onResponse", response.body().toString() + response.toString());
                }

                @Override
                public void onFailure(Call<List<Survey>> call, Throwable t) {
                    Log.e("onFailure-retrofit", t.getMessage());
                }
            });*/

        }

        /*MyRetrofit.getInstance().getSurveys(this.generic,this.from,this.to).enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString()+ response.toString());
            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/
    }
}
