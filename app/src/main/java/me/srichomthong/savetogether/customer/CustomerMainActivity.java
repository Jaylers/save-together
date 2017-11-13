package me.srichomthong.savetogether.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import me.srichomthong.savetogether.R;

public class CustomerMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.customer_main_title_home);
                    return true;
                case R.id.navigation_review:
                    mTextMessage.setText(R.string.customer_main_title_review);
                    return true;
                case R.id.navigation_near_by:
                    mTextMessage.setText(R.string.customer_main_title_near_by);
                    return true;
                case R.id.navigation_save:
                    mTextMessage.setText(R.string.customer_main_title_save);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.customer_main_title_profile);
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
    }

}
