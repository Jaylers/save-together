package me.srichomthong.savetogether.utility.sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;
import me.srichomthong.savetogether.utility.sharedstring.SharedKey;


/**
 * Created by jaylerr on 03-Jun-17.
 */

public class SharedSignedUser {
    Activity activity;
    private SharedPreferences account;
    private SharedPreferences.Editor editor;

    public SharedSignedUser(Activity activity) {
        this.activity = activity;
        account = activity.getSharedPreferences(SharedKey.key_sign_in, Context.MODE_PRIVATE);
        editor = account.edit();
    }

    public void setTypeOfUser(String type){
        editor.putString(SharedKey.key_user_type,type);
        editor.commit();
    }

    public String getTypeOfUser(){
        return account.getString(SharedKey.key_user_type,SharedFlag.flag_unknown);
    }
}
