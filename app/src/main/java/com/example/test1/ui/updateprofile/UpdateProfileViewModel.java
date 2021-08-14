package com.example.test1.ui.updateprofile;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.test1.App;
import com.example.test1.base.BaseViewModel;
import com.example.test1.repository.Repository;
import com.example.test1.utils.RealPath;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateProfileViewModel extends BaseViewModel {
    private static final String TAG = "UpdateProfileViewModel";
    private final Repository repository;

    // constructor
    public UpdateProfileViewModel() {
        this.repository = new Repository();
    }

    // methods
    public void updateProfile(String token, String firstname, String lastname, String gender, String email, String phone, long birthday, String identityCard, Uri uri) {


        // bearer token
        String bearerToken = "Bearer " + token;
        // multipart image
        MultipartBody.Part body = null;
        if (uri != null) {
            String strPath = RealPath.getRealPath(App.getInstance(), uri); // strPath la duong dan cua image
            File file = new File(strPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("avatar", file.getName() + System.currentTimeMillis(), requestFile);
        }else{
            Toast.makeText(App.getInstance(), "Bạn phải chọn 1 ảnh đại diện", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(repository.updateProfile(bearerToken, firstname, lastname, gender, email, phone, birthday, identityCard, body)
                .doOnSubscribe(disposable -> {
                    loading.setValue(true);
                })
                .doFinally(() -> {
                    loading.setValue(false);
                })
                .subscribe(
                        profileResponse -> {
                            Log.d(TAG, "updateProfile: update thanh cong");
                            Toast.makeText(App.getInstance(), "Updated", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Toast.makeText(App.getInstance(), "Lỗi", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "error: " + throwable.getLocalizedMessage());
                        }
                ));
    }
}
