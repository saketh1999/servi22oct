package com.example.servii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VehicleDetailsActivity extends AppCompatActivity {
    Spinner Bvehi,Mvehi;
    int flag;
String license,Vbrand,Vmodel;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        mAuth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        final String Vcolor=intent.getStringExtra(VehicleActivity.EXTRA_COLOR);
        final String Vtype=intent.getStringExtra(VehicleActivity.EXTRA_TYPE);

        TextView Vcolr=findViewById(R.id.vd_color);
        TextView Vtyp=findViewById(R.id.vd_type);
        final Button vdCont=findViewById(R.id.vehDetails_continue);

        Vcolr.setText(Vcolor);
        Vtyp.setText(Vtype);

      final TextView License=findViewById(R.id.vehDetails_licence);
      license=License.getText().toString();


        Bvehi=findViewById(R.id.vehDetails_brand);
        Mvehi=findViewById(R.id.vehDetails_model);
        final List<String> Brand2 = new ArrayList<String>();
        Brand2.add("SELECT BRAND");
        Brand2.add("HERO HONDA");
        Brand2.add("TVS");
        final List<String> Brand4 = new ArrayList<String>();
        Brand4.add("SELECT BRAND");
        Brand4.add("BMW");
        Brand4.add("MARUTHI-SUZUKI");

        final List<String> HH2 = new ArrayList<String>();
        HH2.add("Xpulse200");
        HH2.add("Xtreme 200s");

        final List<String> TVS2 = new ArrayList<String>();
        TVS2.add("Radeon");
        TVS2.add("Apache");

        final List<String> BMW4 = new ArrayList<String>();
        BMW4.add("BMW X1");
        BMW4.add("BMW 7 Sedan");

        final List<String> MS4 = new ArrayList<String>();
        MS4.add("IGNIS");
        MS4.add("Baleno");

        if(Vtype.equals("Two")){
        ArrayAdapter<String> VdarrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Brand2);
        VdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       Bvehi.setAdapter(VdarrayAdapter);

       Bvehi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               Vbrand=Bvehi.getItemAtPosition(i).toString();
               flag=i;
               if(flag==0)
               {
                   vdCont.setClickable(false);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.colorAccent));

               }
               else{


                   if(i==1){
                       ArrayAdapter<String> MdarrayAdapter=new ArrayAdapter<String>(VehicleDetailsActivity.this, android.R.layout.simple_spinner_item, HH2);
                       MdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       Mvehi.setAdapter(MdarrayAdapter);
                       Vmodel=Mvehi.getItemAtPosition(i).toString();


                   }
                   else if(i==2){
                       ArrayAdapter<String> MdarrayAdapter=new ArrayAdapter<String>(VehicleDetailsActivity.this, android.R.layout.simple_spinner_item, TVS2);
                       MdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       Mvehi.setAdapter(MdarrayAdapter);



                   }
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        }
       else  if(Vtype.equals("Four")){
            ArrayAdapter<String> VdarrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Brand4);
            VdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Bvehi.setAdapter(VdarrayAdapter);
            Bvehi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    flag=i;
                    if(flag==0)
                    {
                        vdCont.setClickable(false);
                        vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.colorAccent));

                    }
                    else{


                        if(i==1){
                            ArrayAdapter<String> MdarrayAdapter=new ArrayAdapter<String>(VehicleDetailsActivity.this, android.R.layout.simple_spinner_item, BMW4);
                            MdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Mvehi.setAdapter(MdarrayAdapter);



                        }
                        else if(i==2){
                            ArrayAdapter<String> MdarrayAdapter=new ArrayAdapter<String>(VehicleDetailsActivity.this, android.R.layout.simple_spinner_item,MS4 );
                            MdarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Mvehi.setAdapter(MdarrayAdapter);


                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


       License.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              license=License.getText().toString();
               if(license.isEmpty())
               {
                   vdCont.setClickable(false);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.colorAccent));
               }
               else {
                   vdCont.setClickable(true);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.orange));

               }
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               license=License.getText().toString();
               if(license.isEmpty())
               {
                   vdCont.setClickable(false);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.colorAccent));
               }
               else {
                   vdCont.setClickable(true);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.orange));

               }

           }

           @Override
           public void afterTextChanged(Editable editable) {
               license=License.getText().toString();
               if(license.isEmpty())
               {
                   vdCont.setClickable(false);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.colorAccent));
               }
               else {
                   vdCont.setClickable(true);
                   vdCont.setBackgroundColor(vdCont.getContext().getResources().getColor(R.color.orange));

               }


           }
       });





        Button cont= findViewById(R.id.vehDetails_continue);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase mbase=FirebaseDatabase.getInstance();
                DatabaseReference mRef=mbase.getReference("Users");
                Vehicle vehicle=new Vehicle(Vtype,Vcolor,Vbrand,Vmodel,license);
                mRef.child(mAuth.getUid()).child("Vehicle").setValue(vehicle);







                startActivity(new Intent(VehicleDetailsActivity.this, PlanActivity.class));
            }
        });

    }
}
