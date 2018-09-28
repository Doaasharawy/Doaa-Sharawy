package ati.com.busstation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Shaarawy on 4/11/2018.
 */

public class nearbyplaces extends AppCompatActivity {
    ListView mlistview;
    int []imgs={R.drawable.restaurant,
            R.drawable.market,
            R.drawable.hospital,
            R.drawable.mosque,
            R.drawable.church,
            R.drawable.cafes,
            R.drawable.pharmacy
            ,R.drawable.atm,
            R.drawable.busstation,
            R.drawable.gasstation
    };
    String[]name={"Restaurant","Market","Hospital","Mosque","Church","Cafe","Pharmacy","ATM","Bus Station","Gas Station"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_places);
        mlistview=findViewById(R.id.listview);
        CustomAdapter customAdapter=new CustomAdapter();
        mlistview.setAdapter(customAdapter);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                switch (position){
                    case 0 :
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurant");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;
                    case 1 :
                        Uri gmmIntenUrit = Uri.parse("geo:0,0?q=market");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;
                    case 2 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=hospital");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;
                    case 3 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=mosque");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;
                    case 4 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=church");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;
                    case 5 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=cafe");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                        break;

                    case 6 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=pharmacy");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case 7 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=atm");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case 8 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=bus station");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case 9 :
                        gmmIntenUrit = Uri.parse("geo:0,0?q=gas station");
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUrit);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                }

            }

        });
    }
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.nearby_item,null);
            ImageView imageView=view.findViewById(R.id.imageView);
            TextView textView=view.findViewById(R.id.textview);
            imageView.setImageResource(imgs[position]);
            textView.setText(name[position]);

            return view;
        }
    }
}