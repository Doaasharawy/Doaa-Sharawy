package ati.com.busstation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class metrolinesbutton extends AppCompatActivity {
    TextView firstline;
    TextView secondline;
    TextView thirdline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrolinesbutton);
        firstline=findViewById(R.id.firstline);
        secondline=findViewById(R.id.secondline);
        thirdline=findViewById(R.id.thirdline);
firstline.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(metrolinesbutton.this, First_Line_Fragment.class);
        startActivity(i);
    }
});
        secondline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(metrolinesbutton.this, Second_line_fragment.class);
                startActivity(i);
            }
        });
        thirdline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(metrolinesbutton.this, Third_Line_Fragment.class);
                startActivity(i);
            }
        });
    }
}
