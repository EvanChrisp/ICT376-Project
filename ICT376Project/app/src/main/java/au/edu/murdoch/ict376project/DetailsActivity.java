package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        assert name != null;
        name.setText(bundle.getString("name"));
        assert price != null;
        price.setText(bundle.getString("price"));

        // passed from fragment as int -> must convert to use setText on TextView below
        String convertId = Integer.toString(bundle.getInt("_id"));
        assert convertId != null;
        itemId.setText(convertId);

        assert file != null;
        file.setText(bundle.getString("file"));
        assert description != null;
        description.setText(bundle.getString("description"));
        assert status != null;
        status.setText(bundle.getString("status"));
        assert rating != null;
        rating.setText(bundle.getString("rating"));
        assert platform != null;
        platform.setText(bundle.getString("platform"));
        /*BigDecimal priceVal = BigDecimal.valueOf(bundle.getInt("price"),2); // we had stored price as a whole integer to include cents e.g 1.00 was stored as 100
        assert price != null;
        price.setText("Price: $"+priceVal);*/

    }
}