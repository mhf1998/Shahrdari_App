package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.News;
import com.example.mayorapp.Repository.NewsesRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private NewsesRepository newsesRepository;
    public MutableLiveData<List<News>> mutableLiveData=new MutableLiveData<List<News>>();
    public String from="";
    public String to="";

    @Inject
    public NewsViewModel(NewsesRepository newsesRepository) {
        this.newsesRepository = newsesRepository;
    }

    public LiveData<List<News>> getNewses(String from, String to , String key){
        if (key.equals("create")){
             Log.i("create:loadlist", "getNewses: ");
             loadListNews();
        }else if (key.equals("onclick")) {
             loadListNews(from,to);
        }
        return mutableLiveData;
    }


    public void loadListNews(String from, String to){
        this.from=from;
        this.to=to;
        Log.i("newsViewModelClass", "getNewses: ");
        newsesRepository.getNewses(from,to).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });
        /*MyRetrofit.getInstance().getNewses(from,to).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/
    }

    public void loadListNews(){
        Log.i("newsViewModelClass", "getNewses(): ");
        newsesRepository.getNewses(this.from,this.to).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString()+ response.toString());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });

        /*MyRetrofit.getInstance().getNewses(this.from,this.to).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString()+ response.toString());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/
    }
}
