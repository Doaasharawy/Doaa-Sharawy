package ati.com.busstation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javaclasses.Bus;
import utils.HelperCLass;

public class StationsListActivity extends AppCompatActivity {
    ListView searchBusesListView;
    ArrayList<String> stationsArrayList;
    Set<String> stationsStringSet;
    ArrayAdapter stationsArrayAdapter;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
setSupportActionBar(toolbar);

try {
            from = getIntent().getStringExtra("from");
        } catch (Exception e) {
            from = "source";
            e.printStackTrace();
        }
        searchBusesListView = findViewById(R.id.searchBusesListView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));


        getData(StationsListActivity.this, "Bus", "Please wait", "Loading...");


    }


    public void getData(final StationsListActivity currentActivity, String childName1, String title, String msg) {
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
        stationsArrayList = new ArrayList<>();
        stationsStringSet = new HashSet<>();
        HelperCLass.busArrayList.clear();
        Log.i("dataa", "updateUI: " + data.toString());
        String key = "", index = "";
        try {
            for (DataSnapshot currentChild : data.getChildren()) {
                try {
                    Bus currentBus = currentChild.getValue(Bus.class);
                    key = currentChild.getKey();

                    HelperCLass.busArrayList.add(currentBus);

                    ArrayList<LatLng> stations = new ArrayList<>();
                    for (int i = 0; i < currentBus.getStations().size(); i++) {
                        Log.i("station", "station: " + currentChild.getKey() + "   " + currentBus.getStations().get(i).getName());
                        stationsStringSet.add(currentBus.getStations().get(i).getName());
                        stations.add(new LatLng(Double.parseDouble(currentBus.getStations().get(i).getLatitude()), Double.parseDouble(currentBus.getStations().get(i).getLongitude())));
                        // source

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("dataaaa", "key: " + key);
                    Log.i("dataaaa", "index: " + index);
                }
            }

            try {
                stationsArrayList.addAll(stationsStringSet);

            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < stationsArrayList.size(); i++) {
                Log.e("Station", "updateUI: " + stationsArrayList.get(i));
            }

            try {


                stationsArrayAdapter = new ArrayAdapter<String>(StationsListActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, stationsArrayList);
                searchBusesListView.setAdapter(stationsArrayAdapter);
//            busesArrayAdapter.notifyDataSetChanged();
                searchBusesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            if (from.equalsIgnoreCase("source")) {
                                HelperCLass.sourceLocationName = stationsArrayList.get(i);
                                HelperCLass.sourceLocation = new LatLng(0, 0);
                            } else {
                                HelperCLass.destinationLocationName = stationsArrayList.get(i);
                                HelperCLass.destinationLocation = new LatLng(0, 0);
                            }
                            finish();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem searchitem=menu.findItem(R.id.item_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final ArrayList<String> templist=new ArrayList<>();
                for (String temp:stationsArrayList){
if (temp.toLowerCase().contains(newText.toLowerCase())){
    templist.add(temp);

}


                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(StationsListActivity.this,
                        android.R.layout.simple_list_item_1,templist);
                searchBusesListView.setAdapter(adapter);
                searchBusesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            if (from.equalsIgnoreCase("source")) {
                                HelperCLass.sourceLocationName = templist.get(i);
                                HelperCLass.sourceLocation = new LatLng(0, 0);
                            } else {
                                HelperCLass.destinationLocationName = templist.get(i);
                                HelperCLass.destinationLocation = new LatLng(0, 0);
                            }
                            finish();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            return true;
            }


        });



        return super.onCreateOptionsMenu(menu);
    }
}
