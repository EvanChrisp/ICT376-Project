package au.edu.murdoch.ict376project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

public class CreditCardActivity extends AppCompatActivity {

    TextView payAmount;
    Button payButton;
    EditText cardNumber;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int totalToPay = intent.getIntExtra("totalToPay", 0);

        payButton = (Button)findViewById(R.id.btn_pay);
        payAmount = (TextView)findViewById(R.id.payment_amount);
        cardNumber = (EditText) findViewById(R.id.card_number);
        payAmount.setText(getString(R.string.amount, totalToPay));
        payButton.setText(R.string.pay_now);

        CardForm cardForm = (CardForm) findViewById(R.id.cardform);
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(CreditCardActivity.this, "Name: "+ card.getName() + " | Last 4 digits: "+ card.getLast4(), Toast.LENGTH_SHORT).show();
                placeOrder();
            }
        });


    }

    public void placeOrder(){
        db = new Database(this);
        Toast.makeText(CreditCardActivity.this, "Thank you for ordering from ERE games", Toast.LENGTH_LONG).show();
        db.clearCart();
        db.close();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // code that would have been used if not for the library that simplified the luhn authentication of credit cards

    /*
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
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        amount = findViewById(R.id.creditCardToPay);
        payFname = findViewById(R.id.paymentFname);
        payLname = findViewById(R.id.paymentLname);
        payAddress = findViewById(R.id.paymentAddress);
        payEmail = findViewById(R.id.paymentEmail);
        Intent intent = getIntent();
        int totalToPay = intent.getIntExtra("totalToPay", 0);
        amount.setText(getString(R.string.please_pay, totalToPay));
        // 1. get instance of shared preferences (prefs is the private pref file that stores the values put into in (below....)
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        // 2. String name
        String storedUserName;
        db = new Database(this);
        storedUserName = userDetails.getString("username", "");
        assert storedUserName != null;
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
        payNow = findViewById(R.id.payNow);
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
        */
/*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*//*
        return true;
    }
}*/
}
