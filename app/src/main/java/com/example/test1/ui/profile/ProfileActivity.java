package com.example.test1.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.test1.R;
import com.example.test1.base.BaseActivity;
import com.example.test1.databinding.ActivityProfileBinding;
import com.example.test1.ui.login.LoginActivity;
import com.example.test1.ui.updateprofile.UpdateProfileActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends BaseActivity<ProfileViewModel, ActivityProfileBinding> {

    String firstname, lastname, gioitinh, email, didong, cmnd, avaUrl;
    long ngaysinh;
    private static final String TAG = "ProfileActivity";

    @NonNull
    @Override
    protected ProfileViewModel createViewModel() {
        ProfileViewModelFactory factory = new ProfileViewModelFactory();
        return new ViewModelProvider(this, factory).get(ProfileViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {

        // get profile
        viewModel.getProfileStudent();

        // logout
        viewModel.getLogout()
                .observe(this, integer -> {
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                });
    }

    @Override
    protected void initData() {
        // set data len view
        viewModel.getDataProfile()
                .observe(this, dataProfile -> {
                    // avatar
                    avaUrl = dataProfile.getAvatarUrl().replace("http://", "https://") + "?rand=" + System.currentTimeMillis();

                    Glide.with(this)
                            .load(avaUrl)
                            .placeholder(R.drawable.logo_placeholder)
                            .into(binding.avatarProfile);

                    // fullname
                    firstname = dataProfile.getFirstName();
                    lastname = dataProfile.getLastName();
                    String ten = firstname + " " + lastname;
                    binding.tenProfile.setText(ten);

                    // ngày sinh
                    ngaysinh = dataProfile.getBirthday();
                    Date d = new Date(ngaysinh * 1000);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                    String birthday = simpleDateFormat.format(d);

                    binding.ngaysinhProfile.setText(birthday);

                    // giới tính
                    if (dataProfile.getGender().equalsIgnoreCase("MALE")) {
                        gioitinh = "Nam";
                    } else {
                        gioitinh = "Nữ";
                    }
                    binding.gioitinhProfile.setText(gioitinh);

                    // email
                    email = dataProfile.getEmail();
                    binding.emailProfile.setText(email);

                    // số điện thoại
                    didong = dataProfile.getPhone();
                    binding.sdtProfile.setText(didong);

                    // cmnd
                    cmnd = dataProfile.getIdentityCard();
                    binding.cmndProfile.setText(cmnd);
                });
    }

    @Override
    protected void initListener() {
        // go to update profile screen
        binding.updateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("firstname", firstname);
            bundle.putString("lastname", lastname);
            bundle.putLong("ngaysinh", ngaysinh);
            bundle.putString("gioitinh", gioitinh);
            bundle.putString("email", email);
            bundle.putString("didong", didong);
            bundle.putString("cmnd", cmnd);
            bundle.putString("avaUrl", avaUrl);
            intent.putExtras(bundle);

            startActivity(intent);
        });

        // onclick logout
        binding.buttonLogout.setOnClickListener(v -> {
            viewModel.onClickLogout();
        });

    }
}