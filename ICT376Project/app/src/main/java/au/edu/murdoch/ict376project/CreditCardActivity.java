package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreditCardActivity extends AppCompatActivity {

    TextView amount;
    Database db;
    EditText payFname, payLname, payAddress, payEmail;
    Button payNow;
    String userFname, userLname, userAddress, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        amount = (TextView)findViewById(R.id.creditCardToPay);
        payFname = (EditText) findViewById(R.id.paymentFname);
        payLname = (EditText) findViewById(R.id.paymentLname);
        payAddress = (EditText) findViewById(R.id.paymentAddress);
        payEmail = (EditText) findViewById(R.id.paymentEmail);

        Intent intent = getIntent();

        int totalToPay = intent.getIntExtra("totalToPay", 0);
        amount.setText("Please pay $" +totalToPay+".00 (AUD)");


        // 1. get instance of shared preferences (prefs is the private pref file that stores the values put into in (below....)
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);

        // 2. String name
        String storedUserName;

        db = new Database(this);
        storedUserName = userDetails.getString("username", "");

        if(storedUserName.equals("")){
            Toast.makeText(this, "Please enter details in all fields", Toast.LENGTH_SHORT).show();
        }else{
            Long userId = db.returnUserId(storedUserName);

            ArrayList<String> dbArrayList = db.returnAllUserDetails(userId);

            userFname = dbArrayList.get(0);
            userLname = dbArrayList.get(1);
            userAddress = dbArrayList.get(2);
            userEmail = dbArrayList.get(4);

        }

        payFname.setText(userFname);
        payLname.setText(userLname);
        payAddress.setText(userAddress);
        payEmail.setText(userEmail);

        payNow = (Button)findViewById(R.id.payNow);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEmail == null){
                    // for anonymous purchases
                    userEmail = payEmail.getText().toString();
                    Toast.makeText(CreditCardActivity.this, "Thank you for ordering from ERE games, a confirmation email has been sent to: "+userEmail, Toast.LENGTH_LONG).show();
                    db.clearCart();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CreditCardActivity.this, "Thank you for ordering from ERE games, a confirmation email has been sent to: "+userEmail, Toast.LENGTH_LONG).show();
                    db.clearCart();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        db.close();

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