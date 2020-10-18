package com.example.mayorapp.DI.Components;

import com.example.mayorapp.DI.Modules.ContextModule;
import com.example.mayorapp.DI.Modules.NetworkModule;
import com.example.mayorapp.View.Activity.AddSurvey_Activity;
import com.example.mayorapp.View.Activity.List_News_Activity;
import com.example.mayorapp.View.Activity.MainActivity;
import com.example.mayorapp.View.Activity.SurveyList_Activity;
import com.example.mayorapp.View.Activity.Users_Activity;
import com.example.mayorapp.View.Activity.news_Activity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {
    void inject(Users_Activity users_activity);

    void inject(List_News_Activity listNewsActivity);

    void inject(MainActivity mainActivity);

    void inject(SurveyList_Activity surveyList_activity);

    void inject(AddSurvey_Activity addSurvey_activity);

    void inject (news_Activity news_activity);

}
