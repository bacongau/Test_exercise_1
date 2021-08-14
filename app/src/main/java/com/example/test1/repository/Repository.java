package com.example.test1.repository;

import com.example.test1.api.ApiClient;
import com.example.test1.api.ApiService;
import com.example.test1.model.response.LoginResponse;
import com.example.test1.model.response.ProfileResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Repository {
    private final ApiService apiService;

    public Repository() {
        this.apiService = ApiClient.getInstance().getApiService();
    }

    public Single<LoginResponse> login(String encodedString) {
        return apiService.login(encodedString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ProfileResponse> getProfile(String bearerToken) {
        return apiService.getProfile(bearerToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ProfileResponse> updateProfile(String bearerToken, String firstname, String lastname, String gender, String email, String phone, long birthday, String identityCard, MultipartBody.Part fileImage) {
        return apiService.updateProfile(bearerToken, firstname, lastname, gender, email, phone, birthday, identityCard, fileImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
