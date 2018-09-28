package ati.com.busstation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javaclasses.Metro;
import maps.GPSTracker;
import path.DrawPath;

public class MetroActivity extends AppCompatActivity {
    CardView neareststation,metroomap,metrolines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);
neareststation=findViewById(R.id.neareststation);
metroomap=findViewById(R.id.metromaps);
metrolines=findViewById(R.id.metrolines);

metrolines.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MetroActivity.this,metrolinesbutton.class);
        startActivity(intent);
    }
});
metroomap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MetroActivity.this,Metro_Map_lines.class);
        startActivity(intent);
    }
});

       neareststation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(MetroActivity.this, "Metro", "Please wait", "Loading...");

            }
        });
    }


    public static void getData(final MetroActivity currentActivity, String childName1, String title, String msg) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        showDialog(currentActivity, title, msg);

        mDatabase.child(childName1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideDialog(currentActivity);
                currentActivity.updateUI(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void updateUI(DataSnapshot data) {
        ArrayList<Metro> metroArrayList = new ArrayList<>();
        ArrayList<LatLng> metroStationsLocation = new ArrayList<>();

        Log.i("dataa", "updateUI: " + data.toString());
        try {
            for (DataSnapshot currentChild : data.getChildren()) {

                Metro currentMetro = currentChild.getValue(Metro.class);


                Log.i("current metro", "updateUI: " + currentMetro.getName());
                metroArrayList.add(currentMetro);
                LatLng metroLocation = new LatLng(Double.parseDouble(currentMetro.getLongitude()), Double.parseDouble(currentMetro.getLatitude()));
                metroStationsLocation.add(metroLocation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GPSTracker gps = new GPSTracker(this);
            // check if GPS location can get
            if (gps.canGetLocation()) {
                Log.d("Your Location", "latitude:" + gps.getLatitude()
                        + ", longitude: " + gps.getLongitude());
                LatLng currentLocation = new LatLng(gps.getLatitude(),
                        gps.getLongitude());

                int index = DrawPath.getSmallestDistanceIndex(metroStationsLocation, currentLocation);
                Log.i("nearest metro", "updateUI: " + metroArrayList.get(index).getName());
                Log.i("nearest metro", "updateUI index: " + index);
                Log.i("nearest metro", "updateUI latitude: " + metroStationsLocation.get(index).latitude);
                Log.i("nearest metro", "updateUI longitude: " + metroStationsLocation.get(index).longitude);

                Intent i = new Intent(MetroActivity.this, MapsActivity.class);

                i.putExtra("longitude", metroStationsLocation.get(index).longitude + "");
                i.putExtra("latitude", metroStationsLocation.get(index).latitude + "");
                i.putExtra("from", 0);

                startActivity(i);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static ProgressDialog blg;

    public static void showDialog(Activity currentActivity, String title, String msg) {
        blg = new ProgressDialog(currentActivity);
        blg.setTitle(title);
        blg.setMessage(msg);
        blg.show();

    }

    public static void hideDialog(Activity currentActivity) {
        blg.cancel();
    }


}
