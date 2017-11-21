package me.srichomthong.savetogether.customer.viewmanager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

/**
 * Created by sapthawee_s on 22-Nov-17.
 */

public class CustomerUserInfo {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Activity activity;
    private DatabaseReference databaseReference;

    private TextView mUserName;
    private TextView mEmail;
    private TextView mName;
    private TextView mMobile;
    private TextView mBirthDate;
    private TextView mProvider;

    private RelativeLayout mExtension;
    private RelativeLayout mExtension_map;

    private CircleImageView mPhoto;

    public CustomerUserInfo(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mPhoto = activity.findViewById(R.id.profile_photo);
        mUserName = activity.findViewById(R.id.profile_username);
        mEmail = activity.findViewById(R.id.profile_email);
        mName = activity.findViewById(R.id.profile_display_name);
        mMobile = activity.findViewById(R.id.profile_phone_number);
        mBirthDate = activity.findViewById(R.id.profile_birth_date);
        mProvider = activity.findViewById(R.id.profile_provider);
        mExtension = activity.findViewById(R.id.extension_info);
        mExtension_map = activity.findViewById(R.id.extension_map);
    }

    public void displayInformation(){
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseTag.users).child(currentUser.getUid());
        databaseReference.keepSynced(false);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserName.setText("username : " + dataSnapshot.child(FirebaseTag.user_username).getValue().toString());
                mEmail.setText("email : " + dataSnapshot.child(FirebaseTag.user_email).getValue().toString());
                mName.setText("name : " + dataSnapshot.child(FirebaseTag.user_name).getValue().toString());
                mMobile.setText("mobile : " + dataSnapshot.child(FirebaseTag.user_phone).getValue().toString());
                mBirthDate.setText("birth date : " + dataSnapshot.child(FirebaseTag.user_birth_date).getValue().toString());
                mProvider.setText("provider : " + dataSnapshot.child(FirebaseTag.user_provider).getValue().toString());
                mExtension.setVisibility(View.GONE);
                mExtension_map.setVisibility(View.GONE);
                try {
                    Glide.with(activity)
                            .load(dataSnapshot.child(FirebaseTag.user_photo_url).getValue().toString())
                            .into(mPhoto);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Get USER", "loadPost:onCancelled", databaseError.toException());
                ToastManager toastManager = new ToastManager(activity);
                toastManager.displayError(activity.getString(R.string.err_unknown_error));
                // ...
            }
        });
    }
}
