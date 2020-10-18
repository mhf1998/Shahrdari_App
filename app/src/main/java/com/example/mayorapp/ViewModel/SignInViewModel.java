package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.Repository.SignInRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInViewModel extends ViewModel {
    private SignInRepository signInRepository;
    MutableLiveData<MyResponse> mutableLiveData=new MutableLiveData<MyResponse>();
    private static String TAG="SignIn";

    @Inject
    public SignInViewModel(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    public LiveData<MyResponse> signIn(String username, String password){
        signin(username,password);
        return mutableLiveData;
    }

    private void signin(String username, String password) {
       signInRepository.signIn(username,password).enqueue(new Callback<MyResponse>() {
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

        /*MyRetrofit.getInstance().signIn(username,password).enqueue(new Callback<MyResponse>() {
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
