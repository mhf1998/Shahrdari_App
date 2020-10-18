package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.User;
import com.example.mayorapp.Repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    public MutableLiveData<List<User>> mutableLiveData=new MutableLiveData<List<User>>();

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<User>> getUsers(){
        loadListUser();
        return mutableLiveData;
    }


    public void loadListUser(){
        userRepository.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });
        /*MyRetrofit.getInstance().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/
    }

}
