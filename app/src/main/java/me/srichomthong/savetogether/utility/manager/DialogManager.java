package me.srichomthong.savetogether.utility.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import me.srichomthong.savetogether.R;

/**
 * Created by sapthawee_s on 14-Nov-17.
 */

public class DialogManager {
    private Context ctx;
    private Activity activity;
    private String TAG = "Toast : ";
    ACProgressFlower dialog;

    public DialogManager(Context ctx) {
        this.ctx = ctx;
        this.activity = (Activity) ctx;
    }

    public void displayLoading(){
        dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.CYAN).build();
        dialog.show();
    }

    public void displayMessageLoading(String message){
        ACProgressFlower dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(message)
                .textSize(15)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }

    public Boolean isDisplay(){
        return dialog.isShowing();
    }

    public void dismissDisplay(){
        dialog.dismiss();
    }
}
