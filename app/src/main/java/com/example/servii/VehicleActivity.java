package com.example.servii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class VehicleActivity extends AppCompatActivity {

    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Button cont=findViewById(R.id.vehicle_continue);
        final ImageButton twoWheeler=findViewById(R.id.vehicle_2whiller);
        final ImageButton fourWheeler=findViewById(R.id.vehicle_4whiller);
        twoWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                twoWheeler.setBackgroundResource(R.drawable.transperant_orange);
                flag=0;
                if(flag==0)
                {
                   fourWheeler.setBackgroundResource(R.drawable.box_orange);
                }
            }
        });
        fourWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fourWheeler.setBackgroundResource(R.drawable.transperant_orange);
                flag=1;
                if(flag==1)
                {
                    twoWheeler.setBackgroundResource(R.drawable.box_orange);
                }
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VehicleActivity.this, VehicleDetailsActivity.class));
            }
        });

    }
}
