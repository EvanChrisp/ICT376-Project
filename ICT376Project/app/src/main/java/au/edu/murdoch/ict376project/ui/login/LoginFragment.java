package au.edu.murdoch.ict376project.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.MainActivity;
import au.edu.murdoch.ict376project.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {


    // model class
    private LoginViewModel loginViewModel;

    // views
    View mLayoutView;
    EditText usernameEt, passwordEt, rePasswordEt;
    Button loginButton, logoutButton, registerButton, clickToRegisterButton;
    TextView loginMsg;
    String storedUserName;


    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all products

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // data comes from the nintendoViewModel class
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // declare view -> inflate (this fragment, into this container, false);
        mLayoutView = inflater.inflate(R.layout.fragment_login, container, false);

        // textView = resource
        final TextView textView = mLayoutView.findViewById(R.id.text_login);

        // use the loginViewModel to set the text to String s
        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // now return the view
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //int userId;

        usernameEt = (EditText)mLayoutView.findViewById(R.id.usernameEt);
        passwordEt = (EditText)mLayoutView.findViewById(R.id.passwordEt);
        rePasswordEt = (EditText)mLayoutView.findViewById(R.id.rePasswordEt);
        clickToRegisterButton = (Button)mLayoutView.findViewById(R.id.clickToRegisterUserPwdButton);
        loginButton = (Button)mLayoutView.findViewById(R.id.loginButton);
        loginMsg = (TextView)mLayoutView.findViewById(R.id.loginMsg);
        logoutButton = (Button)mLayoutView.findViewById(R.id.logoutButton);

        registerButton = (Button)mLayoutView.findViewById(R.id.registerUserPwdButton);

        // when user wants to register - the register button is invisible at the start
        clickToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking the "click to register button"
                // will make the confirm pwd box visible and
                rePasswordEt.setVisibility(View.VISIBLE);
                // set the register button to visible
                registerButton.setVisibility(View.VISIBLE);
                // which then makes the click to register button not needed and this invisible
                clickToRegisterButton.setVisibility((View.INVISIBLE));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "Thanks for registering!", Toast.LENGTH_SHORT).show();
                String myUsername = usernameEt.getText().toString();
                String myPwd = passwordEt.getText().toString();
                String myPwd2 = rePasswordEt.getText().toString();

                // get db
                mydb = new Database(getActivity());

                // check if name is taken
                Boolean isTaken = mydb.checkName(myUsername,myPwd);

                // check for empty fields
                if((myUsername.equals(""))|| (myPwd.equals(""))){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter all details", Toast.LENGTH_SHORT).show();
                    loginMsg.setVisibility(View.VISIBLE);
                    loginMsg.setText("Please enter all details !");
                }else{
                    // check if username is already taken
                    if(isTaken){
                        Toast.makeText(getActivity().getApplicationContext(), "That username is taken", Toast.LENGTH_SHORT).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText("That username is already taken!");
                    }else if(!myPwd.equals(myPwd2)){
                        // check if passwords match
                        Toast.makeText(getActivity().getApplicationContext(), "Passwords do not match - try again", Toast.LENGTH_SHORT).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText("Passwords do not match - try again!");
                        }else{
                            // now that user is not null, not already taken and passwords match - enter into db
                            mydb.insertUserPwd(myUsername, myPwd);

                            Toast.makeText(getActivity().getApplicationContext(), "Thank you for registering", Toast.LENGTH_SHORT).show();
                            loginMsg.setVisibility(View.VISIBLE);
                            loginMsg.setText("You have been registered!");
                        }
                    }

                mydb.close();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking the "login" button does nothing but give a toast msg indicating that the user needs to enter the correct details
                String myUsername = usernameEt.getText().toString();
                String myPwd = passwordEt.getText().toString();
                //Toast.makeText(getActivity().getApplicationContext(), "This does not work yet!", Toast.LENGTH_SHORT).show();

                // get db
                mydb = new Database(getActivity());

                if((myUsername.equals(""))|| (myPwd.equals(""))){
                    Toast.makeText(getActivity().getApplicationContext(), "Fields are blank " +myUsername, Toast.LENGTH_SHORT).show();
                }else{

                    // loginTrue is true if passwordcheck passes
                    boolean loginTrue = mydb.checkPassword(myUsername, myPwd);

                    // todo - add in logic to find userid and set the logged in status in db

                    if(loginTrue){
                        // using SharedPreferences.Editor called editor to do the work

                        // 1. get instance of shared preferences (prefs is the private pref file that stores the values put into in (below....)
                        SharedPreferences userDetails = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

                        // 2. create editor object (this stores the details)
                        SharedPreferences.Editor editor = userDetails.edit();
                        // 3. store the username, password and login status of current logged in user -> called "username" store the value from editText (myUsername) etc
                        editor.putString("username", myUsername);
                        editor.putString("password", myPwd);
                        editor.putBoolean("isLoggedIn", true);
                        // 4. values are applied to private file "prefs"
                        editor.apply();

                        storedUserName = userDetails.getString("username", "");

                        Toast.makeText(getActivity().getApplicationContext(), "Login authenticated - welcome (from shared preferences storage) " +storedUserName, Toast.LENGTH_LONG).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText("Login authenticated");
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Login NOT authenticated - are you registered?", Toast.LENGTH_SHORT).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText("Login NOT authenticated - are you registered?");
                    }
                }



            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get instance of sharedpreferences (that has files stored in "prefs" file
                SharedPreferences userDetails = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                // it already exists, so storedUserName string = the String value in the userDetails object
                storedUserName = userDetails.getString("username", "");
                // TODO add some logout functionality for logged out status (you need to call db and find the logged in flag)

                // check if already logged out - ie, storedUserName will already be ""
                if(storedUserName.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "There is no user currently logged in!", Toast.LENGTH_LONG).show();
                }else{
                    // to log out current user -> change the object values to "" with isLoggedIn now false rather than true
                    Toast.makeText(getActivity().getApplicationContext(), "(From shared preferences storage)" +storedUserName+ ", you have successfully logged out: ", Toast.LENGTH_LONG).show();

                    // get instance of shared preferences
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("prefs", MODE_PRIVATE).edit();
                    // overwriting the userdetails object username/password value to "" and setting the isLoggedIn boolen to false
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();


                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.putExtra("finish", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }




                //finish();
            }
        });

        //Toast.makeText(getActivity(), "This is the login fragment", Toast.LENGTH_SHORT).show();
    }



}