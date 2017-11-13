package me.srichomthong.savetogether.center;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.customer.CustomerMainActivity;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_base_sign_in);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.img_google_sign_in) public void onGoogleSignIn(){

    }

    @OnClick(R.id.img_facebook_sign_in) public void onFacebookSignIn(){

    }

    @OnClick(R.id.img_email_sign_in) public void OnEmailSignIn(){
        Intent intent = new Intent(SignInActivity.this ,EmailSignInActivity.class);
        startActivity(intent);
        this.finish();
    }

    //On Back//////////////////////////////////////////////////////////////////////////////////

    @OnClick(R.id.txt_sign_in_back)public void OnBack(){
        onBack();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack(){
        Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
        startActivity(intent);
        this.finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
}
