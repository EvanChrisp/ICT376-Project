package au.edu.murdoch.ict376project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
    final int CAMERA_REQUEST_CODE = 100;
    final int STORAGE_REQUEST_CODE = 101;
    final int IMAGE_PICK_CAMERA_CODE = 102;
    //final int IMAGE_PICK_GALLERY_CODE = 103;
    String[] cameraPermissions;
    String[] storagePermissions;
    Uri imageUri;

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
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show image pikc dialogue
                imagePickDialogue();

                // read external storage permission to select image from gallery, runtime permission for devices android 6.0 and above
                // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                /*ActivityCompat.requestPermissions(
                        ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );*/
            }
        });

        Database mydb = new Database(this);

        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);
        storedUserName = userDetails.getString("username", "");



        if(storedUserName.equals("")){
            username.setText("This feature is only available for registered users - Please log in");
            fname.setVisibility(View.INVISIBLE);
            lname.setVisibility(View.INVISIBLE);
            address.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            clearButton.setVisibility(View.INVISIBLE);
            testButton.setVisibility(View.INVISIBLE);
            profileImageView.setVisibility(View.INVISIBLE);
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
            if(mydb.returnUserPhoto(userId) != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(myPhotoByteArray,0,myPhotoByteArray.length);
                profileImageView.setImageBitmap(bitmap);
            }

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

        mydb.close();


    }

    private void imagePickDialogue() {
        // options to select
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your profile image from: ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // handle the clicks
                if (i == 0){
                    if(!checkCameraPermissions()){
                        requestCameraPermissions();
                    }else{
                        // permissions have already been granted
                        pickFromCamera();
                    } // end of inner else
                } else if (i == 1){
                    if(!checkStoragePermissions()){
                        requestStoragePermissions();
                    }else{
                        // permissions have already been granted
                        pickFromGallery();
                    } // end of 2nd inner else
                }
            }
        });

        // show the dialog created
        builder.create().show();

    }

    private void pickFromGallery() {
        // this is the gallery intent
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        // get only the type "image"
        galleryIntent.setType("image/*");
        // start activity for result -> returns
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image_Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermissions(){

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermissions(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermissions(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // looking for one of two requestCodes -> CameraRequestCode or StorageRequestcode
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                // grantResult must be greater than zero to be valid
                if (grantResults.length>0){
                    // grantresult == pm.pg is either TRUE OR FALSE == boolean var name.
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(this, "Camera AND Storage permissions are required!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            break;
            case STORAGE_REQUEST_CODE: {
                // grantResult must be greater than zero to be valid
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Storage permissions are required!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            break;
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // this is the gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                // get only the type "image"
                galleryIntent.setType("image/*");
                // start activity for result -> returns
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // if result is OK - use the cropimage library on imageUri
        if(requestCode == IMAGE_PICK_CAMERA_CODE){
            //imageUri = data.getData(); not used for camera
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) // enable image guidelines
                    .setAspectRatio(1, 1) // image will be square
                    .start(this);
        }
        // if result is OK - use the cropimage library on imageUri
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            imageUri = data.getData();
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