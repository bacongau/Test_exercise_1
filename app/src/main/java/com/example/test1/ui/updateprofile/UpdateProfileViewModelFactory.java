package com.example.test1.ui.updateprofile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UpdateProfileViewModelFactory implements ViewModelProvider.Factory {
    public UpdateProfileViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpdateProfileViewModel.class)){
            return (T) new UpdateProfileViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
