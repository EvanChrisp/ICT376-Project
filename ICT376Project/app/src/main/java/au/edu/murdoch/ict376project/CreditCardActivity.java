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
}
