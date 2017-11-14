package me.srichomthong.savetogether.center;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.SplashActivity;
import me.srichomthong.savetogether.center.model.User;
import me.srichomthong.savetogether.utility.manager.ColorManager;
import me.srichomthong.savetogether.utility.manager.DialogManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedpreference.SharedSignedUser;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;
import me.srichomthong.savetogether.utility.sharedstring.SharedFlag;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class EmailRegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private static final String TAG = "Email Register";
    // UI references.
    @BindView(R.id.email_reg_email) AutoCompleteTextView mEmailView;
    @BindView(R.id.email_reg_password) EditText mPasswordView;
    @BindView(R.id.email_reg_re_password) EditText mRePasswordView;
    @BindView(R.id.email_reg_form) View mLoginFormView;
    private ColorManager colorManager;
    private SharedSignedUser sharedSignedUser;
    private DialogManager dialogManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ToastManager toastManager;

    @BindView(R.id.email_reg_title) TextView text_title;
    @BindView(R.id.linear_email_reg) LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);
        ButterKnife.bind(this);
        // Set up the login form.
        populateAutoComplete();
        declareClass();
        whoImI(sharedSignedUser.getTypeOfUser());
    }

    private void declareClass(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        colorManager = new ColorManager();
        sharedSignedUser = new SharedSignedUser(EmailRegisterActivity.this);
        dialogManager = new DialogManager(EmailRegisterActivity.this);
        toastManager = new ToastManager(EmailRegisterActivity.this);
        mRePasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
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

    @OnClick(R.id.email_sign_in_button) public void onRegister(){
        attemptLogin();
    }
    @OnClick(R.id.email_reg_selection) public void onReSelection(){
        Intent intent = new Intent(EmailRegisterActivity.this, SplashActivity.class);
        startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.email_reg_back) public void onBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EmailRegisterActivity.this, EmailSignInActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void signUp() {
        Log.d(TAG, getString(R.string.app_message_sign_up));
        dialogManager.displayLoading();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        dialogManager.dismissDisplay();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            toastManager.displayError(getString(R.string.app_message_sign_up_success));
                        } else {
                            toastManager.displayError(task.getException().getMessage());
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Write new user
        writeNewUser(user.getUid(), user.getEmail(), user.getDisplayName(), user.getPhoneNumber()
                ,user.getProviderId());
        // Go to MainActivity
        startActivity(new Intent(EmailRegisterActivity.this, SignInActivity.class));
        finish();
    }

    private void writeNewUser(String id, String email, String name, String phone, String provider) {
        String type = sharedSignedUser.getTypeOfUser();
        if (type.equals(SharedFlag.flag_unknown)){
            type = SharedFlag.flag_customer;
        }
        User user = new User( id, "", email, name, phone, "", provider, "", type);
        mDatabase.child(FirebaseTag.users).child(id).setValue(user);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
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


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String rePassword = mRePasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (password.isEmpty()){
            mPasswordView.setError(getString(R.string.err_message_required));
            focusView = mPasswordView;
            cancel = true;
        }else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if (rePassword.isEmpty()){
            mRePasswordView.setError(getString(R.string.err_message_required));
            focusView = mRePasswordView;
            cancel = true;
        }else if (!password.equals(rePassword)){
            mRePasswordView.setError(getString(R.string.error_invalid_re_password));
            focusView = mRePasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (email.isEmpty()) {
            mEmailView.setError(getString(R.string.err_message_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            signUp();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }


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
                new ArrayAdapter<>(EmailRegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

