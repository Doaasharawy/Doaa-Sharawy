package ati.com.busstation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import utils.HelperCLass;

public class alertdialogue_arabic_destination extends AppCompatActivity {
    RadioGroup rg1;
    String from1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialogue_arabic_destination); try {
            from1 = getIntent().getStringExtra("from");
        } catch (Exception e) {
            from1 = "source";
            e.printStackTrace();
        }


        DisplayMetrics dm1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm1);
        int width = dm1.widthPixels;
        int height = dm1.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .3));
        rg1 = findViewById(R.id.radiogroup2);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.enter) {
                    Intent i = new Intent(alertdialogue_arabic_destination.this, locationActivity.class);
                    i.putExtra("from", from1);
                    if (from1.equalsIgnoreCase("source")) {
                        HelperCLass.sourceLocationName = "";
                    } else {
                        HelperCLass.destinationLocationName = "";
                    }
                    startActivity(i);
                    finish();

                }
                else if (checkedId == R.id.select) {
                    Intent i = new Intent(alertdialogue_arabic_destination.this, StationsListActivity.class);
                    i.putExtra("from", from1);
                    startActivity(i);
                    finish();

                }
            }
        });


    }
}

