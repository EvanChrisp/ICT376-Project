package au.edu.murdoch.ict376project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();

        TextView name = findViewById(R.id.detailsName);
        TextView price = findViewById(R.id.detailsPrice);
        TextView itemId = findViewById(R.id.detailsId);
        TextView file = findViewById(R.id.detailsFile);
        TextView description = findViewById(R.id.detailsDescription);
        TextView status = findViewById(R.id.detailsStatus);
        TextView rating = findViewById(R.id.detailsRating);
        TextView platform = findViewById(R.id.detailsPlatform);
        ImageView image = findViewById(R.id.detailsImageView);

        assert bundle != null;
        assert name != null;
        name.setText(getString(R.string.item_name, bundle.getString("name")));
        assert price != null;
        price.setText(getString(R.string.item_price, bundle.getString("price")));

        // passed from fragment as int -> must convert to use setText on TextView below
        String convertId = Integer.toString(bundle.getInt("_id"));
        itemId.setText(getString(R.string.item_sku, convertId));

        assert file != null;
        file.setText(bundle.getString("file"));

        assert image != null;
        int resId = getResources().getIdentifier(
                bundle.getString("file"),
                "drawable",
                getPackageName());
        image.setImageResource(resId);

        assert description != null;
        description.setText(getString(R.string.item_description, bundle.getString("description")));

        // if(String called "status" from bundle is equal to "0" -> that means it has not been added to cart
        if (bundle.getString("status").equals("0")) {
            status.setText(R.string.item_order);
        } else {
            status.setText(R.string.item_in_cart);
        }

        assert rating != null;
        rating.setText(getString(R.string.item_esrb, bundle.getString("rating")));
        assert platform != null;
        platform.setText(getString(R.string.item_platform, bundle.getString("platform")));
        /*BigDecimal priceVal = BigDecimal.valueOf(bundle.getInt("price"),2); // we had stored price as a whole integer to include cents e.g 1.00 was stored as 100
        assert price != null;
        price.setText("Price: $"+priceVal);*/

        db = new Database(this);

        Button cartButton = findViewById(R.id.detailsCartButton);
        assert cartButton != null;
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if successfully adding 1 to status on the item ->    //
                if (db.addToCart(bundle.getInt("_id"), "1")) {
                    //Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(intent);
                    //finish();
                    Toast.makeText(DetailsActivity.this, "Successfully added to shopping cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailsActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button checkoutButton = findViewById(R.id.detailsCheckoutButton);
        assert checkoutButton != null;
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, CheckoutActivity.class);
                startActivity(intent);
                Toast.makeText(DetailsActivity.this, "Please review your selection", Toast.LENGTH_SHORT).show();
            }

        });

        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}