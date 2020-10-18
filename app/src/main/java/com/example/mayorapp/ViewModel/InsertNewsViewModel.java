package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.Repository.InsertNewsRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertNewsViewModel extends ViewModel {
    private InsertNewsRepository insertNewsRepository;
    MutableLiveData<MyResponse> mutableLiveData=new MutableLiveData<MyResponse>();
    private static String TAG="insertNews";

    @Inject
    public InsertNewsViewModel(InsertNewsRepository insertNewsRepository) {
        this.insertNewsRepository = insertNewsRepository;
    }

    public LiveData<MyResponse> insertNews(String subject, String tag, String pic, String body){
        insertNewsToServer(subject,tag,pic,body);
        return mutableLiveData;
    }

    private void insertNewsToServer(String subject, String tag, String pic, String body) {
       insertNewsRepository.insertNews(subject,tag,pic,body).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: "+response.toString() );
                    return;
                }
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

        /*MyRetrofit.getInstance().insertNews(subject,tag,pic,body).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: "+response.toString() );
                    return;
                }
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });*/
    }
}
