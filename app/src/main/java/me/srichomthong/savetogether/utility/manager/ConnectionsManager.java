package me.srichomthong.savetogether.utility.manager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOError;

import me.srichomthong.savetogether.R;

/**
 * Created by jaylerr on 19-Jun-17.
 */

public class ConnectionsManager {
    Context ctx;
    Activity activity;
    ConnectivityManager connectivityManager;
    ToastManager toast;
    String TAG = "Network : ";

    public ConnectionsManager(Context ctx) {
        this.ctx = ctx;
        this.activity = (Activity) ctx;
        this.toast = new ToastManager(activity);
    }

    public Boolean isConnection(){
        try{
            connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
//                    toast.displayInfo(activity.getString(R.string.app_message_connected_via_wifi));
                    Log.i(TAG, activity.getString(R.string.app_message_connected_via_wifi));
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
//                    toast.displayInfo(activity.getString(R.string.app_message_connected_via_mobile_network));
                    Log.i(TAG, activity.getString(R.string.app_message_connected_via_mobile_network));
                    return true;
                }
            } else {
                Log.e(TAG, activity.getString(R.string.err_connection_error));
                return false;
            }

        }catch (IOError error){
            Log.e(TAG, activity.getString(R.string.err_connection_error));
            Toast.makeText(activity, activity.getString(R.string.err_connection_error),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}
