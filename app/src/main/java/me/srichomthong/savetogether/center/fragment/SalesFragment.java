package me.srichomthong.savetogether.center.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

public class SalesFragment extends SalesListFragment {

    public SalesFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query allSalesQuery = databaseReference.child(FirebaseTag.sales)
                .limitToFirst(100);
        return allSalesQuery;
    }
}
