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

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerSignInFragment extends Fragment {


    public CustomerSignInFragment() {
        // Required empty public constructor
    }

    View view;
    @BindView(R.id.edt_des_username)
    EditText edt_des_user;
    @BindView(R.id.edt_elderly_username)
    EditText edt_eld_user;
    @BindView(R.id.edt_elderly_gmail_account)
    EditText edt_eld_gmail;
    @BindView(R.id.edt_machine_serial_number)
    EditText edt_eld_serial;
    SharedSignedUser sharedSignedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_descendant_sign_in, container, false);
        ButterKnife.bind(this, view);
        sharedSignedUser = new SharedSignedUser(getActivity());
        return view;
    }

    @OnClick(R.id.btn_frag_des_create) public void onDesCreate(){
        if (isSignInForm()){
            sharedSignedUser.setStateSignIn(SharedFlag.flag_descendant);
            Intent intent = new Intent(getActivity(), DescendantMainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @OnClick(R.id.txt_des_back) public void onBack(){
        BaseAuthFragment baseAuthFragment = new BaseAuthFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, baseAuthFragment);
        ft.commit();
    }

    private Boolean isSignInForm(){
        if (edt_des_user.getText().toString().isEmpty()){
            edt_des_user.setError(getString(R.string.err_message_required));
            return false;
        }else if (edt_eld_user.getText().toString().isEmpty()){
            edt_eld_user.setError(getString(R.string.err_message_required));
            return false;
        }else if (edt_eld_gmail.getText().toString().isEmpty()){
            edt_eld_gmail.setError(getString(R.string.err_message_required));
            return false;
        }else if (edt_eld_serial.getText().toString().isEmpty()){
            edt_eld_serial.setError(getString(R.string.err_message_required));
            return false;
        }else return true;
    }

}
