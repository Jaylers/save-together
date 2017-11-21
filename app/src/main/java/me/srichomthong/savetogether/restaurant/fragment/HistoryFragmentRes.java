package me.srichomthong.savetogether.restaurant.fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import me.srichomthong.savetogether.center.fragment.SalesResListFragment;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

public class HistoryFragmentRes extends SalesResListFragment {

    public HistoryFragmentRes() {}
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child(FirebaseTag.sales_user).child(myUserId)
                .limitToFirst(100);
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}
