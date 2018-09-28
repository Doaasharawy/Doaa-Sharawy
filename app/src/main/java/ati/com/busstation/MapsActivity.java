package ati.com.busstation;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javaclasses.MapsInterface;
import maps.AlertDialogManager;
import maps.ConnectionDetector;
import maps.GPSTracker;
import path.ConnectAsyncTask;
import path.DrawPath;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsInterface {

    private GoogleMap mMap;

    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    // GPS Location
    GPSTracker gps;


    int from = 0;
    String longitude = "", latitude = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.i("dataa", "onCreate: "+ "wasaaaaaaaal");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");
            name = getIntent().getStringExtra("name");
            from = getIntent().getIntExtra("from", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            alert.showAlertDialog(MapsActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude()
                    + ", longitude: " + gps.getLongitude());
            LatLng currentLocation = new LatLng(gps.getLatitude(),
                    gps.getLongitude());


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,
                    13));

            mMap.addMarker(new MarkerOptions()
                    .title("My location")
                    .position(currentLocation)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.mark_red)).draggable(false));

            LatLng destinationLocation = new LatLng(0, 0);
            if (from == 1) {

//
            } else {
                destinationLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions()
                        .title("Destination")
                        .position(destinationLocation)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.mark_blue)).draggable(false));

            }


            new ConnectAsyncTask(DrawPath.makeURLDriving(currentLocation.latitude,
                    currentLocation.longitude, destinationLocation.latitude,
                    destinationLocation.longitude), MapsActivity.this).execute();

        }
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }
}
