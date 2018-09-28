package ati.com.busstation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import maps.GPSTracker;
import utils.HelperCLass;

public class alertdialogcurrentlocation_arabic extends AppCompatActivity {
    RadioGroup rg;
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialogcurrentlocation_arabic);
        try {
            from = getIntent().getStringExtra("from");
        } catch (Exception e) {
            from = "source";
            e.printStackTrace();
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .4));
        rg = findViewById(R.id.radiogroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton6) {
                    Intent i = new Intent(alertdialogcurrentlocation_arabic.this, locationActivity.class);
                    i.putExtra("from", from);
                    if (from.equalsIgnoreCase("source")) {
                        HelperCLass.sourceLocationName = "";
                    } else {
                        HelperCLass.destinationLocationName = "";
                    }
                    startActivity(i);
                    finish();

                } else if (checkedId == R.id.radioButton5) {
                    GPSTracker gps = new GPSTracker(alertdialogcurrentlocation_arabic.this);

                    // check if GPS location can get
                    if (gps.canGetLocation()) {
                        Log.d("Your Location", "latitude:" + gps.getLatitude()
                                + ", longitude: " + gps.getLongitude());
                        LatLng currentLocation = new LatLng(gps.getLatitude(),
                                gps.getLongitude());
                        if (from.equalsIgnoreCase("source")) {
                            HelperCLass.sourceLocation = currentLocation;
                        } else {
                            HelperCLass.destinationLocation = currentLocation;
                        }
                        Toast.makeText(alertdialogcurrentlocation_arabic.this, "location entered sucessfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(alertdialogcurrentlocation_arabic.this, "Couldn't get Location please open internet connection", Toast.LENGTH_SHORT).show();

                    }
                    finish();
                }
                if (checkedId == R.id.radioButton7) {
                    Intent i = new Intent(alertdialogcurrentlocation_arabic.this, StationsListActivity.class);
                    i.putExtra("from", from);
                    startActivity(i);
                    finish();

                }

            }
        });


    }}