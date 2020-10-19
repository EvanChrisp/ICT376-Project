package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView username;
    Button saveButton, testButton;
    EditText fname, lname, address, phone, email;
    Database mydb = null;
    String userFname, userLname, userAddress, userPhone, userEmail, storedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (TextView)findViewById(R.id.profileTextViewUname);
        fname = (EditText)findViewById(R.id.profileEditTextFname);
        lname = (EditText)findViewById(R.id.profileEditTextLname);
        address = (EditText)findViewById(R.id.profileEditTextAddress);
        phone = (EditText)findViewById(R.id.profileEditTextPhone);
        email = (EditText)findViewById(R.id.profileEditTextEmail);
        saveButton = (Button)findViewById(R.id.profileSaveButton);
        testButton = (Button)findViewById(R.id.profileTestButton);

        Database mydb = new Database(this);

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        Long userId = mydb.returnUserId(storedUserName);


        ArrayList<String> dbArrayList = mydb.returnAllUserDetails(userId);

        String userFname = dbArrayList.get(0);
        String userLname = dbArrayList.get(1);
        String userAddress = dbArrayList.get(2);
        String userPhone = dbArrayList.get(3);
        String userEmail = dbArrayList.get(4);

        if(storedUserName.equals("")){
            username.setText("Please log in");
            fname.setVisibility(View.INVISIBLE);
            lname.setVisibility(View.INVISIBLE);
            address.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            // set the editText fields to uneditable or hidden
        }else{
            username.setText("Username: " +storedUserName);
            fname.setText(userFname);
            lname.setText(userLname);
            address.setText(userAddress);
            phone.setText(userPhone);
            email.setText(userEmail);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTest();
            }
        });


    }

    private void onTest() {
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        mydb = new Database(this);
        long userNum = mydb.returnUserId(storedUserName);

        mydb = new Database(this);

        ArrayList<String> dbArrayList = mydb.returnAllUserDetails(userNum);


        //String myemail = mydb.returnUserEmail(userNum);
        String myFname = dbArrayList.get(0);
        String myLname = dbArrayList.get(1);
        String myAddress = dbArrayList.get(2);
        String myPhone = dbArrayList.get(3);
        String myemail = dbArrayList.get(4);

        Toast.makeText(this, "From the database: Hi "+myFname+ " "+myLname+ " Address: " +myAddress+ " Phone: " +myPhone+ " Email: " +myemail, Toast.LENGTH_LONG).show();

        mydb.close();
    }

    public void onSave(){

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        userFname = fname.getText().toString();
        userLname = lname.getText().toString();
        userAddress = address.getText().toString();
        userPhone = phone.getText().toString();
        userEmail = email.getText().toString();

        mydb = new Database(this);
        // get _id for User
        long userNum = mydb.returnUserId(storedUserName);
        // should show the user id
        Toast.makeText(this, "Current user id is: " +userNum, Toast.LENGTH_SHORT).show();

       mydb.updateUserProfile(userFname, userLname, userPhone,userEmail, userAddress, userNum);

       mydb.close();
    }
}