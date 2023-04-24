package com.example.mobanko.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

import com.example.mobanko.R;

public class WaitingDialog {

    private Activity activity;
    private Dialog dialog;

    public WaitingDialog(Activity mActivity) {
        this.activity = mActivity;
    }

    public void startWaitingDialog() {
        if (dialog == null) {
            dialog = new Dialog(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            dialog.setContentView(inflater.inflate(R.layout.waiting_dialog, null));
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void closeWaitingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
