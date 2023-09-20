package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VEmail extends AppCompatActivity {

    Button verified_btn;
    TextView verify_email_tv;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vemail);

        verified_btn = findViewById(R.id.verified_btn);
        verify_email_tv = findViewById(R.id.verify_email_tv);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();



        verified_btn.setOnClickListener(v->{


            ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while checking...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();



            user.reload();


            if(user.isEmailVerified()){

                progress.show();
                startActivity(new Intent(getApplicationContext(),LaunchPad.class));
            }
            else{
                user.sendEmailVerification();
                progress.show();
                Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progress.dismiss();
                }
            }, 2000);





        });




    }
}