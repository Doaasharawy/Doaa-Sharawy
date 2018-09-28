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

public class Third_Line_Fragment extends AppCompatActivity {
    ListView mlistview;

    String[]name={"Al Ahram","Koleyt El Banat","Cairo Stadium","Ard El Maared","Abbassiya","Abdou Basha",
            "El Geish","Bab El Shaaria"
            ,"Ataba"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_line_fragment);
        mlistview=findViewById(R.id.thirdline);
      CustomAdapter adapter= new CustomAdapter();
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
            View view=getLayoutInflater().inflate(R.layout.thirdline_item,null);
            TextView textView=view.findViewById(R.id.textView3);
            textView.setText(name[position]);

            return view;
        }
    }}