package me.srichomthong.savetogether.center;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailSignFragment extends Fragment {


    public EmailSignFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email_sign, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.txt_email_sign_back) public void onBack(){
        CustomerSignInFragment customerSignInFragment = new CustomerSignInFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, customerSignInFragment);
        ft.commit();
    }

}
