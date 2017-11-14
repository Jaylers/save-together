package me.srichomthong.savetogether.center;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shashank.sony.fancytoastlib.FancyToast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectionAuthFragment extends Fragment {


    public SelectionAuthFragment() {
        // Required empty public constructor
    }

    View view;
    private Activity activity;
    private SharedSignedUser sharedSignedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_auth_selection, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        sharedSignedUser = new SharedSignedUser(activity);
        sharedSignedUser.setTypeOfUser(SharedFlag.flag_customer);

        return  view;
    }

    @OnClick(R.id.img_restaurant) public void imgRestaurant(){
        sharedSignedUser.setTypeOfUser(SharedFlag.flag_restaurant);
        restaurantSignIn();
    }

    @OnClick(R.id.img_customer) public void imgConsumer(){
        sharedSignedUser.setTypeOfUser(SharedFlag.flag_customer);
        customerSignIn();
    }

    private void customerSignIn(){
        Intent intent = new Intent(activity, SignInActivity.class);
        startActivity(intent);
        activity.finish();
    }

    private void restaurantSignIn(){
        Intent intent = new Intent(activity, SignInActivity.class);
        startActivity(intent);
        activity.finish();
    }
}
