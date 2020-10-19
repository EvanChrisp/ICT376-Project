package au.edu.murdoch.ict376project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    Database mydb = null;
    private AppBarConfiguration mAppBarConfiguration;
    TextView loginName, loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // check for db - if null create from insert my shop items
        mydb = new Database(this);

        //check if items are available
        int total = mydb.getTotalItemsCount();
        if (total<= 0){
            //Add some data -> here is where the database is updated with default entries
            // usually this would be spooled from an updated online database of current inventory
            mydb.insertMyShopItems();
        }

        /*if (mydb == null){
            mydb = new Database(this);
            mydb.insertMyShopItems();
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // need to getHeaderView to modify textView element
        View headerView = navigationView.getHeaderView(0);
        // now init textView based on headerView view object
        loginName = (TextView)headerView.findViewById(R.id.navHeaderName);
        loginEmail = (TextView)headerView.findViewById(R.id.navHeaderEmail);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_playstation, R.id.nav_xbox, R.id.nav_nintendo, R.id.nav_pc, R.id.nav_search, R.id.nav_contact, R.id.nav_news, R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 1. get instance of shared preferences (prefs is the private pref file that stores the values put into in (below....)
        SharedPreferences userDetails = getSharedPreferences("prefs", MODE_PRIVATE);

        // 2. String name
        String storedUserName;

        // 3. init with value from shared prefs - if username in the userDetails object is NOT null
        if(userDetails.getString("username","")!= null){
            storedUserName = userDetails.getString("username", "");

            if(storedUserName.equals("")){
                Toast.makeText(this,"You are currently logged in anonymously", Toast.LENGTH_LONG).show();
                loginName.setText("Anonymous");
                loginEmail.setText("Email not set");

            }else{
                Toast.makeText(this,"Welcome back " +storedUserName+"!", Toast.LENGTH_LONG).show();
                loginName.setText(storedUserName);
            }
        }
        mydb.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                //Toast.makeText(getApplicationContext(),"You clicked settings", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_cart:
                //Toast.makeText(getApplicationContext(),"You clicked Go to cart", Toast.LENGTH_SHORT).show();
                Intent cartIntent = new Intent(this, CheckoutActivity.class);
                startActivity(cartIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }




}
