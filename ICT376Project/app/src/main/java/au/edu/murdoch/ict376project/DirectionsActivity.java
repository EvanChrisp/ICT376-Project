package au.edu.murdoch.ict376project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


public class DirectionsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Button requestLocation;
    final int LOCATION_REQUEST_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        requestLocation = findViewById(R.id.mapButton);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set button to be able to check for permissions
        requestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkLocationPermissions()){
                    ActivityCompat.requestPermissions(DirectionsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                }
            }
        });

    }

    // check for location permissions
    private boolean checkLocationPermissions(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // could use googleMap -> I use the global to make it easier to pass around -> mMap = googleMap;
        mMap = googleMap;

        // setting the latitude and longitude of each ERE games outlet

        LatLng Murdoch = new LatLng(-32.068456, 115.834925);
        mMap.addMarker(new MarkerOptions().position(Murdoch).title("ERE Games Murdoch"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Murdoch));

        LatLng Cannington = new LatLng(-32.019438, 115.936375);
        mMap.addMarker(new MarkerOptions().position(Cannington).title("ERE Games Cannington"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Cannington));

        LatLng Midland = new LatLng(-31.890344, 116.010405);
        mMap.addMarker(new MarkerOptions().position(Midland).title("ERE Games Midland"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Midland));

        LatLng Joondalup = new LatLng(-31.745981, 115.769449);
        mMap.addMarker(new MarkerOptions().position(Joondalup).title("ERE Games Midland"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Joondalup));

        LatLng Perth = new LatLng(-31.953096, 115.860720);
        mMap.addMarker(new MarkerOptions().position(Perth).title("ERE Games Midland"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Perth));

        // permissions check for Android M and above (does not need to be declared at runtime under Android M)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Toast.makeText(this, "permission is not given for location", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
        else {
            // permissions check not needed for below Android M
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
    protected synchronized void buildGoogleApiClient() {
        // boilerplate for google maps api
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        // make new location request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        // using balanced power setting for location accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // another permissions check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
            // not yet implemented
    }

    @Override
    public void onLocationChanged(Location location) {

        // updates to location
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place the current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move the map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop the location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // to do
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        // switch case on requestCode coming through
        switch (requestCode) {
            // first case LOCATION_REQUEST_CODE int value is 777 as in global member variable at the top
            case LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                // check grantResults
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // check the String permissions[]
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // restart activity if permission granted
                        Intent intent = new Intent(this, DirectionsActivity.class);
                        startActivity(intent);
                    }

                } else {
                    // in every other case - permissions are not set correctly and you need a message to the user why the page does not display anything
                    Toast.makeText(this, "Maps needs your location permission", Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

}