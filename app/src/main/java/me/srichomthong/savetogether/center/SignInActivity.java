package me.srichomthong.savetogether.center;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

public class SignInActivity extends AppCompatActivity {

    private ColorManager colorManager;
    private SharedSignedUser sharedSignedUser;
    @BindView(R.id.sign_in_txt_title) TextView text_title;
    @BindView(R.id.sign_in_container) ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_base_sign_in);
        ButterKnife.bind(this);

        colorManager = new ColorManager();
        sharedSignedUser = new SharedSignedUser(SignInActivity.this);
        whoImI(sharedSignedUser.getTypeOfUser());
    }

    private void whoImI(String userType){
        if (userType.equals(SharedFlag.flag_restaurant)){
            text_title.setText(getString(R.string.app_message_im_the_restaurant_double_line));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(SharedFlag.flag_restaurant_color_theme)));
        }else if (userType.equals(SharedFlag.flag_customer)){
            text_title.setText(getString(R.string.app_message_im_consumer_double_line));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(SharedFlag.flag_customer_color_theme)));
        }else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.err_unknown_error), Toast.LENGTH_SHORT).show();
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
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(SignInActivity.this, R.anim.fade_in, R.anim.fade_out);
        startActivity(intent, options.toBundle());
        this.finish();
    }

    //On Back//////////////////////////////////////////////////////////////////////////////////

    @OnClick(R.id.txt_sign_in_back)public void OnBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(SignInActivity.this, R.anim.fade_in_smooth, R.anim.fade_out);
        startActivity(intent, options.toBundle());
        this.finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
}
