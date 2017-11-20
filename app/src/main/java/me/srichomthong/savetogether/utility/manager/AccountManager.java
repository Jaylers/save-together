package me.srichomthong.savetogether.utility.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cc.cloudist.acplibrary.ACProgressFlower;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.center.model.User;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class AccountManager {
    private Context ctx;
    private Activity activity;
    private String TAG = "Dialog : ";
    private DatabaseReference mDatabase;
    private SharedSignedUser sharedSignedUser;
    private FirebaseAuth mAuth;
    public AccountManager(Context ctx){
        this.ctx = ctx;
        this.activity = (Activity) ctx;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedSignedUser = new SharedSignedUser(activity);
    }

    public void writeUser(FirebaseUser user){
        String url;
        if (user.getPhotoUrl() != null){
            url = user.getPhotoUrl().toString();
        }else url = "";

        User users = new User( user.getUid(), "", user.getEmail(), user.getDisplayName(),
                user.getPhoneNumber(), "", user.getProviders().toString(), url
                , sharedSignedUser.getTypeOfUser());
        mDatabase.child(FirebaseTag.users).child(user.getUid()).setValue(users);
    }

    public void writePhoneUser(PhoneAuthCredential credential, FirebaseUser mUser, String phone){
        User users = new User(mUser.getUid(),"", "", "", phone, "",
                credential.getProvider(), "", sharedSignedUser.getTypeOfUser());
        mDatabase.child(FirebaseTag.users).child(mUser.getUid()).setValue(users);
    }

    public void signOut(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        restartApp();
    }

    public void revokeAccess(GoogleApiClient mGoogleApiClient){
        final ToastManager toastManager = new ToastManager(activity);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()){
                            toastManager.displaySuccess(status.getStatusMessage());
                        }else {
                            toastManager.displayError(activity.getString(R.string.err_unknown_error));
                        }
                    }
                }
        );
    }

    public void restartApp(){
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
