package com.example.servii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        UserValidate();
    }

    public void onBackPressed()
    {
        final AlertDialog.Builder builder= new AlertDialog.Builder(HomeActivity.this);
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
    private void UserValidate()
    {
        FirebaseDatabase mbase=FirebaseDatabase.getInstance();
        DatabaseReference mRef=mbase.getReference("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child(mAuth.getUid()).exists())
                {
                    Toast.makeText(HomeActivity.this, "We haven't got your mobile number. Please enter mobile number first",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, VerifyActivity.class));
                }
                else if(!dataSnapshot.child(mAuth.getUid()).child("Profile").exists())
                {
                    Toast.makeText(HomeActivity.this, "We haven't got your name. Please enter your name first",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                }
                else
                {
                    startActivity(new Intent(HomeActivity.this, MainHomeActivity.class));
                    Toast.makeText(HomeActivity.this, "Welcome to home activity",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
