package me.srichomthong.savetogether.customer.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

public class SalesFragment extends SalesListFragment {

    public SalesFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child(FirebaseTag.sale)
                .limitToFirst(100);
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}
