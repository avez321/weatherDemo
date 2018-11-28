package com.example.avi_pc.weatherdemo.util;

import android.app.Dialog;
import android.content.Context;

import android.widget.Toast;

import com.example.avi_pc.weatherdemo.R;


public class DialogUtil {
    public static void showPleaseWaitDialog(Dialog dialog) {
        dialog.setContentView(R.layout.layout_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void hideDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    public static void showToast(String message, int duration, Context context) {
        Toast.makeText(context, message, duration).show();
    }
}
