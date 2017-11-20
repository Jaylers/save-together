package me.srichomthong.savetogether.center;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.model.User;
import me.srichomthong.savetogether.utility.manager.AccountManager;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.manager.DialogManager;
import me.srichomthong.savetogether.utility.manager.SweetDialogManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

/**
 * Created by sapthawee_s on 17-Nov-17.
 */

public class PhoneSignInActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PHONE_NUMBER_HINT = 1234;

    @BindView(R.id.phone_sign_in_number) AutoCompleteTextView mPhone;
    @BindView(R.id.phone_sign_in_verify_code) EditText mVerify;
    @BindView(R.id.phone_verify_btn) Button mVerifyButton;
    @BindView(R.id.phone_send_btn) Button mSendButton;
    @BindView(R.id.phone_re_send_btn) Button mReSendButton;
    @BindView(R.id.phone_sign_title) TextView text_title;
    @BindView(R.id.linear_phone_sign_in) LinearLayout background;
    private ColorManager colorManager;
    private SharedSignedUser sharedSignedUser;
    private DialogManager dialogManager;
    private ToastManager toastManager;
    private SweetDialogManager sweetDialogManager;
    private Handler handler;
    private boolean mVerificationInProgress = false;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final String TAG = "PhoneAuthActivity";
    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign_in);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        colorManager = new ColorManager();
        sharedSignedUser = new SharedSignedUser(PhoneSignInActivity.this);
        dialogManager = new DialogManager(PhoneSignInActivity.this);
        toastManager = new ToastManager(PhoneSignInActivity.this);
        sweetDialogManager = new SweetDialogManager(PhoneSignInActivity.this);
        whoImI(sharedSignedUser.getTypeOfUser());
        handler = new Handler();

        setupView();

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
                dialogManager.dismissDisplay();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mPhone.setError(getString(R.string.err_invalid_phone_number));
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    sweetDialogManager.sweetError(getString(R.string.app_message_message),
                            getString(R.string.err_phone_sign_up_quota_exceeded));
                    // [END_EXCLUDE]
                }
                dialogManager.dismissDisplay();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                toastManager.displaySuccess("We already send a verify code to your phone message, the contain with 6 digit");
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                mVerify.requestFocus();
                dialogManager.dismissDisplay();
            }
        };
        // [END phone_auth_callbacks]
    }

    private void setupView(){
        mSendButton.setEnabled(true);
        mReSendButton.setEnabled(false);
        mVerifyButton.setEnabled(false);
    }

    @OnClick(R.id.phone_send_btn) public void onSend(){
        dialogManager.displayLoading();
        mReSendButton.setEnabled(false);
        mSendButton.setEnabled(false);
        mVerifyButton.setEnabled(true);
        if (validatePhoneNumber()){
            startPhoneNumberVerification(mPhone.getText().toString());
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mReSendButton.setEnabled(true);
            }
        },30000);
    }

    @OnClick(R.id.phone_re_send_btn) public void onReSend(){
        dialogManager.displayLoading();
        mVerifyButton.setEnabled(true);
        mReSendButton.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mReSendButton.setEnabled(true);
            }
        },30000);
        resendVerificationCode(mPhone.getText().toString(), mResendToken);
    }

    @OnClick(R.id.phone_verify_btn) public void onSignInBtn(){
        dialogManager.displayLoading();
        if (mVerify.getText().length() == 6){
            verifyPhoneNumberWithCode(mVerificationId, mVerify.getText().toString());
        }else {
            dialogManager.dismissDisplay();
            mVerify.setError(getString(R.string.app_message_must_be_6));
            mVerify.requestFocus();
        }
    }

    private void whoImI(String userType){
        if (userType.equals(SharedFlag.flag_restaurant)){
            text_title.setText(getString(R.string.app_message_im_the_restaurant_double_line));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(SharedFlag.flag_restaurant_color_theme)));
        }else if (userType.equals(SharedFlag.flag_customer)){
            text_title.setText(getString(R.string.app_message_im_consumer_double_line));
            background.setBackground(colorManager.getColorDrawable(colorManager.parser(SharedFlag.flag_customer_color_theme)));
        }
    }

    @OnClick(R.id.txt_phone_sign_in_back) public void onBack(){
        onBackPressed();
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
        mVerificationInProgress = true;
        toastManager.displayInfo("Sending verify code . . .");
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        toastManager.displayInfo("Resending verify code . . .");
    }
// [END resend_verification]

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PhoneSignInActivity.this, SignInActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void verifyUserType(PhoneAuthCredential credential, FirebaseUser mUser){
        dialogManager.displayLoading();
        if (mUser != null){
            AccountManager accountManager = new AccountManager(PhoneSignInActivity.this);
            accountManager.writePhoneUser(credential, mUser, mPhone.getText().toString());
            dialogManager.dismissDisplay();
            accountManager.restartApp();
        }
    }
    // [START sign_in_with_phone]
    FirebaseUser mUser;
    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            mVerify.setVisibility(View.INVISIBLE);
                            mAuth = FirebaseAuth.getInstance();
                            mUser = mAuth.getCurrentUser();
                            verifyUserType(credential, mUser);
                            // [END_EXCLUDE]
                            dialogManager.dismissDisplay();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mVerify.setError(getString(R.string.err_invalid_phone_code));
                                // [END_EXCLUDE]
                            }
                            dialogManager.dismissDisplay();
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhone.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhone.getText().toString());
        }
        // [END_EXCLUDE]
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }
}
