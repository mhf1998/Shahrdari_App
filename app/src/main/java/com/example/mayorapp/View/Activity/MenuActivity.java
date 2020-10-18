package com.example.mayorapp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayorapp.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;

    public Bundle bundle;
    private String TAG="MENU ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbinding
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();
        bundle=new Bundle();
        final String username = getIntent().getStringExtra("user");
        Log.i(TAG, "onCreate: "+ username);

        binding.addSurveyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this , AddSurvey_Activity.class));
            }
        });

        binding.showSurveyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this , SurveyList_Activity.class));
            }
        });

        binding.userMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuActivity.this , Users_Activity.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });

        binding.newsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this , news_Activity.class));
            }
        });


    }
}