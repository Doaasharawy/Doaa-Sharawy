package ati.com.busstation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Shaarawy on 5/13/2018.
 */

public class Second_line_fragment extends AppCompatActivity{
    ListView mlistview;

    String[]name={"Shubra","Koleyt El Zera3a","El Mazallat","El Khalafawi","Sant.Teresa","Rod El farag","Massara","El Shohadaa"
            ,"Ataba","M.Naguib","Saddat","Opera","Dokki","Elbhoos","Cairo University"
            ,"Faysal","Giza","Omm El Misryeen","Sakiat Mekki","El Monib"

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_line_fragment);
        mlistview=findViewById(R.id.secondline);
        CustomAdapter adapter=new CustomAdapter();
        mlistview.setAdapter(adapter);

    }
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return name.length;
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
            View view=getLayoutInflater().inflate(R.layout.secondline_item,null);
            TextView textView=view.findViewById(R.id.textView2);
            textView.setText(name[position]);

            return view;
        }
    }}