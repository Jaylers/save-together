package me.srichomthong.savetogether.center.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.ProfileActivity;
import me.srichomthong.savetogether.utility.manager.SweetDialogManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }

    private View view;
    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SweetDialogManager sweet;
    @BindView(R.id.fragment_menu_profile_img) CircleImageView user_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        sweet = new SweetDialogManager(activity);
        setUpImage();
        return  view;
    }

    private void setUpImage(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser.getPhotoUrl() != null){
            Glide.with(this)
                    .load(mUser.getPhotoUrl())
                    .into(user_photo);
        }
    }

    @OnClick(R.id.fragment_menu_profile_img) public void onProfile(){
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.fragment_menu_setting_img) public void onSetting(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_announce_img) public void onAnnounce(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_privacy_img) public void onPrivacy(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_notification_img) public void onNotification(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_report_img) public void onReport(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_care_img) public void onCustomerCare(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_facebook_img) public void onFacebook(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

    @OnClick(R.id.fragment_menu_g_plus_img) public void onGooglePlus(){
        sweet.sweetInfo(activity.getString(R.string.app_message_message),
                activity.getString(R.string.app_message_coming_soon));
    }

}
