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

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.MainActivity;
import au.edu.murdoch.ict376project.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment
{
    // views
    View mLayoutView;
    EditText usernameEt, passwordEt, rePasswordEt;
    Button loginButton, logoutButton, registerButton, clickToRegisterButton, clickToReturnToLogin, clickToReturnToRegister;
    TextView loginMsg, loginStatus;
    String storedUserName;

    // Database
    Database mydb = null;

    // static
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
        "(?=.*[0-9])" +         //at least 1 digit
        "(?=.*[a-z])" +         //at least 1 lower case letter
        "(?=.*[A-Z])" +         //at least 1 upper case letter
        //"(?=.*[a-zA-Z])" +    //any letter
        "(?=.*[@#$%^&!+=])" +    //at least 1 special character
        "(?=\\S+$)" +           //no white spaces
        ".{6,20}" +             //at least 6 characters but no more than 20
        "$");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // declare view -> inflate (this fragment, into this container, false);
        mLayoutView = inflater.inflate(R.layout.fragment_login, container, false);

        /*// textView = resource
        final TextView textView = mLayoutView.findViewById(R.id.text_login);

        // use the loginViewModel to set the text to String s
        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        loginStatus = mLayoutView.findViewById(R.id.loginStatus);
        loginButton = mLayoutView.findViewById(R.id.loginButton);

        // 1. get instance of shared preferences (prefs is the private pref file that stores the values put into in (below....)
        assert getActivity() != null;
        SharedPreferences userDetails = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        // 2. String name

        // 3. init with value from shared prefs - if username in the userDetails object is NOT null
        if(userDetails.getString("username","")!= null){
            storedUserName = userDetails.getString("username", "");

            assert storedUserName != null;
            if(storedUserName.equals("")){
                loginStatus.setText(R.string.login_anonymous);
            }else{
                loginStatus.setText(getString(R.string.login_as, storedUserName));
                loginButton.setVisibility(View.INVISIBLE);
            }
        }

        // now return the view
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //int userId;

        usernameEt = mLayoutView.findViewById(R.id.usernameEt);
        passwordEt = mLayoutView.findViewById(R.id.passwordEt);
        rePasswordEt = mLayoutView.findViewById(R.id.rePasswordEt);
        clickToRegisterButton = mLayoutView.findViewById(R.id.clickToRegisterUserPwdButton);
        loginButton = mLayoutView.findViewById(R.id.loginButton);
        loginMsg = mLayoutView.findViewById(R.id.loginMsg);
        logoutButton = mLayoutView.findViewById(R.id.logoutButton);
        clickToReturnToLogin = mLayoutView.findViewById(R.id.clickToReturnToLogin);
        clickToReturnToRegister = mLayoutView.findViewById(R.id.clickToReturnToRegister);


        registerButton = mLayoutView.findViewById(R.id.registerUserPwdButton);

        clickToReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!storedUserName.equals("")){
                    assert getActivity() != null;
                    Toast.makeText(getActivity().getApplicationContext(),"Please log out first", Toast.LENGTH_LONG).show();
                }else{
                    clickToReturnToLogin.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    clickToRegisterButton.setVisibility(View.VISIBLE);
                    rePasswordEt.setVisibility(View.INVISIBLE);
                    registerButton.setVisibility(View.INVISIBLE);
                }

            }
        });

        clickToReturnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setVisibility(View.INVISIBLE);
                clickToReturnToRegister.setVisibility(View.INVISIBLE);
                clickToReturnToLogin.setVisibility(View.VISIBLE);
                rePasswordEt.setVisibility(View.VISIBLE);
            }
        });

        // when user wants to register - the register button is invisible at the start
        clickToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setVisibility(View.INVISIBLE);
                clickToRegisterButton.setVisibility(View.INVISIBLE);
                clickToReturnToLogin.setVisibility(View.VISIBLE);
                rePasswordEt.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.VISIBLE);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myUsername = usernameEt.getText().toString();

                // get db
                mydb = new Database(getActivity());

                // check if name is taken
                Boolean isTaken = mydb.checkName(myUsername);

                // validation version 2 - check for empty name
                if(validateUsername()){
                    if(isTaken){
                        usernameEt.setError("That username is taken");
                    } else {
                        loginMsg.setText("");
                        if(validatePassword()){
                            if(validateMatch()){
                                registerUser();

                            }
                        }
                    }
                }

                mydb.close();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myUsername = usernameEt.getText().toString();
                String myPwd = passwordEt.getText().toString();

                // get db
                mydb = new Database(getActivity());

                if( myUsername.isEmpty() || myPwd.isEmpty() ){
                        loginMsg.setText(R.string.enter_both);
                } else{

                    // now check user+password against db entry

                    boolean loginTrue = mydb.checkPassword(myUsername, myPwd);

                    assert getActivity() != null;
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

                        Toast.makeText(getActivity().getApplicationContext(), "Login authenticated!", Toast.LENGTH_SHORT).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText(R.string.authenticated);
                        // this will clear the cart if anonymous user added items to cart (they don't log out).
                        mydb.clearCart();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Login NOT authenticated - are you registered?", Toast.LENGTH_SHORT).show();
                        loginMsg.setVisibility(View.VISIBLE);
                        loginMsg.setText(R.string.not_authenticated);
                    }
                }

                mydb.close();

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database mydb;
                // get instance of sharedpreferences (that has files stored in "prefs" file
                assert getActivity() != null;
                SharedPreferences userDetails = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                // it already exists, so storedUserName string = the String value in the userDetails object
                storedUserName = userDetails.getString("username", "");

                // check if already logged out - ie, storedUserName will already be ""
                assert storedUserName != null;
                if(storedUserName.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "There is no user currently logged in!", Toast.LENGTH_LONG).show();
                }else{
                    // to log out current user -> change the object values to "" with isLoggedIn now false rather than true
                    Toast.makeText(getActivity().getApplicationContext(), "Thanks for shopping with us " +storedUserName+ ", you have successfully logged out: ", Toast.LENGTH_LONG).show();
                    mydb = new Database(getActivity());
                    mydb.clearCart();
                    mydb.close();

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

    private boolean validateUsername() {
        String username = usernameEt.getText().toString().trim();

        if (username.isEmpty()) {
            usernameEt.setError("Field can't be empty");
            return false;
        } else if (username.length() > 15) {
            usernameEt.setError("Your username is too long");
            return false;
        } else {
            usernameEt.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = passwordEt.getText().toString().trim();
        if (password.isEmpty()) {
            passwordEt.setError("Field can't be empty");
            return false;
        }
        String password2 = rePasswordEt.getText().toString().trim();
        if (password2.isEmpty()) {
            rePasswordEt.setError("Field can't be empty");
            return false;
        }else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordEt.setError("Your password is not strong enough");
            assert getActivity() != null;
            Toast.makeText(getActivity().getApplicationContext(), "Please enter 1 upper, 1 lower, 1 special and minimum 6 characters (with max of 20)", Toast.LENGTH_LONG).show();
            return false;
        } else {
            passwordEt.setError(null);
            return true;
        }
    }

    private boolean validateMatch() {
        String password = passwordEt.getText().toString().trim();
        String rePassword = rePasswordEt.getText().toString().trim();

        if (!password.equals(rePassword)) {
            rePasswordEt.setError("Passwords do not match");
            return false;
        } else  {
            return true;
        }
    }

    private void registerUser(){
        String myUsername = usernameEt.getText().toString();
        String myPwd = passwordEt.getText().toString();
        mydb.insertUserPwd(myUsername, myPwd);
        assert getActivity() != null;
        Toast.makeText(getActivity().getApplicationContext(), "Thank you for registering", Toast.LENGTH_LONG).show();
        loginMsg.setVisibility(View.VISIBLE);
        loginMsg.setText(R.string.been_registered);
        clickToReturnToLogin.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        clickToRegisterButton.setVisibility(View.VISIBLE);
        rePasswordEt.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
    }

}//EditText usernameEt, passwordEt, rePasswordEt;