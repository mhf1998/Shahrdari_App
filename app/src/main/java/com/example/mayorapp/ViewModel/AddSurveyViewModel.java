package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.Repository.AddSurvey2OpPicRepository;
import com.example.mayorapp.Repository.AddSurvey2OpRepository;
import com.example.mayorapp.Repository.AddSurvey4OpPicRepository;
import com.example.mayorapp.Repository.AddSurvey4OpRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSurveyViewModel extends ViewModel {
    private AddSurvey2OpRepository addSurvey2OpRepository;
    private AddSurvey4OpRepository addSurvey4OpRepository;
    private AddSurvey2OpPicRepository addSurvey2OpPicRepository;
    private AddSurvey4OpPicRepository addSurvey4OpPicRepository;
    public MutableLiveData<MyResponse> mutableLiveData=new MutableLiveData<MyResponse>();
    private static String TAG="AddSurveyViewModel";

    @Inject
    public AddSurveyViewModel(AddSurvey2OpRepository addSurvey2OpRepository, AddSurvey4OpRepository addSurvey4OpRepository, AddSurvey2OpPicRepository addSurvey2OpPicRepository, AddSurvey4OpPicRepository addSurvey4OpPicRepository) {
        this.addSurvey2OpRepository = addSurvey2OpRepository;
        this.addSurvey4OpRepository = addSurvey4OpRepository;
        this.addSurvey2OpPicRepository = addSurvey2OpPicRepository;
        this.addSurvey4OpPicRepository = addSurvey4OpPicRepository;
    }



    public LiveData<MyResponse> addsurvey(int key, String u_id, String subject, String pic, String gen, String op1, String op2, String op3, String op4, String region){
        if(key==1) {
            addSurvey2Op(u_id,subject,pic,gen,op1,op2,region);
        }else if (key==2){
            addSurvey4Op(u_id,subject,pic,gen,op1,op2,op3,op4,region);
        }else if (key==3) {
            addSurvey2OpPic(u_id,subject,pic,gen,op1,op2,region);
        }else if (key==4) {
            addSurvey4OpPic(u_id,subject,pic,gen,op1,op2,op3,op4,region);
        }
        return mutableLiveData;
    }

    private void addSurvey4OpPic(String u_id, String subject, String pic, String gen, String op1, String op2, String op3, String op4, String region) {
        addSurvey4OpPicRepository.addSurvey4OpPic(u_id,subject,pic,gen,op1,op2,op3,op4,region).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: ");
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i(TAG, "onResponse: "+response.message() + "  " + response.toString() + response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

            }
        });
        /*
        MyRetrofit.getInstance().addSurvey4OpPic(u_id,subject,pic,gen,op1,op2,op3,op4,region).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: ");
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i(TAG, "onResponse: "+response.message() + "  " + response.toString() + response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

            }
        });*/
    }

    private void addSurvey2OpPic(String u_id, String subject, String pic, String gen, String op1, String op2, String region) {
        addSurvey2OpPicRepository.addSurvey2OpPic(u_id,subject,pic,gen,op1,op2,region).enqueue(new Callback<MyResponse>() {
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
                Log.e(TAG, "onFailure: ", t);
            }
        });
        /*MyRetrofit.getInstance().addSurvey2OpPic(u_id,subject,pic,gen,op1,op2,region).enqueue(new Callback<MyResponse>() {
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

            }
        });*/

    }


    private void addSurvey4Op(String u_id, String subject, String pic, String gen, String op1, String op2, String op3, String op4, String region) {
        addSurvey4OpRepository.addSurvey4Op(u_id,subject,pic,gen,op1,op2,op3,op4,region).enqueue(new Callback<MyResponse>() {
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
                Log.e(TAG, "onFailure: ", t);
            }
        });

        /*MyRetrofit.getInstance().addSurvey4Op(u_id,subject,pic,gen,op1,op2,op3,op4,region).enqueue(new Callback<MyResponse>() {
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

            }
        });*/

    }

    private void addSurvey2Op(String u_id, String subject, String pic, String gen, String op1, String op2, String region) {

        addSurvey2OpRepository.addSurvey2Op(u_id,subject,pic,gen,op1,op2,region).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: ");
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i(TAG, "onResponse: "+response.message() + "  " + response.toString() + response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

            }
        });
        /*MyRetrofit.getInstance().addSurvey2Op(u_id,subject,pic,gen,op1,op2,region).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG, "onResponse: ");
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i(TAG, "onResponse: "+response.message() + "  " + response.toString() + response.body());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

            }
        });*/
    }
}
