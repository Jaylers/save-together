//package me.srichomthong.savetogether.customer.fragment;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//
//import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;
//
//public class ReviewFragment extends ReviewListFragment {
//
//    public ReviewFragment() {}
//
//    @Override
//    public Query getQuery(DatabaseReference databaseReference) {
//        // [START recent_posts_query]
//        // Last 100 posts, these are automatically the 100 most recent
//        // due to sorting by push() keys
//        Query recentPostsQuery = databaseReference.child(FirebaseTag.review)
//                .limitToFirst(100);
//        // [END recent_posts_query]
//
//        return recentPostsQuery;
//    }
//}
