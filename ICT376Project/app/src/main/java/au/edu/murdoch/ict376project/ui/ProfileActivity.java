package au.edu.murdoch.ict376project.ui;

import androidx.appcompat.app.AppCompatActivity;
import au.edu.murdoch.ict376project.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView username;
    Button saveButton;
    EditText fname, lname, address, phone, email;

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


        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        String storedUserName = userDetails.getString("username", "");

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
        }


    }
}