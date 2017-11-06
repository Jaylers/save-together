package me.srichomthong.savetogether.together;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOError;

import me.srichomthong.savetogether.together.sharedstring.SharedTag;

/**
 * Created by jaylerr on 09-Jun-17.
 */

public class Connection {
    Context ctx;
    Activity activity;
    ConnectivityManager connectivityManager;
    String TAG = SharedTag.tag_network;

    public Connection(Context ctx) {
        this.ctx = ctx;
        this.activity = (Activity) ctx;
    }

    public Boolean isConnection(){
        try{
            connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    Log.i(TAG, "Connection property on WIFI");
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    Log.i(TAG, "Connection property on MOBILE");
                    return true;
                }
            } else {
                Log.e(TAG, "Connection error");
                return false;
            }

        }catch (IOError error){
            Log.e(TAG, "Connection error");
            return false;
        }
        return false;
    }
}
