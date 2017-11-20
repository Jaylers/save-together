package me.srichomthong.savetogether.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.utility.manager.SweetDialogManager;

public class CustomerMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SweetDialogManager sweetDialogManager = new SweetDialogManager(CustomerMainActivity.this);
        sweetDialogManager.sweetInfo("Welcome","I hope you enjoy with us.");
    }

    private void home(){

    }

    private void review(){

    }

    private void nearBy(){

    }

    private void favorite(){

    }

    private void profile(){

    }

}
