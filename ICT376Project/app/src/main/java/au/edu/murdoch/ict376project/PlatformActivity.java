package au.edu.murdoch.ict376project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import au.edu.murdoch.ict376project.ui.nintendo.NintendoFragment;
import au.edu.murdoch.ict376project.ui.pc.PCFragment;
import au.edu.murdoch.ict376project.ui.playstation.PlaystationFragment;
import au.edu.murdoch.ict376project.ui.xbox.XboxFragment;

import android.content.Intent;
import android.os.Bundle;

public class PlatformActivity extends AppCompatActivity {

    PCFragment pcFragment;
    XboxFragment xboxFragment;
    PlaystationFragment playstationFragment;
    NintendoFragment nintendoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);

        Bundle extras = getIntent().getExtras();
        int platform = extras.getInt("platform", 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // platform = PC
        if(platform == 1) {

            if (savedInstanceState == null) {
                pcFragment = PCFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.platformContainer, pcFragment).commit();
                setTitle("PC Games");
            } else {
                pcFragment = (PCFragment) getSupportFragmentManager().findFragmentById(R.id.platformContainer);
            }
        }

        if(platform == 2) {

            if (savedInstanceState == null) {
                xboxFragment = XboxFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.platformContainer, xboxFragment).commit();
                setTitle("Xbox Games");
            } else {
                xboxFragment = (XboxFragment) getSupportFragmentManager().findFragmentById(R.id.platformContainer);
            }
        }

        if(platform == 3) {

            if (savedInstanceState == null) {
                playstationFragment = PlaystationFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.platformContainer, playstationFragment).commit();
                setTitle("Playstation Games");
            } else {
                playstationFragment = (PlaystationFragment) getSupportFragmentManager().findFragmentById(R.id.platformContainer);
            }
        }

        if(platform == 4) {

            if (savedInstanceState == null) {
                nintendoFragment = NintendoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.platformContainer, nintendoFragment).commit();
                setTitle("Nintendo Games");
            } else {
                nintendoFragment = (NintendoFragment) getSupportFragmentManager().findFragmentById(R.id.platformContainer);
            }
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        // replaced onBackPressed with return to main activity instead
        Intent intent = new Intent(PlatformActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }
}