package com.example.test1.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.test1.App;
import com.example.test1.base.BaseViewModel;
import com.example.test1.repository.Repository;

import okhttp3.Credentials;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";
    private final Repository repository;

    private ObservableField<Boolean> showIconClearEmail = new ObservableField<>(false);
    private ObservableField<Boolean> showIconTogglePassword = new ObservableField<>(false);

    private MutableLiveData<Integer> dangNhap = new MutableLiveData<>();

    private String email;
    private String matkhau;


    // getter and setter


    public MutableLiveData<Integer> getDangNhap() {
        return dangNhap;
    }

    public ObservableField<Boolean> getShowIconClearEmail() {
        return showIconClearEmail;
    }

    public ObservableField<Boolean> getShowIconTogglePassword() {
        return showIconTogglePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    // constructor
    public LoginViewModel() {
        this.repository = new Repository();
    }


    // method
    public void onClickDangNhap(String email1, String matkhau1) {
        if (email1.isEmpty() || matkhau1.isEmpty()) {
            Toast.makeText(App.getInstance(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String header = Credentials.basic(email1, matkhau1);

        compositeDisposable.add(repository.login(header)
                .doOnSubscribe(disposable -> {
                    // chay progress bar
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    // dung progress bar
                    loading.setValue(false);
                })
                .subscribe(
                        loginResponse -> {
                            Toast.makeText(App.getInstance(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            // luu thong tin vao sharedpreferences
                            String id = loginResponse.getData().getId();
                            String accessToken = loginResponse.getData().getAccessToken();
                            luuVaoSharedPreferences(header,id,accessToken);

                            dangNhap.setValue(100);
                        },
                        throwable -> {
                            Log.d(TAG, "dangnhap: " + throwable.getLocalizedMessage());
                        }
                ));
    }

    public void luuVaoSharedPreferences(String header,String id,String accessToken) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("header",header);
        editor.putString("id",id);
        editor.putString("accessToken",accessToken);
        editor.apply();
    }
}
