package com.example.mayorapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mayorapp.Model.Option;
import com.example.mayorapp.Repository.VoteOptionRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OptionViewModel extends ViewModel {
    private VoteOptionRepository voteOptionRepository;
    public MutableLiveData<List<Option>> mutableLiveData=new MutableLiveData<List<Option>>();
    public String v_id="";

    @Inject
    public OptionViewModel(VoteOptionRepository voteOptionRepository) {
        this.voteOptionRepository = voteOptionRepository;
    }

    public LiveData<List<Option>> getOptions(String v_id){
        Log.i("create:loadlist", "getOptions: ");
        loadListOption(v_id);

        return mutableLiveData;
    }


    public void loadListOption(String v_id){
        this.v_id=v_id;
        Log.i("optionViewModelClass", "getoptions: ");
       voteOptionRepository.getOptions(v_id).enqueue(new Callback<List<Option>>() {
            @Override
            public void onResponse(Call<List<Option>> call, Response<List<Option>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Option>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });

        /*MyRetrofit.getInstance().getOptions(v_id).enqueue(new Callback<List<Option>>() {
            @Override
            public void onResponse(Call<List<Option>> call, Response<List<Option>> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ", "onResponse: "+String.valueOf(response.code()));
                    return;
                }
                mutableLiveData.setValue(response.body());
                Log.i("onResponse",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Option>> call, Throwable t) {
                Log.e("onFailure-retrofit", t.getMessage() );
            }
        });*/
    }


}
