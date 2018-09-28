package ati.com.busstation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity
      implements View.OnClickListener {
    CardView busbtn,metrobtn,share,more,about,contactus,aroundme,streetview;;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busbtn=findViewById(R.id.busstation);
        metrobtn=findViewById(R.id.metrostation);
        share =findViewById(R.id.share);
        more =findViewById(R.id.moreapp);
        about =findViewById(R.id.aboutus);
        streetview =findViewById(R.id.streetview);
        contactus =findViewById(R.id.contactus);
        myDialog = new Dialog(this);

        aroundme =findViewById(R.id.nearby);
        busbtn.setOnClickListener(this);
        metrobtn.setOnClickListener(this);
        share.setOnClickListener(this);
        more.setOnClickListener(this);
        about.setOnClickListener(this);
        contactus.setOnClickListener(this);
        aroundme.setOnClickListener(this);
        streetview.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.busstation :
                Intent i = new Intent(MainActivity.this, BusesScreen.class);
                startActivity(i);
                break;

            case R.id.metrostation :
                Intent a = new Intent(MainActivity.this, MetroActivity.class);
                startActivity(a);
                break;
            case R.id.share :
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sharebody="Bus.station2018@gmail.com";
                intent.putExtra(intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(intent,"share Via"));

                break;
            case R.id.moreapp :
                Intent browseinternet=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));
                startActivity(browseinternet);
                break;
            case R.id.contactus :
           Intent n=new Intent(MainActivity.this,contactUsActivity.class);
           startActivity(n);

                break;
            case R.id.aboutus :
                Intent h= new Intent(MainActivity.this, pop.class);
                startActivity(h);
                break;
            case R.id.streetview :
                Intent d= new Intent(MainActivity.this, streetview.class);
                startActivity(d);
                break;
            case R.id.nearby :
                Intent z = new Intent(MainActivity.this, nearbyplaces.class);
                startActivity(z);
                break;
        }
    }



}