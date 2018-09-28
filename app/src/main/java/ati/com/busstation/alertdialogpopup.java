package ati.com.busstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import maps.GPSTracker;
import utils.HelperCLass;

/**
 * Created by Shaarawy on 4/10/2018.
 */

public class alertdialogpopup extends AppCompatActivity {
    RadioGroup rg;
    String from;
    ImageView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialogcurrentlocation);
        language=findViewById(R.id.languagebutton);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(alertdialogpopup.this);
                builder.setTitle("Change language");
                builder.setMessage("do you want to change language");
                builder.setCancelable(false);

                AlertDialog.Builder builder1 = builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(getApplicationContext(), "yes button clicked", Toast.LENGTH_LONG).show();
                        Intent r = new Intent(alertdialogpopup.this, alertdialogcurrentlocation_arabic.class);
                        r.putExtra("from", "source");
                        startActivity(r);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alart = builder.create();
                alart.show();
            }
        });
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
                if (checkedId == R.id.radioButton2) {
                    Intent i = new Intent(alertdialogpopup.this, locationActivity.class);
                    i.putExtra("from", from);
                    if (from.equalsIgnoreCase("source")) {
                        HelperCLass.sourceLocationName = "";
                    } else {
                        HelperCLass.destinationLocationName = "";
                    }
                    startActivity(i);
                    finish();

                } else if (checkedId == R.id.radioButton) {
                    GPSTracker gps = new GPSTracker(alertdialogpopup.this);

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
                        Toast.makeText(alertdialogpopup.this, "location entered sucessfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(alertdialogpopup.this, "Couldn't get Location please open internet connection", Toast.LENGTH_SHORT).show();

                    }
                    finish();
                }
                if (checkedId == R.id.radioButton3) {
                    Intent i = new Intent(alertdialogpopup.this, StationsListActivity.class);
                    i.putExtra("from", from);
                    startActivity(i);
                    finish();

                }
            }
        });


    }
}
