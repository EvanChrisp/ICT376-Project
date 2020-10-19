package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CreditCardActivity extends AppCompatActivity {

    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        amount = (TextView)findViewById(R.id.creditCardToPay);

        Intent intent = getIntent();

        int totalToPay = intent.getIntExtra("totalToPay", 0);
        amount.setText("Please pay $" +totalToPay+".00 (AUD)");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        // replaced onBackPressed with return to main activity instead
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        return true;
    }
}