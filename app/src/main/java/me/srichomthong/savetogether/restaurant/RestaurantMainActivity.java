package me.srichomthong.savetogether.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import me.srichomthong.savetogether.R;

public class RestaurantMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.res_navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.res_navigation_review:
                    mTextMessage.setText(R.string.main_title_review);
                    return true;
                case R.id.res_navigation_my_sale:
                    mTextMessage.setText(R.string.main_title_sale);
                    return true;
                case R.id.res_navigation_history:
                    mTextMessage.setText(R.string.main_title_history);
                    return true;
                case R.id.res_navigation_profile:
                    mTextMessage.setText(R.string.main_title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
