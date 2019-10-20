package com.example.servii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class ProfileActivity extends AppCompatActivity {
    EditText locality;
    EditText name;
    Spinner spinner;
    Button cont;
    String Name,Location;
    int flag;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        spinner=(Spinner) findViewById(R.id.profile_locality);
        Location=spinner.toString();
        cont=(Button) findViewById(R.id.profile_continue);
        name=(EditText)findViewById(R.id.profile_name);
        mAuth=FirebaseAuth.getInstance();
        final List<String> localities = new ArrayList<String>();
        localities.add("Select Cities");
        localities.add("Mumbai");
        localities.add("Hyderabad");
        localities.add("Chennai");
        localities.add("Delhi");

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Name=name.getText().toString();
                if(Name.isEmpty()||flag==0)
                {
                    cont.setClickable(false);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    cont.setClickable(true);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.orange));
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Name=name.getText().toString();
                if(Name.isEmpty()||flag==0)
                    {
                        cont.setClickable(false);
                        cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.colorAccent));
                    }
                    else
                    {
                        cont.setClickable(true);
                        cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.orange));
                    }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Name=name.getText().toString();
                if(Name.isEmpty()||flag==0)
                {
                    cont.setClickable(false);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    cont.setClickable(true);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.orange));
                }
            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, localities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                flag=i;
                Name=name.getText().toString();
                if(Name.isEmpty()||flag==0)
                {
                    cont.setClickable(false);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    cont.setClickable(true);
                    cont.setBackgroundColor(cont.getContext().getResources().getColor(R.color.orange));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                FirebaseDatabase mbase=FirebaseDatabase.getInstance();
                DatabaseReference mRef=mbase.getReference("Users");
                profile p=new profile(Name, flag);
                mRef.child(mAuth.getUid()).child("Profile").setValue(p);
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
            }
        });

    }
    public void onBackPressed()
    {
        final AlertDialog.Builder builder= new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage("Do you want to exit app?");
        builder.setCancelable(true);
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a=new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(a);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}

