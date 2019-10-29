package com.example.servii;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VehicleActivity extends AppCompatActivity {
   Spinner Vspinner;
   public static final String EXTRA_COLOR="com.example.servi.EXTRA_COLOR";
    public static final String EXTRA_TYPE="com.example.servi.EXTRA_TYPE";

    private int flag1=2,flag;
    private TextView try2;
    private String VehicleType="";
    String colr;

    private Button Vcont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Vspinner=findViewById(R.id.vehicle_spinner);
        Vcont=findViewById(R.id.vehicle_continue);
        final List<String> vColor = new ArrayList<String>();

        try2=findViewById(R.id.vehi_type);

        vColor.add("Select Color");
        vColor.add("RED");
        vColor.add("BLACK");
        vColor.add("WHITE");
        vColor.add("MAROON");




        final ImageButton twoWheeler=findViewById(R.id.vehicle_2wheeler);
        final ImageButton fourWheeler=findViewById(R.id.vehicle_4wheeler);
        twoWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VehicleType="Two";

                try2.setText(VehicleType);

                twoWheeler.setBackgroundResource(R.drawable.transperant_orange);
                flag1=0;
                if(flag1==0)
                {
                   fourWheeler.setBackgroundResource(R.drawable.box_orange);
                }
            }
        });
        fourWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try2.setText(VehicleType);
                VehicleType="Four";
                fourWheeler.setBackgroundResource(R.drawable.transperant_orange);
                flag1=1;
                if(flag1==1)
                {
                    twoWheeler.setBackgroundResource(R.drawable.box_orange);
                }
            }
        });


        ArrayAdapter<String> VarrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vColor);
        VarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Vspinner.setAdapter(VarrayAdapter);
        Vspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                flag=i;

                if(VehicleType.isEmpty()||flag1==2||flag==0)
                {

                    Vcont.setClickable(false);
                    Vcont.setBackgroundColor(Vcont.getContext().getResources().getColor(R.color.colorAccent));


                    try2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(flag!=0)
                            {
                                Vcont.setClickable(true);
                                Vcont.setBackgroundColor(Vcont.getContext().getResources().getColor(R.color.orange));

                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }

                else
                {
                    Vcont.setClickable(true);
                    Vcont.setBackgroundColor(Vcont.getContext().getResources().getColor(R.color.orange));

                }
                 colr=Vspinner.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {








            }
        });

        Vcont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent=new Intent(VehicleActivity.this,VehicleDetailsActivity.class);
               intent.putExtra(EXTRA_COLOR,colr);
               intent.putExtra(EXTRA_TYPE,VehicleType);
               startActivity(intent);
            }
        });

    }

}
