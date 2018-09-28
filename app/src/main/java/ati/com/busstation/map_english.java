package ati.com.busstation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Shaarawy on 5/20/2018.
 */

public class map_english extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_english);
        img=findViewById(R.id.language);
        PhotoViewAttacher photoview=new PhotoViewAttacher(img);
        photoview.update();
    }

}


