package ati.com.busstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import utils.HelperCLass;

public class alretdialogpopup2 extends AppCompatActivity {
    RadioGroup rg1;
    String from1;
    ImageView language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alretdialogpopup2);
        language=findViewById(R.id.languagebutton);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(alretdialogpopup2.this);
                builder.setTitle("Change language");
                builder.setMessage("do you want to change language");
                builder.setCancelable(false);

                AlertDialog.Builder builder1 = builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(getApplicationContext(), "yes button clicked", Toast.LENGTH_LONG).show();
                        Intent r = new Intent(alretdialogpopup2.this, alertdialogue_arabic_destination.class);
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
                    Intent i = new Intent(alretdialogpopup2.this, locationActivity.class);
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
                    Intent i = new Intent(alretdialogpopup2.this, StationsListActivity.class);
                    i.putExtra("from", from1);
                    startActivity(i);
                    finish();

                }
            }
        });


    }
}

