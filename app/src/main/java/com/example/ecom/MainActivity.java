package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button log1,reg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log1 = findViewById(R.id.log1);
        reg1 = findViewById(R.id.reg1);


        log1.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
        });

        reg1.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),RegisterPage.class));
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            if(user.isEmailVerified()){
                startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                finish();
            }
            else{
                startActivity(new Intent(getApplicationContext(),VEmail.class));
            }


        }
    }
}