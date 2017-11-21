package me.srichomthong.savetogether.customer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.fragment.MenuFragment;

public class CustomerMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    @BindView(R.id.cus_mail_fragment_control) FrameLayout fragment_control;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cus_navigation_home:
                    mTextMessage.setText(R.string.main_title_home);
                    home();
                    return true;
                case R.id.cus_navigation_review:
                    mTextMessage.setText(R.string.main_title_review);
                    review();
                    return true;
                case R.id.cus_navigation_near_by:
                    mTextMessage.setText(R.string.main_title_near_by);
                    nearBy();
                    return true;
                case R.id.cus_navigation_favorite:
                    mTextMessage.setText(R.string.main_title_favorite);
                    favorite();
                    return true;
                case R.id.cus_navigation_profile:
                    mTextMessage.setText(R.string.main_title_profile);
                    profile();
                    return true;
            }
            return false;
        }
    };

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        declareClass();
        profile();
    }

    private void declareClass(){
        mAuth = FirebaseAuth.getInstance();
        activity = this;
        currentUser = mAuth.getCurrentUser();
    }

    private void home(){

    }

    private void review(){

    }

    private void nearBy(){
        MapFragment mapFragment = new MapFragment();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.cus_mail_fragment_control, mapFragment);
        ft.commit();
    }

    private void favorite(){

    }

    private void profile(){
        MenuFragment menuFragment = new MenuFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.cus_mail_fragment_control, menuFragment);
        ft.commit();
    }

}
