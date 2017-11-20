package me.srichomthong.savetogether.utility.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;


import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import me.srichomthong.savetogether.R;

/**
 * Created by sapthawee_s on 19-Nov-17.
 */

public class SweetDialogManager {
    private Context ctx;
    private Activity activity;
    private String TAG = "Sweet Dialog : ";
    SweetAlertDialog pDialog;

    public SweetDialogManager(Context ctx) {
        this.ctx = ctx;
        this.activity = (Activity) ctx;
    }

    public void sweetInfo(String title, String message){
        new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void sweetSuccess(String title, String message){
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void sweetWarning(String title, String message){
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void sweetError(String title, String message){
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void sweetLoading(){
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(activity.getString(R.string.app_message_one_moment));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void dismissSweet(){
        if (pDialog.isShowing()){
            pDialog.dismissWithAnimation();
        }
    }
}
