//package me.srichomthong.savetogether.customer.fragment;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//
//import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;
//
//public class FavoriteFragment extends ReviewListFragment {
//
//    public FavoriteFragment() {}
//
//    @Override
//    public Query getQuery(DatabaseReference databaseReference) {
//        // All my posts
//        return databaseReference.child(FirebaseTag.favorit)
//                .child(getUid());
//    }
//}
