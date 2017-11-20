package me.srichomthong.savetogether;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.center.AppSignatureHelper;
import me.srichomthong.savetogether.center.EmailSignInActivity;
import me.srichomthong.savetogether.center.SelectionAuthFragment;
import me.srichomthong.savetogether.center.model.User;
import me.srichomthong.savetogether.customer.CustomerMainActivity;
import me.srichomthong.savetogether.restaurant.RestaurantMainActivity;
import me.srichomthong.savetogether.utility.manager.AccountManager;
import me.srichomthong.savetogether.utility.manager.ConnectionsManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

public class SplashActivity extends AppCompatActivity {
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private View mControlsView_sign_out;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @BindView(R.id.txt_auth_message) TextView auth_message;
    private ConnectionsManager connection;
    private FirebaseAuth mAuth;
    @BindView(R.id.splash_progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mControlsView_sign_out = findViewById(R.id.fullscreen_content_controls_2);
        mContentView = findViewById(R.id.fullscreen_content);

        connection = new ConnectionsManager(SplashActivity.this);
        connectionVerify();
    }

    private void connectionVerify(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (connection.isConnection()){
                    auth_message.setText(getString(R.string.app_message_verifying));
                    userVerify();
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    auth_message.setText(getString(R.string.err_message_connection_failure));
                    Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_up_in_from_buttom);
                    animation.setDuration(200);
                    mControlsView.startAnimation(animation);
                    mControlsView.setVisibility(View.VISIBLE);
                }
            }
        },1000);
    }

    private FirebaseUser currentUser;
    private void userVerify(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            existingUser();
        }else {
            openBaseAuth();
        }
    }

    private void existingUser(){
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.slide_up_in_from_buttom);
        animation.setDuration(200);
        mControlsView_sign_out.startAnimation(animation);
        mControlsView_sign_out.setVisibility(View.VISIBLE);
        auth_message.setText(getString(R.string.app_message_signing_in));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                validateUserType();
            }
        }, 1000);
    }

    private void validateUserType(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/".concat(currentUser.getUid()));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null){
                    AccountManager accountManager = new AccountManager(SplashActivity.this);
                    accountManager.signOut();
                }else if (user.getType().equals(SharedFlag.flag_customer)){
                    iAmCustomer();
                }else {
                    iAmRestaurant();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = String.valueOf(databaseError.getCode()).concat(" ".concat(databaseError.getMessage()));
                Log.e("Error", message);
                mAuth.signOut();
                openBaseAuth();
            }
        });
    }

    private void iAmCustomer(){
        Intent intent = new Intent(SplashActivity.this, CustomerMainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void iAmRestaurant(){
        Intent intent = new Intent(SplashActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.splash_btn_retry) public void onRetry(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down_out);
        animation.setDuration(200);
        progressBar.setVisibility(View.VISIBLE);
        mControlsView.startAnimation(animation);
        mControlsView.setVisibility(View.GONE);
        auth_message.setText(getString(R.string.app_message_connecting));
        connectionVerify();
    }

    @OnClick(R.id.splash_btn_log_out) public void onSignOut(){
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.slide_down_out);
        animation.setDuration(200);
        mControlsView_sign_out.startAnimation(animation);
        mControlsView_sign_out.setVisibility(View.INVISIBLE);

        mAuth.signOut();
        Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and cus_navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);

    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void openBaseAuth(){
        mContentView.setVisibility(View.GONE);
        SelectionAuthFragment selectionAuthFragment = new SelectionAuthFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in_smooth,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, selectionAuthFragment);
        ft.commit();
    }

    private int confirm = 0;
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment_base_auth);

        SelectionAuthFragment selectionAuthFragment = new SelectionAuthFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (currentFragment instanceof SelectionAuthFragment){
            if (confirm>=1){
                confirm = 0;
                finish();
            }else {
                confirm++;
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.app_message_confirm_to_close_app),
                        Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        confirm = 0;
                    }
                },2000);
            }
        }else {
            ft.setCustomAnimations(R.anim.fade_in,
                    R.anim.fade_out);
            ft.replace(R.id.frame_fragment_base_auth, selectionAuthFragment);
            ft.commit();
        }
    }
}
