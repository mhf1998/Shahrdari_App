package com.example.mayorapp.DI.Modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mayorapp.DI.ViewModelKey;
import com.example.mayorapp.ViewModel.AddSurveyViewModel;
import com.example.mayorapp.ViewModel.InsertNewsViewModel;
import com.example.mayorapp.ViewModel.NewsViewModel;
import com.example.mayorapp.ViewModel.OptionViewModel;
import com.example.mayorapp.ViewModel.SignInViewModel;
import com.example.mayorapp.ViewModel.SurveyViewModel;
import com.example.mayorapp.ViewModel.UserViewModel;
import com.example.mayorapp.ViewModel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUsersViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindNewsesViewModel(NewsViewModel newsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SurveyViewModel.class)
    abstract ViewModel bindSurveyViewModel(SurveyViewModel surveyViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddSurveyViewModel.class)
    abstract ViewModel bindAddSurveyViewModel(AddSurveyViewModel addSurveyViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(InsertNewsViewModel.class)
    abstract ViewModel bindInsertNewsViewModel(InsertNewsViewModel insertNewsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OptionViewModel.class)
    abstract ViewModel bindOptionViewModel(OptionViewModel optionViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInViewModel(SignInViewModel signInViewModel);





    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);
}
