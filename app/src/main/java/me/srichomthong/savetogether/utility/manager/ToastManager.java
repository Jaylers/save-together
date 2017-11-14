package me.srichomthong.savetogether.utility.manager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Gravity;

import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Created by sapthawee_s on 14-Nov-17.
 */

public class ToastManager {
    private Context ctx;
    private Activity activity;
    private String TAG = "Toast : ";

    public ToastManager(Context ctx) {
        this.ctx = ctx;
        this.activity = (Activity) ctx;
    }

    public void displaySuccess(String message){
        FancyToast.makeText(activity,message,FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
    }

    public void displayInfo(String message){
        FancyToast.makeText(activity,message,FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();
    }

    public void displayInfoOnTop(String message){
        FancyToast fancyToast = new FancyToast(activity);
        fancyToast.setText(message);
        fancyToast.setDuration(FancyToast.LENGTH_LONG);
        fancyToast.setText(FancyToast.INFO);
        fancyToast.setGravity(Gravity.TOP,0,0);
        fancyToast.show();
    }

    public void displayWarning(String message){
        FancyToast.makeText(activity,message,FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show();
    }

    public void displayError(String message){
        FancyToast.makeText(activity,message,FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
    }
}
