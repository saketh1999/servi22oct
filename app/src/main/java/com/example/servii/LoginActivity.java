package com.example.servii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    private EditText phone;
    private android.widget.EditText otp;
    private TextView signup;
    private TextView tv1;
    private TextView tv2;
    private Button cont;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private ProgressBar progressBar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private int flag=0, flag2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.login_progress);

        phone=(EditText) findViewById(R.id.login_phone);
        otp=(android.widget.EditText) findViewById(R.id.login_otp);
        cont=(Button)findViewById(R.id.login_continue);
        signup=(TextView)findViewById(R.id.login_ne4);
        tv1=(TextView)findViewById(R.id.login_ne5);
        tv2=(TextView)findViewById(R.id.login_ne6);
        mAuth = FirebaseAuth.getInstance();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(flag==0) {
                    String mob = phone.getText().toString().trim();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mob,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            LoginActivity.this,          // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }
                else {

                    String Otp = otp.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, Otp);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();


                                        if (task.isSuccessful()) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            FirebaseUser user = task.getResult().getUser();
                                            updateUI(user);
                                            if(isNew==true)
                                            {
                                                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(LoginActivity.this, "You haven't registered. Do registration first",
                                                                Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(LoginActivity.this, "Authentication successful",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            }
                                        } else {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }
                                    }


                            });
                }
                    }



        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                boolean isNew=task.getResult().getAdditionalUserInfo().isNewUser();

                                    if (task.isSuccessful()) {

                                        FirebaseUser user = task.getResult().getUser();
                                        updateUI(user);
                                        if(isNew==true)
                                        {
                                            Toast.makeText(LoginActivity.this, "You haven't registered. Do registration first",
                                                    Toast.LENGTH_SHORT).show();
                                           mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                               }
                                           });

                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "Authentication successful",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        }

                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }


                        });
                }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                String message= e.toString();
                Toast.makeText(LoginActivity.this, "Phone login failed"+ message,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(LoginActivity.this, "Code sent to your phone",Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
                mResendToken = token;
                flag=1;
                phone.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                signup.setText("OTP VERIFICATION");
                tv1.setText("Enter OTP to proceed");
                tv2.setText("Enter OTP");
                cont.setText("Submit");

                // ...
            }
        };


    }

    private void updateUI(FirebaseUser currentUser)
    {
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }
}
