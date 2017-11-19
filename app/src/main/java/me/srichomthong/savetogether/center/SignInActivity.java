package me.srichomthong.savetogether.center;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.utility.manager.AccountManager;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.manager.DialogManager;
import me.srichomthong.savetogether.utility.manager.SweetDialogManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

public class SignInActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "Sign In";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;


    private ColorManager colorManager;
    private SharedSignedUser sharedSignedUser;
    private DialogManager dialogManager;
    @BindView(R.id.sign_in_txt_title) TextView text_title;
    @BindView(R.id.sign_in_container) ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        colorManager = new ColorManager();
        sharedSignedUser = new SharedSignedUser(SignInActivity.this);
        dialogManager = new DialogManager(this);
        whoImI(sharedSignedUser.getTypeOfUser());
        configureSignIn();
    }

    public void configureSignIn(){
        //config sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //initialize auth
        mAuth = FirebaseAuth.getInstance();
        //auth stats listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                }else {

                }
            }
        };
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogManager.displayLoading();
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private FirebaseUser currentUser;
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account){
        Log.d(TAG, "firebase with Google" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "sign in with credential" + task.isSuccessful());
                if (task.isSuccessful()){
                    AccountManager accountManager = new AccountManager(SignInActivity.this);
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getCurrentUser();
                    accountManager.writeUser(currentUser);
                    reStartApp();
                }else {
                    SweetDialogManager sweetDialogManager = new SweetDialogManager(SignInActivity.this);
                    sweetDialogManager.sweetError(getString(R.string.err_), task.getException().getMessage());
                }
                dialogManager.dismissDisplay();
            }
        });
    }

    @OnClick(R.id.img_google_sign_in) public void onGoogleSignIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @OnClick(R.id.img_facebook_sign_in) public void onFacebookSignIn(){
        Intent intent = new Intent(SignInActivity.this, PhoneSignInActivity.class);
        startActivity(intent);
        this.finish();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastManager toastManager = new ToastManager(this);
        toastManager.displayError(getString(R.string.err_google_play_error));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void reStartApp(){
        Log.d("Application", "restart");
        Intent intent = new Intent(SignInActivity.this, SplashActivity.class);
        startActivity(intent);
        this.finish();
    }
}
