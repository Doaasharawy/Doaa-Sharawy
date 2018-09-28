package ati.com.busstation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Shaarawy on 3/26/2018.
 */

public class pop extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width=dm.widthPixels;
            int height=dm.heightPixels;
            getWindow().setLayout((int) (width*.9),(int)(height*.5));
}}