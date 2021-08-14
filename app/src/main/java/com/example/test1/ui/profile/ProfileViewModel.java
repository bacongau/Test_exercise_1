package com.example.test1.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.test1.App;
import com.example.test1.base.BaseViewModel;
import com.example.test1.model.response.ProfileResponse;
import com.example.test1.repository.Repository;

public class ProfileViewModel extends BaseViewModel {
    private static final String TAG = "ProfileViewModel";
    private final Repository repository;

    private MutableLiveData<Integer> logout = new MutableLiveData<>();
    private MutableLiveData<ProfileResponse.DataProfile> dataProfile = new MutableLiveData<>();

    // constructor
    public ProfileViewModel() {
        this.repository = new Repository();
    }

    // getter and setter
    public MutableLiveData<ProfileResponse.DataProfile> getDataProfile() {
        return dataProfile;
    }

    public MutableLiveData<Integer> getLogout() {
        return logout;
    }


    // method
    public void getProfileStudent() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        String bearerToken = "Bearer " + token;
        Log.d(TAG, "getProfileStudent: " + token);

        compositeDisposable.add(repository.getProfile(bearerToken)
                .doOnSubscribe(disposable -> {
                    // chay progress bar
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    // dung progress bar
                    loading.setValue(false);
                })
                .subscribe(
                        profileResponse -> {
                            Log.d(TAG, "getProfileStudent:  get profile thanh cong");
                            dataProfile.setValue(profileResponse.getData());
                        },
                        throwable -> {
                            Log.d(TAG, "getProfileStudent: " + throwable.getLocalizedMessage());
                            Toast.makeText(App.getInstance(), "Token hết hạn", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    public void onClickLogout() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("header");
        editor.remove("id");
        editor.remove("accessToken");
        editor.apply();

        Log.d("TAG", "onClickLogout: sao khong logout");

        logout.setValue(100);
    }
}
