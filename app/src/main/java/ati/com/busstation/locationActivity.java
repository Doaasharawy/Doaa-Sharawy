package ati.com.busstation;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javaclasses.MapsInterface;
import maps.AlertDialogManager;
import maps.ConnectionDetector;
import maps.GPSTracker;
import path.ConnectAsyncTask;
import path.DrawPath;
import utils.HelperCLass;

public class locationActivity extends FragmentActivity implements OnMapReadyCallback, MapsInterface {

    private GoogleMap mMap;
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    // GPS Location
    GPSTracker gps;
    String from = "";
    TextView destinationname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        try {
            from = getIntent().getStringExtra("from");
            System.out.println(from.charAt(1));
        } catch (Exception e) {
            from = "";
            e.printStackTrace();
        }
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onsearch(View view) {
        EditText locationtext = findViewById(R.id.autocomplete);
        String location = locationtext.getText().toString();
        List<Address> addressList = null;
        if (location != null || location.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Address address = addressList.get(0);
            final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .title("My location")
                    .position(latLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.mark_red)).draggable(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(final Marker marker) {
                    Double latitude = marker.getPosition().latitude;
                    Double longitude = marker.getPosition().longitude;

                    Log.i("latitude", "onMarkerDragEnd: " + latitude);
                    Log.i("longitude", "onMarkerDragEnd: " + longitude);

                    AlertDialog.Builder builder = new AlertDialog.Builder(locationActivity.this);
                    builder.setTitle("Do you want to save this location");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (from.equalsIgnoreCase("source")) {
                                HelperCLass.sourceLocation = marker.getPosition();


//                                HelperCLass.sourceLocation = new LatLng(marker.getPosition().longitude, marker.getPosition().latitude);

                                finish();
                            } else if (from.equalsIgnoreCase("destination")) {
                                HelperCLass.destinationLocation = marker.getPosition();
//                                HelperCLass.destinationLocation = new LatLng(marker.getPosition().longitude, marker.getPosition().latitude);
                                finish();
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                }
            });

        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        cd = new ConnectionDetector(getApplicationContext());
        try {
            // Check if Internet present
            isInternetPresent = cd.isConnectingToInternet();
            if (!isInternetPresent) {
                // Internet Connection is not present
                alert.showAlertDialog(locationActivity.this,
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

                if (!from.equalsIgnoreCase("")) {

                    mMap.addMarker(new MarkerOptions()
                            .title("My location")
                            .position(currentLocation)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.mark_red)).draggable(true));

                    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(final Marker marker) {
                            Double latitude = marker.getPosition().latitude;
                            Double longitude = marker.getPosition().longitude;

                            Log.i("latitude", "onMarkerDragEnd: " + latitude);
                            Log.i("longitude", "onMarkerDragEnd: " + longitude);

                            AlertDialog.Builder builder = new AlertDialog.Builder(locationActivity.this);
                            builder.setTitle("Do you want to save this location");
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setCancelable(false);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    if (from.equalsIgnoreCase("source")) {
                                        HelperCLass.sourceLocation = marker.getPosition();


//                                HelperCLass.sourceLocation = new LatLng(marker.getPosition().longitude, marker.getPosition().latitude);

                                        finish();
                                    } else if (from.equalsIgnoreCase("destination")) {
                                        HelperCLass.destinationLocation = marker.getPosition();
//                                HelperCLass.destinationLocation = new LatLng(marker.getPosition().longitude, marker.getPosition().latitude);
                                        finish();
                                    }

                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();


                        }
                    });
                } else {
                    if (HelperCLass.sourceLocation.longitude != 0) {

                        mMap.addMarker(new MarkerOptions()
                                .title("Source location")
                                .position(HelperCLass.sourceLocation)
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.mark_red)).draggable(false));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HelperCLass.sourceLocation,
                                13));

                    } else {
                        Toast.makeText(locationActivity.this, "wasal 5 ", Toast.LENGTH_SHORT).show();

                        mMap.addMarker(new MarkerOptions()
                                .title("My location")
                                .position(currentLocation)
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.mark_red)).draggable(false));
                    }

                    LatLng destinationLocation = new LatLng(0, 0);


                    ArrayList<LatLng> stations = new ArrayList<>();
                    for (int i = 0; i < HelperCLass.selectedBus.getStations().size(); i++) {
                        stations.add(new LatLng(Double.parseDouble(HelperCLass.selectedBus.getStations().get(i).getLongitude()), Double.parseDouble(HelperCLass.selectedBus.getStations().get(i).getLatitude())));

                    }

                    int nearestDestinationIndex = DrawPath.getSmallestDistanceIndex(HelperCLass.selectedBus.getStations(), "destination");

                    destinationLocation = new LatLng(Double.parseDouble(HelperCLass.selectedBus.getStations().get(nearestDestinationIndex).getLongitude()), Double.parseDouble(HelperCLass.selectedBus.getStations().get(nearestDestinationIndex).getLatitude()));
                    Log.i("destination longitude ", "onMapReady: " + destinationLocation.longitude);
                    Log.i("destination latitude ", "onMapReady: " + destinationLocation.latitude);

                    mMap.addMarker(new MarkerOptions()
                            .title("Destination")
                            .position(destinationLocation)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.mark_blue)).draggable(false));


                    new ConnectAsyncTask(DrawPath.makeURLWalking(HelperCLass.sourceLocation.latitude,
                            HelperCLass.sourceLocation.longitude, destinationLocation.latitude,
                            destinationLocation.longitude), locationActivity.this).execute();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public GoogleMap getmMap() {
        return mMap;
    }
}