package me.srichomthong.savetogether.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.fragment.MenuFragment;
import me.srichomthong.savetogether.center.fragment.SalesFragment;
import me.srichomthong.savetogether.restaurant.fragment.HistoryFragmentRes;

public class RestaurantMainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.res_navigation_review:
                    review();
                    fab_add.setVisibility(View.GONE);
                    return true;
                case R.id.res_navigation_my_sale:
                    sale();
                    fab_add.setVisibility(View.VISIBLE);
                    return true;
                case R.id.res_navigation_history:
                    history();
                    fab_add.setVisibility(View.GONE);
                    return true;
                case R.id.res_navigation_menu:
                    menu();
                    fab_add.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @BindView(R.id.fab_add_product)FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);
        ButterKnife.bind(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sale();
    }

    @OnClick(R.id.fab_add_product)public void onAddProduct(){
        Intent intent = new Intent(RestaurantMainActivity.this, AddSaleActivity.class);
        startActivity(intent);
    }
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private void review(){

    }

    private void history(){
        mPagerAdapter = null;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new HistoryFragmentRes()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.main_title_history),
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.reviewPager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.salePager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.historyPager_res_container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_control);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void sale(){
        mPagerAdapter = null;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new SalesFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.main_title_sale),
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.reviewPager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.historyPager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.salePager_res_container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_control);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void menu(){
        mPagerAdapter = null;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mMenuFragments = new Fragment[] {
                    new MenuFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.main_title_menu)
            };
            @Override
            public Fragment getItem(int position) {
                return mMenuFragments[position];
            }
            @Override
            public int getCount() {
                return mMenuFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.salePager_res_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_res_container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_control);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private int confirm = 0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            }, 2000);
        }
    }
}
