package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText login_email,login_pass;
    Button login_btn,reg_now_btn;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);
        login_btn = findViewById(R.id.login_btn);
        reg_now_btn = findViewById(R.id.reg_now_btn);

        login_btn.setOnClickListener(v->{

            String email = login_email.getText().toString();
            String pass = login_pass.getText().toString();


           if(email.length()<5){
               login_email.setError("Enter Valid Email");
           }
            else if(pass.length()<6){
                login_pass.setError("Minimum length is 6");
            }
           else{
               mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       user = mAuth.getCurrentUser();
                       if(task.isSuccessful() ){
                           if(user.isEmailVerified()){
                               startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                           }
                           else
                           {
                               startActivity(new Intent(getApplicationContext(),VEmail.class));
                           }

                       }

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(), "Login Failed \n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }




        });

        reg_now_btn.setOnClickListener(v->{

            startActivity(new Intent(getApplicationContext(),RegisterPage.class));
            finish();
        });


    }
}