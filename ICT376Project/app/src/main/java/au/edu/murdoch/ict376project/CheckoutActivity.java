package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {
    Database dbHelper;
    ListView listView;
    TextView checkoutDisplayAmount;
    Database db;
    String totalAmount;
    Button checkoutPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkoutPayment = (Button)findViewById(R.id.checkoutPayment);

        displayCart();


    }

    private void displayCart(){

        // get database
        dbHelper = new Database(this);
        checkoutDisplayAmount = (TextView)findViewById(R.id.checkoutDisplayAmount);

        // cursor = return from db function
        Cursor cursor = dbHelper.getShoppingCart("1");

        // columns to return
        final String[] columns = new String[]{Database.PRODUCT_ID, Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM, Database.PRODUCT_FILE};

        // column data goes to this layout (in item_layout.xml) per item
        int[] lvResourceIds = new int[]{R.id.cIdTextView, R.id.cNameTextView, R.id.cPlatformTextView, R.id.cImageHolder};

        // cursor adapter requires the id to be _id in the database. Please do not change
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,R.layout.cart_layout, cursor, columns, lvResourceIds,0);

        // the setViewBinder of the data adapter takes every view called R.id.pImageHolder and replaces it with the resId
        // which as per the db changes for each different game
        dataAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                if (view.getId() == R.id.cImageHolder) {
                    ImageView simpleImageView = (ImageView) view;
                    int resId = getResources().getIdentifier(
                            cursor.getString(cursor.getColumnIndex("file")),
                            "drawable",
                            getPackageName());
                    simpleImageView.setImageResource(resId);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // listview - uses the mLayoutView as it is a fragment and not an activity -> listview is displayed in nintendoProductListview container
        listView = (ListView)findViewById(R.id.checkoutListView);

        // listview cannot be null as the db is pre-filled
        assert listView != null;

        // set the adapter and display on screen
        listView.setAdapter(dataAdapter);

        db = new Database(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Cursor cursor = (Cursor)listView.getItemAtPosition(position);

                // Get the item _id and use itemId to set the cart item back to status="0"
                int itemId =  cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                db.removeFromCart(itemId,"0");
                Intent intent = new Intent(CheckoutActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        db.close();
        totalAmount = dbHelper.totalCartValue();
        if(totalAmount.equals("")){
            checkoutDisplayAmount.setText("You have no items in your cart!");
        }else{
            checkoutDisplayAmount.setText("The total amount to pay: $" +totalAmount+ ".00 (AUD)");
        }

        checkoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CreditCardActivity.class);
                // because totalToPay is a String use Integer.parseInt(s) to convert
                intent.putExtra("totalToPay", Integer.parseInt(totalAmount));
                startActivity(intent);
            }
        });

        dbHelper.close();

    }
    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        // replaced onBackPressed with return to main activity instead
        Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }
}