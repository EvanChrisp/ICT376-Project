package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Bundle bundle = getIntent().getExtras();

        TextView name = (TextView)findViewById(R.id.detailsName);
        TextView price = (TextView)findViewById(R.id.detailsPrice);
        TextView itemId = (TextView)findViewById(R.id.detailsId);
        TextView file = (TextView)findViewById(R.id.detailsFile);
        TextView description = (TextView)findViewById(R.id.detailsDescription);
        TextView status = (TextView)findViewById(R.id.detailsStatus);
        TextView rating = (TextView)findViewById(R.id.detailsRating);
        TextView platform = (TextView)findViewById(R.id.detailsPlatform);
        //ImageView image = (ImageView)findViewById(R.id.)

        assert name != null;
        name.setText("Item Name: " + bundle.getString("name"));
        assert price != null;
        price.setText("Price: " +"$ " +bundle.getString("price")+".00");

        // passed from fragment as int -> must convert to use setText on TextView below
        String convertId = Integer.toString(bundle.getInt("_id"));
        assert convertId != null;
        itemId.setText("SKU No: " +convertId);

        assert file != null;
        file.setText(bundle.getString("file"));



        assert description != null;
        description.setText("Game Description: " +bundle.getString("description"));

        assert status != null;
        if(status.equals("0")){
            status.setText("Please order below");
        } else {
            status.setText("Already in cart");
        }
        //status.setText(bundle.getString("status"));

        assert rating != null;
        rating.setText("ESRB Rating: " +bundle.getString("rating"));
        assert platform != null;
        platform.setText("Platform: " +bundle.getString("platform"));
        /*BigDecimal priceVal = BigDecimal.valueOf(bundle.getInt("price"),2); // we had stored price as a whole integer to include cents e.g 1.00 was stored as 100
        assert price != null;
        price.setText("Price: $"+priceVal);*/

    }
}