package com.example.test1.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.test1.R;
import com.example.test1.base.BaseActivity;
import com.example.test1.databinding.ActivityLoginBinding;
import com.example.test1.ui.profile.ProfileActivity;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    private static final String TAG = "LoginActivity";

    @NonNull
    @Override
    protected LoginViewModel createViewModel() {
        LoginViewModelFactory factory = new LoginViewModelFactory();
        return new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        viewModel.getDangNhap()
                .observe(this,integer -> {
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                });

        // hien loading
        viewModel.getLoading().observe(this,isLoading->{
            if(isLoading != null){
                if(isLoading){
                    loadingDialog.show();
                }else {
                    loadingDialog.hide();
                }
            }
        });

        // setup clear text icon
        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    binding.iconCleartext.setVisibility(View.VISIBLE);
                } else {
                    binding.iconCleartext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.iconCleartext.setOnClickListener(v -> {
            binding.edtEmail.setText("");
        });

        // setup toggle show password
        binding.edtMatkhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    binding.iconShowMatkhau.setVisibility(View.VISIBLE);
                } else {
                    binding.iconShowMatkhau.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean[] a = {true};
        binding.iconShowMatkhau.setOnClickListener(v -> {
            Log.d(TAG, "initView: vxcvxcxv");
            if (a[0]) {
                a[0] = false;
                binding.edtMatkhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            } else {
                a[0] = true;
                binding.edtMatkhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //button dang nhap
        binding.button.setOnClickListener(v -> {
            viewModel.onClickDangNhap(binding.edtEmail.getText().toString().trim(),
                    binding.edtMatkhau.getText().toString().trim());
        });
    }
}