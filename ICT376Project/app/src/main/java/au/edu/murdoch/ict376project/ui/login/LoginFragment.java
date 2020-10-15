package au.edu.murdoch.ict376project.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.nintendo.NintendoViewModel;

public class LoginFragment extends Fragment {

    // model class
    private LoginViewModel loginViewModel;
    View mLayoutView;
    EditText username;
    EditText password;
    EditText rePassword;
    Button loginButton;
    Button registerButton;
    Button clickToRegisterButton;


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

        rePassword = (EditText)mLayoutView.findViewById(R.id.rePasswordEt);
        clickToRegisterButton = (Button)mLayoutView.findViewById(R.id.clickToRegisterUserPwdButton);
        loginButton = (Button)mLayoutView.findViewById(R.id.loginButton);

        registerButton = (Button)mLayoutView.findViewById(R.id.registerUserPwdButton);

        // when user wants to register - the register button is invisible at the start
        clickToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking the "click to register button"
                // will make the confirm pwd box visible and
                rePassword.setVisibility(View.VISIBLE);
                // set the register button to visible
                registerButton.setVisibility(View.VISIBLE);
                // which then makes the click to register button not needed and this invisible
                clickToRegisterButton.setVisibility((View.INVISIBLE));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking the "register" button does nothing but give a toast msg indicating that this is mainly working. No db hoolup yet.
                Toast.makeText(getActivity().getApplicationContext(), "Thanks for registering!", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking the "login" button does nothing but give a toast msg indicating that the user needs to enter the correct details
                Toast.makeText(getActivity().getApplicationContext(), "This does not work yet!", Toast.LENGTH_SHORT).show();
            }
        });





        Toast.makeText(getActivity(), "This is the login fragment", Toast.LENGTH_SHORT).show();
    }

    public void registerMode(){

    }

    public void loginMode(){

    }

}