package au.edu.murdoch.ict376project.ui;

import androidx.appcompat.app.AppCompatActivity;
import au.edu.murdoch.ict376project.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (TextView)findViewById(R.id.profileTextViewUname);

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        String storedUserName = userDetails.getString("username", "");

        username.setText("Username: " +storedUserName);

    }
}