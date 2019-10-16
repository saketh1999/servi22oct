package com.example.servii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.bachors.prefixinput.EditText;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

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
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        phone=(EditText) findViewById(R.id.verify_phone);
        otp=(android.widget.EditText) findViewById(R.id.verify_otp);
        cont=(Button)findViewById(R.id.verify_continue);
        signup=(TextView)findViewById(R.id.verify_ne4);
        tv1=(TextView)findViewById(R.id.verify_ne5);
        tv2=(TextView)findViewById(R.id.verify_ne6);
        mAuth = FirebaseAuth.getInstance();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {
                    String mob = phone.getText().toString().trim();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mob,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            VerifyActivity.this,          // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }
                else{

                    String Otp=otp.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, Otp);

                    mAuth.getCurrentUser().linkWithCredential(credential)
                            .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(VerifyActivity.this, "Authentication successful",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = task.getResult().getUser();
                                        updateUI(user);
                                        startActivity(new Intent(VerifyActivity.this, ProfileActivity.class));
                                    }
                                    else {
                                        Toast.makeText(VerifyActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });

                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(VerifyActivity.this, "Phone login successful",Toast.LENGTH_SHORT).show();
                mAuth.getCurrentUser().linkWithCredential(credential)
                        .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(VerifyActivity.this, "Authentication successful",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = task.getResult().getUser();
                                    updateUI(user);
                                    startActivity(new Intent(VerifyActivity.this, ProfileActivity.class));
                                }
                                else {
                                    Toast.makeText(VerifyActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                String message= e.toString();
                Toast.makeText(VerifyActivity.this, "Phone login failed"+ message,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(VerifyActivity.this, "Code sent to your phone",Toast.LENGTH_SHORT).show();
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
