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
public class BaseAuthFragment extends Fragment {


    public BaseAuthFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_together_base_auth, container, false);
        ButterKnife.bind(this, view);

        return  view;
    }

    @OnClick(R.id.relative_descendant)
    public void relateDescendant(){
        descendantSignIn();
    }

    @OnClick(R.id.img_descendant)
    public void imgDescendant(){
        descendantSignIn();
    }

    @OnClick(R.id.relative_elderly)
    public void relateElderly(){
        elderlySignIn();
    }

    @OnClick(R.id.img_elderly)
    public void imgElderly(){
        elderlySignIn();
    }

    @OnClick(R.id.txt_language_setting)
    public void language(){
        languageSetting();
    }

    private void descendantSignIn(){
        CustomerSignInFragment customerSignInFragment = new CustomerSignInFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, customerSignInFragment);
        ft.commit();
    }

    private void elderlySignIn(){
        RestaurantSignInFragment restaurantSignInFragment = new RestaurantSignInFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out);
        ft.replace(R.id.frame_fragment_base_auth, restaurantSignInFragment);
        ft.commit();
    }

    private void languageSetting(){
//        LanguageListFragment lang = new LanguageListFragment();
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations(R.anim.slide_up_in_from_buttom,
//                R.anim.slide_up_out);
//        ft.replace(R.id.frame_fragment_base_auth, lang);
//        ft.commit();
    }
}
