package ati.com.busstation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.alorma.timeline.RoundTimelineView;
import com.alorma.timeline.TimelineView;

/**
 * Created by Shaarawy on 4/25/2018.
 */

public class First_Line_Fragment extends AppCompatActivity {
    ListView mlistview;

    String[]name={"New Marg","El Marg","Azbet El Nakheel","Ain Shames","El Matrya","Helmyt El Zayton","Hadayaa El Zayton","Saraya El Koba"
            ,"Hammat El Koba","Kobry El Koba","Manshiet El Sadr","El Demerdash","Ghamra","El shohadaa","Ahmed Orabi"
            ,"Naser","Saddat","Saad Zaghlol","El sayeda Zeinab","El Malk El saleh"
            ,"Mari Gargis","El Zahraa","Dar El salam","Haddayk El Maadi"
            ,"El Maadi","Thakanat El Maadi","Tora EL balad","Kozzika","Tora Al-Asmant","El maasara","Hadayaa Helwan"
            ,"Wadi Hoof","Helwan University","Ain Shams","Helwan"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_line_fragment);
        mlistview=findViewById(R.id.firstline);
        CustomAdapter ads=new CustomAdapter();
        mlistview.setAdapter(ads);

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
            View view=getLayoutInflater().inflate(R.layout.firstline_item,null);
            TextView textView=view.findViewById(R.id.textView);
            textView.setText(name[position]);

            return view;
        }
    }


   /* @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.first_line_fragment,container,false);

        ListView lv= (ListView) rootView.findViewById(R.id.firstline);
        CustomAdapter adapter=new CustomAdapter(this.getActivity(),getMetroLines());
        lv.setAdapter(adapter);

        return rootView;

    }

    private ArrayList<Metrolines> getMetroLines() {
        //COLECTION OF CRIME MOVIES
        ArrayList<Metrolines> names=new ArrayList<>();

        //SINGLE MOVIE
        Metrolines metro=new Metrolines("New Marg");
            names.add(metro);

        metro=new Metrolines("El Marg");
        names.add(metro);

        metro=new Metrolines("Azbet El Nakheel");
        names.add(metro);

        metro=new Metrolines("Ain Shames");
        names.add(metro);

        metro=new Metrolines("El Matrya");
        names.add(metro);
        metro=new Metrolines("Helmyt El Zayton");
        names.add(metro);

        metro=new Metrolines("Hadayaa El Zayton");
        names.add(metro);

        metro=new Metrolines("Saraya El Koba");
        names.add(metro);

        metro=new Metrolines("Hammat El Koba");
        names.add(metro);
        metro=new Metrolines("Kobry El Koba");
        names.add(metro);

        metro=new Metrolines("Manshiet El Sadr");
        names.add(metro);

        metro=new Metrolines("El Demerdash");
        names.add(metro);

        metro=new Metrolines("Ghamra");
        names.add(metro);
        metro=new Metrolines("El shohadaa");
        names.add(metro);

        metro=new Metrolines("Ahmed Orabi");
        names.add(metro);

        metro=new Metrolines("Naser");
        names.add(metro);

        metro=new Metrolines("Saddat");
        names.add(metro);
        metro=new Metrolines("Saad Zaghlol");
        names.add(metro);
        metro=new Metrolines("Elsayeda Zeinb");
        names.add(metro);
        metro=new Metrolines("El Malk El Saleh");
        names.add(metro);
        metro=new Metrolines("Mari Gargis");
        names.add(metro);
        metro=new Metrolines("Elzahraa");
        names.add(metro);
        metro=new Metrolines("Dar El salam");
        names.add(metro);
        metro=new Metrolines("Haddayak El Maadi");
        names.add(metro);
        metro=new Metrolines("El Maadi");
        names.add(metro);
        metro=new Metrolines("Thakanat El Maadi");
        names.add(metro);
        metro=new Metrolines("Tora El Balad");
        names.add(metro);
        metro=new Metrolines("Kozzika");
        names.add(metro);
        metro=new Metrolines("Tora Al-Asmant");
        names.add(metro);
        metro=new Metrolines("El Maasara");
        names.add(metro);
        metro=new Metrolines("Hadayaa Helwan");
        names.add(metro);
        metro=new Metrolines("Wadi Hoof");
        names.add(metro);
        metro=new Metrolines("Helwan University");
        names.add(metro);
        metro=new Metrolines("Ain Helwan");
        names.add(metro);
        metro=new Metrolines("Helwan");
        names.add(metro);
        return names;
    }

    @Override
    public String toString() {
        String title="First line";
        return title;
    }*/
}

