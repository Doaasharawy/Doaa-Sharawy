package ati.com.busstation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javaclasses.Bus;
import javaclasses.GPSDistanceTime;
import javaclasses.Station;
import path.DrawPath;
import path.JSONParser;
import utils.HelperCLass;

public class BusesScreen extends AppCompatActivity {
    private CardView currentlocation, destination;
TextView searchBtn;
    ListView busesListView;
    ArrayList<String> busesDataList;
    ArrayList<Bus> busesArrayList;
    ArrayList<Bus> tempBusesArrayList;
    ArrayAdapter<String> busesArrayAdapter;
    ArrayList<String> urls = new ArrayList<>();
    ArrayList<String> distances = new ArrayList<>();
    long time = 3000;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_screen);

        currentlocation = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        searchBtn = findViewById(R.id.searchBtn);

        busesListView = findViewById(R.id.busesListView);

        busesDataList = new ArrayList<>();

        busesArrayAdapter = new ArrayAdapter<String>(BusesScreen.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, busesDataList);
        busesListView.setAdapter(busesArrayAdapter);


        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusesScreen.this, alertdialogpopup.class);
                i.putExtra("from", "source");
                startActivity(i);

            }
        });

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(BusesScreen.this, locationActivity.class);
//                i.putExtra("from", "destination");
//                startActivity(i);
                Intent i = new Intent(BusesScreen.this, alretdialogpopup2.class);
                i.putExtra("from", "destination");
                startActivity(i);

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData(BusesScreen.this, "Bus", "Please wait", "Loading...");


            }
        });


    }


    public static void getData(final BusesScreen currentActivity, String childName1, String title, String msg) {
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
        busesArrayList = new ArrayList<>();
        busesDataList = new ArrayList<>();
        tempBusesArrayList = new ArrayList<>();
        urls = new ArrayList<>();
        Log.i("dataa", "updateUI: " + data.toString());
        String key = "", index = "";
        try {
            for (DataSnapshot currentChild : data.getChildren()) {
                try {
                    Bus currentBus = currentChild.getValue(Bus.class);
                    key = currentChild.getKey();

                    ArrayList<LatLng> stations = new ArrayList<>();
                    for (int i = 0; i < currentBus.getStations().size(); i++) {
                        Log.i("station", "station: " + currentBus.getStations().get(i).getName());
                        stations.add(new LatLng(Double.parseDouble(currentBus.getStations().get(i).getLatitude()), Double.parseDouble(currentBus.getStations().get(i).getLongitude())));
                        // source
                        urls.add(DrawPath.makeURLWalking(HelperCLass.sourceLocation.latitude,
                                HelperCLass.sourceLocation.longitude, Double.parseDouble(currentBus.getStations().get(i).getLongitude()),
                                Double.parseDouble(currentBus.getStations().get(i).getLatitude())));
                        // destination

                        urls.add(DrawPath.makeURLWalking(HelperCLass.destinationLocation.latitude,
                                HelperCLass.destinationLocation.longitude, Double.parseDouble(currentBus.getStations().get(i).getLongitude()),
                                Double.parseDouble(currentBus.getStations().get(i).getLatitude())));
                        index = i + "";
                    }
                    tempBusesArrayList.add(currentBus);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("dataaaa", "key: " + key);
                    Log.i("dataaaa", "index: " + index);
                }
            }

//            new GetDistanceTimeAsyncTask(urls).execute();
            if (HelperCLass.sourceLocation.longitude != 0
                    && HelperCLass.destinationLocation.longitude != 0) {
                try {

                    for (int i = 0; i < tempBusesArrayList.size(); i++) {

                        int nearestSourceLocation = DrawPath.getSmallestDistanceIndex(tempBusesArrayList.get(i).getStations(), "source");
                        int nearestDestinationLocation = DrawPath.getSmallestDistanceIndex(tempBusesArrayList.get(i).getStations(), "destination");

                        Log.i("nearest metro", "updateUI: " + tempBusesArrayList.get(i).getName());
                        Log.i("nearest metro", "updateUI index: " + i);
                        Log.i("nearest metro", "updateUI latitude: " + tempBusesArrayList.get(i).getStations().get(nearestSourceLocation).getLatitude());
                        Log.i("nearest metro", "updateUI longitude: " + tempBusesArrayList.get(i).getStations().get(nearestSourceLocation).getLongitude());
                        LatLng nearestSourcePoint = new LatLng(Double.parseDouble(tempBusesArrayList.get(i).getStations().get(nearestSourceLocation).getLongitude()), Double.parseDouble(tempBusesArrayList.get(i).getStations().get(nearestSourceLocation).getLatitude()));
                        LatLng nearestDestinationPoint = new LatLng(Double.parseDouble(tempBusesArrayList.get(i).getStations().get(nearestDestinationLocation).getLongitude()), Double.parseDouble(tempBusesArrayList.get(i).getStations().get(nearestDestinationLocation).getLatitude()));
                        if (DrawPath.CalculationByDistance(HelperCLass.sourceLocation, nearestSourcePoint) < 5 &&
                                DrawPath.CalculationByDistance(HelperCLass.destinationLocation, nearestDestinationPoint) < 5) {
                            busesDataList.add(tempBusesArrayList.get(i).getName());
                            busesArrayList.add(tempBusesArrayList.get(i));
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!HelperCLass.sourceLocationName.equalsIgnoreCase("")) {
                for (int i = 0; i < tempBusesArrayList.size(); i++) {
                    boolean sourceFound = false, destinationFound = false;
                    for (int j = 0; j < tempBusesArrayList.get(i).getStations().size(); j++) {
                        Station currentStation = tempBusesArrayList.get(i).getStations().get(j);

                        if (currentStation.getName().equalsIgnoreCase(HelperCLass.sourceLocationName)) {
                            sourceFound = true;
                        } else if (currentStation.getName().equalsIgnoreCase(HelperCLass.destinationLocationName)) {
                            destinationFound = true;
                        }
                        if (sourceFound && destinationFound) {
                            busesDataList.add(tempBusesArrayList.get(i).getName());
                            busesArrayList.add(tempBusesArrayList.get(i));
                            break;
                        }
                    }
                }

            }

            busesArrayAdapter = new ArrayAdapter<String>(BusesScreen.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, busesDataList);
            busesListView.setAdapter(busesArrayAdapter);
//            busesArrayAdapter.notifyDataSetChanged();
            busesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        Intent intent = new Intent(BusesScreen.this, locationActivity.class);
//                        intent.putExtra("indexx", i + "");
                        intent.putExtra("from", "");
                        HelperCLass.selectedBus = busesArrayList.get(i);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


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

    public class GetDistanceTimeAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        ArrayList<String> urls;
        ArrayList<String> jsons;

        // GoogleMap currentMap;
        public GetDistanceTimeAsyncTask(ArrayList<String> urlPass) {
            urls = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            jsons = new ArrayList<String>();
            progressDialog = new ProgressDialog(BusesScreen.this);
            progressDialog
                    .setMessage("Calculating Distance and Duration, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();

            for (int i = 0; i < urls.size(); i++) {
                String jsonFromUrl = jParser.getJSONFromUrl(urls.get(i));
                Log.i("json parser dataaaa" + i, "doInBackground: " + jsonFromUrl);
                jsons.add(jsonFromUrl);

            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            distances = new ArrayList<>();
            try {
                for (int i = 0; i < jsons.size(); i++) {
                    GPSDistanceTime gps = new DrawPath()
                            .findDistanceAndDuration(jsons.get(i));
                    try {
                        String mDistance = gps.getDistance();
                        String mTime = gps.getTime();
                        distances.add(mDistance);

                    } catch (Exception e) {
                        distances.add("10000000");

                    }

                }
// distances
//                for (int i = 0; i < distances.size(); i++) {
                int i = 0;
                for (int j = 0; j < tempBusesArrayList.size(); j++) {
                    for (int k = 0; k < tempBusesArrayList.get(j).getStations().size(); k++) {
                        for (int l = 0; l < 2; l++) {
                            if (l % 2 == 0) {
                                tempBusesArrayList.get(j).getStations().get(k).setSourceDistance(Double.parseDouble(distances.get(i).substring(0, distances.get(i).length() - 3)));
                                Log.i("source distance" + i, "onPostExecute: " + distances.get(i).substring(0, distances.get(i).length() - 3));
                            } else {
                                tempBusesArrayList.get(j).getStations().get(k).setDestinationDestance(Double.parseDouble(distances.get(i).substring(0, distances.get(i).length() - 3)));
                                Log.i("destination distance" + i, "onPostExecute: " + distances.get(i).substring(0, distances.get(i).length() - 3));
                            }
                            i++;

                        }
                    }
                }

                for (int j = 0; j < tempBusesArrayList.size(); j++) {
                    for (int k = 0; k < tempBusesArrayList.get(j).getStations().size(); k++) {
                        Log.i("dataaaaaaaaaaaaaaa", "distance source: " + tempBusesArrayList.get(j).getStations().get(k).getSourceDistance());
                        Log.i("dataaaaaaaaaaaaaaa", "distance destnation: " + tempBusesArrayList.get(j).getStations().get(k).getDestinationDestance());
                    }

                }

                for (i = 0; i < tempBusesArrayList.size(); i++) {


                    int nearestSourceIndex = DrawPath.getSmallestDistanceIndex(tempBusesArrayList.get(i).getStations(), "source");
                    int nearestDestinationIndex = DrawPath.getSmallestDistanceIndex(tempBusesArrayList.get(i).getStations(), "destination");

                    try {
                        if (tempBusesArrayList.get(i).getStations().get(nearestSourceIndex).getSourceDistance() < 10
                                && tempBusesArrayList.get(i).getStations().get(nearestDestinationIndex).getDestinationDestance() < 10) {
                            busesArrayList.add(tempBusesArrayList.get(i));
                            busesDataList.add(tempBusesArrayList.get(i).getName());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                busesArrayAdapter = new ArrayAdapter<String>(BusesScreen.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, busesDataList);
                busesListView.setAdapter(busesArrayAdapter);
//            busesArrayAdapter.notifyDataSetChanged();
                busesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            Intent intent = new Intent(BusesScreen.this, locationActivity.class);
//                        intent.putExtra("indexx", i + "");
                            HelperCLass.selectedBus = busesArrayList.get(i);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.hide();

        }
    }

}
