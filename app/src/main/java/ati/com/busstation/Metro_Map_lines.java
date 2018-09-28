package ati.com.busstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Shaarawy on 4/29/2018.
 */

public class Metro_Map_lines extends AppCompatActivity {

    ImageView img;
    ImageView language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metromap);
        img=findViewById(R.id.language);
        PhotoViewAttacher photoview=new PhotoViewAttacher(img);
        photoview.update();

         language=findViewById(R.id.languagebutton);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Metro_Map_lines.this);
                builder.setTitle("Change language");
                builder.setMessage("do you want to change map language");
                builder.setCancelable(false);

                AlertDialog.Builder builder1 = builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(getApplicationContext(), "yes button clicked", Toast.LENGTH_LONG).show();
                        Intent r = new Intent(Metro_Map_lines.this, map_english.class);
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
        }
}