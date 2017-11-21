package me.srichomthong.savetogether.center.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.srichomthong.savetogether.R;


public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }

    private Activity activity;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
