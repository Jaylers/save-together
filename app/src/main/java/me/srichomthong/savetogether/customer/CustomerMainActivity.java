package me.srichomthong.savetogether.customer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.ProfileActivity;
import me.srichomthong.savetogether.center.fragment.MenuFragment;
import me.srichomthong.savetogether.center.fragment.SalesFragment;

public class CustomerMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cus_navigation_home:
                    store();
                    return true;
                case R.id.cus_navigation_review:
                    review();
                    return true;
                case R.id.cus_navigation_near_by:
                    nearBy();
                    return true;
                case R.id.cus_navigation_favorite:
                    favorite();
                    return true;
                case R.id.cus_navigation_menu:
                    menu();
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

    @BindView(R.id.cus_main_fragment_control)
    FrameLayout fragment_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        ButterKnife.bind(this);

        BottomNavigationView navigation = findViewById(R.id.cus_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        declareClass();
        store();
    }

    private void declareClass() {
        mAuth = FirebaseAuth.getInstance();
        activity = this;
        currentUser = mAuth.getCurrentUser();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cus_profile_map);
        mapFragment.getMapAsync(CustomerMainActivity.this);
    }

    private void store() {
        fragment_control.setVisibility(View.INVISIBLE);
        mPagerAdapter = null;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new SalesFragment()
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.main_title_store),
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
        mViewPager = findViewById(R.id.reviewPager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.favoritePager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.salePager_cus_container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_cus_control);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void review() {
        fragment_control.setVisibility(View.INVISIBLE);
    }

    private void nearBy() {
        mViewPager = findViewById(R.id.reviewPager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.favoritePager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.salePager_cus_container);
        mViewPager.setVisibility(View.GONE);
        fragment_control.setVisibility(View.VISIBLE);

    }

    private void favorite() {
        fragment_control.setVisibility(View.INVISIBLE);
    }

    private void menu() {
        fragment_control.setVisibility(View.INVISIBLE);
        mPagerAdapter = null;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mMenuFragments = new Fragment[]{
                    new MenuFragment()
            };
            private final String[] mFragmentNames = new String[]{
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
        mViewPager = findViewById(R.id.salePager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.favoritePager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.reviewPager_cus_container);
        mViewPager.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.menuPager_cus_container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs_cus_control);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng locationMe = new LatLng(18.8013116, 98.9674135);
        mMap.addMarker(new MarkerOptions()
                .position(locationMe)
                .title("Marker in Nimmanhaemin"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(18.8013116, 98.9674135)).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }
}
