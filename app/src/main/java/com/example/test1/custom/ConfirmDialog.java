package com.example.test1.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.test1.App;

public class ConfirmDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo")
                .setMessage("Bạn muốn thực hiện hành động này?")
                .setPositiveButton("OK",(dialog, which) -> {

                })
                .setPositiveButton("Hủy",(dialog, which) -> {

                });
        return builder.create();
    }
}
