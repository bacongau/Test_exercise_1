package com.example.test1.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.test1.App;
import com.example.test1.base.BaseViewModel;
import com.example.test1.repository.Repository;

public class SplashViewModel extends BaseViewModel {
    private static final String TAG = "zzzzzz";
    private final Repository repository;

    private MutableLiveData<Integer> intProgress = new MutableLiveData<>();
    private MutableLiveData<Integer> vaoLoginScreen = new MutableLiveData<>();
    private MutableLiveData<Integer> vaoHomeScreen = new MutableLiveData<>();


    // getter and setter


    public MutableLiveData<Integer> getVaoHomeScreen() {
        return vaoHomeScreen;
    }

    public MutableLiveData<Integer> getVaoLoginScreen() {
        return vaoLoginScreen;
    }

    public MutableLiveData<Integer> getIntProgress() {
        return intProgress;
    }

    public SplashViewModel() {
        this.repository = new Repository();

    }


    public void dangnhap() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        String header = sharedPreferences.getString("header", "");

        if (header.equalsIgnoreCase("")) {
            // vao login screen
            vaoLoginScreen.setValue(100);
        } else {
            // thuc hien dang nhap
            compositeDisposable.add(repository.login(header)
                    .doOnSubscribe(disposable -> {

                    })
                    .doFinally(() -> {

                    })
                    .subscribe(
                            loginResponse -> {
                                // vao home screen
                                String accessToken = loginResponse.getData().getAccessToken();
                                luuThongTinVaoSharedPreferences(accessToken);

                                vaoHomeScreen.setValue(100);
                            },
                            throwable -> {
                                Log.d(TAG, "dangnhap: " + throwable.getLocalizedMessage());
                                // vao login screen
                                vaoLoginScreen.setValue(100);
                            }
                    ));
        }
    }

    private void luuThongTinVaoSharedPreferences(String accessToken) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
    }

    public void startProgressBar() {
        // progress bar
        final int[] i = {0};
        CountDownTimer countDownTimer = new CountDownTimer(2200, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                i[0] += 10;
                intProgress.setValue(i[0]);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }
}
