package com.example.test1.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import com.example.test1.R;
import com.example.test1.base.BaseActivity;
import com.example.test1.databinding.ActivitySplashBinding;
import com.example.test1.ui.profile.ProfileActivity;
import com.example.test1.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity<SplashViewModel, ActivitySplashBinding> {

    private static final String TAG = "zzzzzz";

    @NonNull
    @Override
    protected SplashViewModel createViewModel() {
        SplashViewModelFactory factory = new SplashViewModelFactory();
        return new ViewModelProvider(this, factory).get(SplashViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        // progress bar bat dau chay
        viewModel.startProgressBar();
        // cap nhat gia tri cua progress bar
        viewModel.getIntProgress()
                .observe(this,integer -> {
                    binding.progressBar.setProgress(integer);
                    if (binding.progressBar.getProgress() >= 100){
                        viewModel.dangnhap();
                    }
                });

        // vao login neu chua co thong tin dang nhap
        viewModel.getVaoLoginScreen()
                .observe(this,integer -> {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                });

        // vao home screen neu da dang nhap rồi
        viewModel.getVaoHomeScreen()
                .observe(this,integer -> {
                    startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
                });
    }

    @Override
    protected void initData() {



    }

    @Override
    protected void initListener() {

    }
}