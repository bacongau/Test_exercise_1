package com.example.test1.base;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.test1.custom.LoadingDialog;

/**
 * Created by Kidd on 07/01/2019.
 */
public abstract class BaseActivity<VM extends BaseViewModel,T extends ViewDataBinding> extends AppCompatActivity {

    protected VM viewModel;
    protected T binding;
    protected LoadingDialog loadingDialog;
    protected SharedPreferences sharedPreferences;

    @NonNull
    protected abstract VM createViewModel();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        viewModel = createViewModel();
        loadingDialog = new LoadingDialog(this);
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();

}
