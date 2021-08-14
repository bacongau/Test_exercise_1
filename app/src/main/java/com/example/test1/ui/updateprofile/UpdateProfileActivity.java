package com.example.test1.ui.updateprofile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.test1.App;
import com.example.test1.R;
import com.example.test1.base.BaseActivity;
import com.example.test1.databinding.ActivityUpdateProfileBinding;
import com.example.test1.ui.profile.ProfileActivity;
import com.example.test1.utils.RequestPermission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UpdateProfileActivity extends BaseActivity<UpdateProfileViewModel, ActivityUpdateProfileBinding> {

    private static final String TAG = "UpdateProfileActivity";
    private String token, firstname, lastname, gioitinh, email, didong, cmnd, avaUrl;
    private long ngaysinh;
    private Long sdf;
    private List<String> list;
    private Uri mUri;

    @NonNull
    @Override
    protected UpdateProfileViewModel createViewModel() {
        UpdateProfileViewModelFactory factory = new UpdateProfileViewModelFactory();
        return new ViewModelProvider(this, factory).get(UpdateProfileViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void initView() {
        // request permission
        RequestPermission requestPermission = new RequestPermission();
        requestPermission.requestPermission(UpdateProfileActivity.this);

        // get data
        Bundle bundle = getIntent().getExtras();
        firstname = bundle.getString("firstname");
        lastname = bundle.getString("lastname");
        ngaysinh = bundle.getLong("ngaysinh");
        gioitinh = bundle.getString("gioitinh");
        email = bundle.getString("email");
        didong = bundle.getString("didong");
        cmnd = bundle.getString("cmnd");
        avaUrl = bundle.getString("avaUrl");

        // chon gioi tinh
        list = new ArrayList<>();
        list.add("Nam");
        list.add("Nữ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        binding.spinnerGioitinhUpdate.setAdapter(arrayAdapter);

        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("Login_info_2", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("accessToken", "");

        // show loading
        viewModel.getLoading()
                .observe(this, isLoading -> {
                    if (isLoading != null) {
                        if (isLoading) {
                            loadingDialog.show();
                        } else {
                            loadingDialog.hide();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        // set data student lên view
        binding.edtUpdateFirstname.setText(firstname);
        binding.edtUpdateLastname.setText(lastname);
        binding.edtEmailUpdate.setText(email);
        binding.edtDidongUpdate.setText(didong);
        binding.edtCmndUpdate.setText(cmnd);

        Date d = new Date(ngaysinh * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        String birthday = simpleDateFormat.format(d);
        binding.edtNgaysinhUpdate.setText(birthday);

        if (gioitinh.equals("Nam")) {
            binding.spinnerGioitinhUpdate.setSelection(0);
        } else {
            binding.spinnerGioitinhUpdate.setSelection(1);
        }

        Glide.with(this)
                .load(avaUrl)
                .placeholder(R.drawable.logo_placeholder)
                .into(binding.avatarProfile);
    }

    @Override
    protected void initListener() {
        // back về profile screen
        binding.backProfile.setOnClickListener(v -> {
            startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
        });

        // click update profile
        binding.confirmUpdate.setOnClickListener(v -> {
            showConfirm();
        });

        // chọn ảnh
        binding.imgSelectImage.setOnClickListener(v -> {
            chooseNewAvatar();
        });

        // datepickerdialog
        binding.edtNgaysinhUpdate.setOnClickListener(v -> {
            pickDate();
        });
    }

    private void pickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.edtNgaysinhUpdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, 2000, 0, 1);
        datePickerDialog.show();
    }

    private void chooseNewAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }

                        mUri = data.getData();

                        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).signature(new ObjectKey(System.currentTimeMillis()));
                        Glide.with(UpdateProfileActivity.this)
                                .load(mUri)
                                .apply(options)
                                .placeholder(R.drawable.logo_placeholder)
                                .into(binding.avatarProfile);
                    }
                }
            }
    );

    public void showConfirm() {
        // get data
        firstname = binding.edtUpdateFirstname.getText().toString().trim();
        lastname = binding.edtUpdateLastname.getText().toString().trim();
        gioitinh = binding.spinnerGioitinhUpdate.getSelectedItem().toString();
        email = binding.edtEmailUpdate.getText().toString().trim();
        didong = binding.edtDidongUpdate.getText().toString().trim();
//        ngaysinh = binding.edtNgaysinhUpdate.getText().toString().trim();
        cmnd = binding.edtCmndUpdate.getText().toString().trim();
        // ngaysinh
        ngaysinh = Long.parseLong(binding.edtNgaysinhUpdate.getText().toString().trim().replace("/", "").replace(" ",""));

        Log.d(TAG, "ngaysinh: " + ngaysinh);

        if (gioitinh.equals("Nam")) {
            gioitinh = "MALE";
        } else {
            gioitinh = "FEMALE";
        }

        // alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn muốn thực hiện hành động này?")
                .setPositiveButton("OK", (dialog, which) -> {
                    viewModel.updateProfile(token, firstname, lastname, gioitinh, email, didong, ngaysinh, cmnd, mUri);
                    dialog.dismiss();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.create();
        builder.show();
    }
}