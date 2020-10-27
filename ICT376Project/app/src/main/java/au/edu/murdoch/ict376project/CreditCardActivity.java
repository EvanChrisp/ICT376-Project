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

    TextView payAmount, previewCardName, previewCardNumber, previewCvc, previewExpiry;
    Button payButton;
    EditText cardNumber, cvc, expiryDate, cardName;
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
        cardName = (EditText) findViewById(R.id.card_name);
        cvc = (EditText) findViewById(R.id.cvc);
        expiryDate = (EditText) findViewById(R.id.expiry_date);
        payAmount.setText(getString(R.string.amount, totalToPay));
        payButton.setText(R.string.pay_now);
        previewCardName = (TextView) findViewById(R.id.card_preview_name);
        previewCardNumber = (TextView) findViewById(R.id.card_preview_number);
        previewCvc = (TextView) findViewById(R.id.card_preview_cvc);
        previewExpiry = (TextView) findViewById(R.id.card_preview_expiry);

        CardForm cardForm = (CardForm) findViewById(R.id.cardform);
        cardForm.setCardNameError("Name is a required field");
        cardForm.setCardNumberError("Please enter 16 digit credit card number");
        cardForm.setExpiryDateError("Please enter the expiration date MM/YY ");
        cardForm.setCvcError("Please enter the 3 digit CVV/CVC number");
        cardName.setHint("Name on card");
        cardNumber.setHint("Card number");
        expiryDate.setHint("MM/YY");
        cvc.setHint("CVV/CVC");
        previewCardName.setText("Name on card");
        previewExpiry.setText("MM/YY");
        previewCvc.setText("CVV/CVC");

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

    public static final int NONE = 0;
    public int VISA = 1;
    public int MASTERCARD = 2;
    public int DISCOVER = 3;
    public int AMEX = 4;
    public String VISA_PREFIX = "4";
    public String MASTERCARD_PREFIX = "51,52,53,54,55,";
    public String DISCOVER_PREFIX = "6011";
    public String AMEX_PREFIX = "34,37,";


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

    public int getCardType(String cardNumber) {

        if (cardNumber.substring(0, 1).equals(VISA_PREFIX))
            return VISA;
        else if (MASTERCARD_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return MASTERCARD;
        else if (AMEX_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return AMEX;
        else if (cardNumber.substring(0, 4).equals(DISCOVER_PREFIX))
            return DISCOVER;

        return NONE;
    }

    public static boolean isValid(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() >= 4)
            if (getCardType(cardNumber) == VISA && ((cardNumber.length() == 13 || cardNumber.length() == 16)))
                return true;
            else if (getCardType(cardNumber) == MASTERCARD && cardNumber.length() == 16)
                return true;
            else if (getCardType(cardNumber) == AMEX && cardNumber.length() == 15)
                return true;
            else if (getCardType(cardNumber) == DISCOVER && cardNumber.length() == 16)
                return true;
        return false;
    }

    public static boolean isValidDate(String cardValidity) {
        if (!TextUtils.isEmpty(cardValidity) && cardValidity.length() == 5)
        {
            String month=cardValidity.substring(0,2);
            String year=cardValidity.substring(3,5);

            int monthpart=-1,yearpart=-1;

            try
            {
                monthpart=Integer.parseInt(month)-1;
                yearpart=Integer.parseInt(year);

                Calendar current = Calendar.getInstance();
                current.set(Calendar.DATE,1);
                current.set(Calendar.HOUR,12);
                current.set(Calendar.MINUTE,0);
                current.set(Calendar.SECOND,0);
                current.set(Calendar.MILLISECOND,0);

                Calendar validity=Calendar.getInstance();
                validity.set(Calendar.DATE,1);
                validity.set(Calendar.HOUR,12);
                validity.set(Calendar.MINUTE,0);
                validity.set(Calendar.SECOND,0);
                validity.set(Calendar.MILLISECOND,0);

                if(monthpart>-1&&monthpart<12&&yearpart>-1)
                {
                    validity.set(Calendar.MONTH,monthpart);
                    validity.set(Calendar.YEAR,yearpart+2000);
                }
                else
                    return false;

                if(current.compareTo(validity)<=0)
                    return true;
            }
            catch(Exception e)
            {
                //e.printStackTrace();
            }
        }

        return false;
    }





}*/
}
