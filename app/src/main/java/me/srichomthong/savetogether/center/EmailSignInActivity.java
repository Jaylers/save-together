package me.srichomthong.savetogether.center;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.customer.CustomerMainActivity;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.manager.DialogManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class EmailSignInActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private ColorManager colorManager;
    private SharedSignedUser sharedSignedUser;
    private DialogManager dialogManager;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.email_sign_title) TextView text_title;
    @BindView(R.id.linear_email_sign_in) LinearLayout background;
    @BindView(R.id.email_sign_in_email) AutoCompleteTextView mEmailView;
    @BindView(R.id.email_sign_in_password) EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_email_sign_in);
        ButterKnife.bind(this);
        populateAutoComplete();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        colorManager = new ColorManager();
        sharedSignedUser = new SharedSignedUser(EmailSignInActivity.this);
        dialogManager = new DialogManager(EmailSignInActivity.this);
        whoImI(sharedSignedUser.getTypeOfUser());
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

    @OnClick(R.id.email_sign_in_button) public void onSignIn(){
        attemptLogin();
    }

    @OnClick(R.id.email_reg_text_btn) public void onRegister(){
        Intent intent = new Intent(EmailSignInActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.txt_email_sign_in_back) public void onBack(){
        onBackPressed();
    }

    private void signIn(String email, String password){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialogManager.dismissDisplay();
                if (task.isSuccessful()){
                    whoAmI();
                }else {
                    ToastManager toastManager = new ToastManager(EmailSignInActivity.this);
                    toastManager.displayError(task.getException().getMessage());
                }
            }
        });
    }

    private void whoAmI(){
        // by pass
        iAmCustomer();
    }

    private void iAmCustomer(){
        Intent intent = new Intent(EmailSignInActivity.this, CustomerMainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void iAmRestaurant(){
        Intent intent = new Intent(EmailSignInActivity.this, CustomerMainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EmailSignInActivity.this, SignInActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(EmailSignInActivity.this, R.anim.fade_in_smooth, R.anim.fade_out);
        startActivity(intent, options.toBundle());
        this.finish();
    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.err_message_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            dialogManager.displayLoading();
            //do sign in
            signIn(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(EmailSignInActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }
}

