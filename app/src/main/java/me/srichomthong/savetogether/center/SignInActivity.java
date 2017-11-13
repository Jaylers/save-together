package me.srichomthong.savetogether.center;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

public class SignInActivity extends AppCompatActivity {

    ColorManager colorManager;
    SharedFlag sharedFlag;
    @BindView(R.id.sign_in_txt_title) TextView text_title;
    @BindView(R.id.sign_in_container) ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_base_sign_in);
        ButterKnife.bind(this);
        colorManager = new ColorManager();
        sharedFlag = new SharedFlag();
        whoImI();
    }

    private void whoImI(){
        SharedSignedUser sharedSignedUser = new SharedSignedUser(SignInActivity.this);
        if (sharedSignedUser.getTypeOfUser() == SharedFlag.flag_customer){
            text_title.setText(getString(R.string.auth_message_im_consumer));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(sharedFlag.flag_customer_color_theme)));
        }else if (sharedSignedUser.getTypeOfUser() == SharedFlag.flag_restaurant){
            text_title.setText(getString(R.string.auth_message_im_the_restaurant));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(sharedFlag.flag_restaurant_color_theme)));
        }else {
            Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
            startActivity(intent);
            this.finish();
        }
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
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
        startActivity(intent);
        this.finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
}
