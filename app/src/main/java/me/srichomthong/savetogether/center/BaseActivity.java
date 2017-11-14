package me.srichomthong.savetogether.center;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.utility.manager.DialogManager;

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public DialogManager dialogManager;

    public void showProgressDialog() {
        dialogManager = new DialogManager(BaseActivity.this);
        dialogManager.displayLoading();
    }

    public void hideProgressDialog() {
        if (dialogManager != null && dialogManager.isDisplay()) {
            dialogManager.dismissDisplay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        dialogManager.dismissDisplay();
    }

}
