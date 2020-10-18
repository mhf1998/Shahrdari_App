package com.example.mayorapp.DI.Modules;

import com.example.mayorapp.data.Api;
import com.example.mayorapp.data.serverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public abstract class NetworkModule {
    @Provides
    @Singleton
    static Retrofit provideRetrofit(){
        Gson gson=new GsonBuilder()
                .setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(serverConstants.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Provides
    @Singleton
    static Api provideService(Retrofit retrofit){
        return retrofit.create(Api.class);
    }

}
