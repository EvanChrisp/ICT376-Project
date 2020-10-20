package au.edu.murdoch.ict376project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView username;
    Button saveButton, testButton, clearButton;
    EditText fname, lname, address, phone, email;
    Database mydb = null;
    String userFname, userLname, userAddress, userPhone, userEmail, storedUserName;
    ImageView profileImageView;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = (TextView)findViewById(R.id.profileTextViewUname);
        fname = (EditText)findViewById(R.id.profileEditTextFname);
        lname = (EditText)findViewById(R.id.profileEditTextLname);
        address = (EditText)findViewById(R.id.profileEditTextAddress);
        phone = (EditText)findViewById(R.id.profileEditTextPhone);
        email = (EditText)findViewById(R.id.profileEditTextEmail);
        saveButton = (Button)findViewById(R.id.profileSaveButton);
        testButton = (Button)findViewById(R.id.profileTestButton);
        clearButton = (Button)findViewById(R.id.profileClearButton);
        profileImageView = (ImageView)findViewById(R.id.profileImageView);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // read external storage permission to select image from gallery, runtime permission for devices android 6.0 and above
                // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                ActivityCompat.requestPermissions(
                        ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        Database mydb = new Database(this);

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");



        if(storedUserName.equals("")){
            username.setText("Please log in");
            fname.setVisibility(View.INVISIBLE);
            lname.setVisibility(View.INVISIBLE);
            address.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            clearButton.setVisibility(View.INVISIBLE);
            testButton.setVisibility(View.INVISIBLE);
            // set the editText fields to uneditable or hidden
        }else{

            Long userId = mydb.returnUserId(storedUserName);
            // return arrayList with all string user details
            ArrayList<String> dbArrayList = mydb.returnAllUserDetails(userId);

            // return byte array used for user photo field in db
            byte[] myPhotoByteArray = mydb.returnUserPhoto(userId);

            String userFname = dbArrayList.get(0);
            String userLname = dbArrayList.get(1);
            String userAddress = dbArrayList.get(2);
            String userPhone = dbArrayList.get(3);
            String userEmail = dbArrayList.get(4);

            username.setText("Username: " +storedUserName);
            fname.setText(userFname);
            lname.setText(userLname);
            address.setText(userAddress);
            phone.setText(userPhone);
            email.setText(userEmail);

            // convert the byte array back to bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(myPhotoByteArray,0,myPhotoByteArray.length);
            profileImageView.setImageBitmap(bitmap);


        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveProfile();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTest();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearProfile();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // this is the gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                // get only the type "image"
                galleryIntent.setType("image/*");
                // start activity for result
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // if result is OK - use the cropimage library on imageUri
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) // enable image guidelines
                    .setAspectRatio(1, 1) // image will be square
                    .start(this);
        }
        // returning the image chosen (from gallery) to fill the imageView
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // set image chosen from gallery to image view
                profileImageView.setImageURI(resultUri);
            } // else error....
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onTest() {
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        mydb = new Database(this);
        long userNum = mydb.returnUserId(storedUserName);

        //mydb = new Database(this);

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

    public void onSaveProfile(){

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        userFname = fname.getText().toString();
        userLname = lname.getText().toString();
        userAddress = address.getText().toString();
        userPhone = phone.getText().toString();
        userEmail = email.getText().toString();
        byte[] userPhoto = imageViewToByte(profileImageView);

        mydb = new Database(this);
        // get _id for User
        long userNum = mydb.returnUserId(storedUserName);
        // should show the user id
        //Toast.makeText(this, "Current user id is: " +userNum, Toast.LENGTH_SHORT).show();

       mydb.updateUserProfile(userFname, userLname, userPhone,userEmail, userAddress, userNum);
       mydb.updateUserPhoto(userNum, userPhoto);

       mydb.close();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ( (BitmapDrawable) image.getDrawable() ).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



    private void clearProfile() {
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");

        mydb = new Database(this);
        long userNum = mydb.returnUserId(storedUserName);

        mydb.updateUserProfile("", "", "","", "", userNum);

        mydb.close();

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        // replaced onBackPressed with return to main activity instead
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }
}