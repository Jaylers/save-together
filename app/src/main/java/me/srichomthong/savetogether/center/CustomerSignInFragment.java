package me.srichomthong.savetogether.center;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerSignInFragment extends Fragment {


    public CustomerSignInFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer_sign_in, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.img_email_sign_in) public void onEmailSign(){
        EmailSignFragment emailSignFragment = new EmailSignFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, emailSignFragment);
        ft.commit();
    }

    @OnClick(R.id.img_facebook_sign_in) public void onFacebook(){

    }

    @OnClick(R.id.img_google_sign_in) public void onGoogle(){

    }

    @OnClick(R.id.txt_cus_back) public void onBack(){
        BaseAuthFragment baseAuthFragment = new BaseAuthFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.slide_down_out);
        ft.replace(R.id.frame_fragment_base_auth, baseAuthFragment);
        ft.commit();
    }

}
