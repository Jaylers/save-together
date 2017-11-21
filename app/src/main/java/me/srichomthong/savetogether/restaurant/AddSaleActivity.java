package me.srichomthong.savetogether.restaurant;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.model.Sale;
import me.srichomthong.savetogether.utility.manager.DialogManager;
import me.srichomthong.savetogether.utility.manager.ToastManager;
import me.srichomthong.savetogether.utility.sharedstring.FirebaseTag;

public class AddSaleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "NewRouteActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @BindView(R.id.sale_item_date_btn) Button date_btn;
    @BindView(R.id.sale_shop_title)TextView txt_shop;
    @BindView(R.id.sale_item_name)EditText edt_name;
    @BindView(R.id.sale_item_full_price)EditText edt_full_price;
    @BindView(R.id.sale_item_new_price)EditText edt_new_price;
    @BindView(R.id.sale_item_detail)EditText edt_detail;
    @BindView(R.id.sale_item_count)EditText edt_count;
    FloatingActionButton mSubmitButton;
    private ToastManager toastManager;
    private DialogManager dialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);
        ButterKnife.bind(this);
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mSubmitButton = findViewById(R.id.fab_submit_post);
        dialogManager = new DialogManager(AddSaleActivity.this);
        toastManager = new ToastManager(AddSaleActivity.this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        txt_shop.setText(mUser.getDisplayName());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    @OnClick(R.id.new_sale_back)public void onBack(){
        this.finish();
    }

    @OnClick(R.id.sale_item_date_btn) public void onSetDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddSaleActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Date Picker Dialog");
        datePickerDialog.show(getFragmentManager(), "DatePicker");
    }

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private void submitPost() {
        String shop = mUser.getUid();
        String shop_name = mUser.getDisplayName();
        String title = edt_name.getText().toString();
        String body = edt_detail.getText().toString();
        String full_price = edt_full_price.getText().toString();
        String new_price = edt_new_price.getText().toString();
        String count = edt_count.getText().toString();
        String date = date_btn.getText().toString();
        String shop_url = mUser.getPhotoUrl().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            edt_name.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(body)) {
            edt_detail.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(full_price)) {
            edt_full_price.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(count)) {
            edt_count.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(new_price)) {
            edt_new_price.setError(REQUIRED);
            return;
        }

        if (!TextUtils.isDigitsOnly(full_price)){
            edt_full_price.setError(getString(R.string.err_only_number_require));
            return;
        }

        if (!TextUtils.isDigitsOnly(new_price)){
            edt_full_price.setError(getString(R.string.err_only_number_require));
            return;
        }

        if (!TextUtils.isDigitsOnly(count)){
            edt_full_price.setError(getString(R.string.err_only_number_require));
            return;
        }

        // Disable button so there are no multi-posts
        toastManager.displayInfo(getString(R.string.app_message_selling));
        dialogManager.displayLoading();

        // [START single_value_read]
        writeNewSale(shop, title, body, full_price, new_price, date, shop_name, 5, shop_url);
    }

    // [START write_fan_out]
    private void writeNewSale(String shop, String title, String body, String full_price,
                              String new_price, String date, String shop_name, int count, String shop_url) {
        // Create new post at /user-sales/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child(FirebaseTag.sales).push().getKey();
        Sale sale = new Sale(key, title, body, Integer.parseInt(full_price), Integer.parseInt(new_price), shop, date, shop_name, count, shop_url);
        Map<String, Object> postValues = sale.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+FirebaseTag.sales +"/" + key, postValues);
        childUpdates.put("/"+FirebaseTag.sales_user +"/" + mUser.getUid() + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

        dialogManager.dismissDisplay();
        toastManager.displaySuccess(getString(R.string.app_message_sale_done));
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        date_btn.setText(date);
    }
    // [END write_fan_out]
}
