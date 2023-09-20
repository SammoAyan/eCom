package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    RadioGroup radioGroup;

    EditText reg_name,reg_email,reg_pass,reg_mobile;
    Button reg_btn,log_now_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        radioGroup = findViewById(R.id.radioGroup);
        reg_pass = findViewById(R.id.reg_pass);
        reg_email = findViewById(R.id.reg_email);
        reg_name = findViewById(R.id.reg_name);
        reg_mobile = findViewById(R.id.reg_mobile);
        log_now_btn = findViewById(R.id.log_now_btn);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        reg_btn = findViewById(R.id.reg_btn);

        reg_btn.setOnClickListener(v->{

            String email = reg_email.getText().toString();
            String pass = reg_pass.getText().toString();
            String name = reg_name.getText().toString();
            String mobile = reg_mobile.getText().toString();



            int selecedRadio = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selecedRadio);
            String userStatus = radioButton.getText().toString();


            if (!name.matches(".*\\d.*")) {
                if(email.length()<5){
                    reg_email.setError("Enter Valid Email");
                }
                else if(name.length()<2){
                    reg_name.setError("Enter Valid Name");
                }
                else if(pass.length()<6){
                    reg_pass.setError("Enter password of Minimum length 6");
                }
                else if(mobile.length()!=11){
                    reg_mobile.setError("Enter Valid Mobile");
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),VEmail.class));

                                FirebaseUser user = mAuth.getCurrentUser();

                                UserModel userModel = new UserModel(user.getUid(),"",name,email,mobile,userStatus,"false","Not Provided Yet");

                                db = FirebaseFirestore.getInstance();


                                // Insert == Set , Delete , Update , Read ;


                                db.collection("users")
                                        .document(user.getUid())
                                        .set(userModel);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Registration Failed\n"+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Registration Failed !\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                } else {
                    //buzz
                Toast.makeText(getApplicationContext(), "Name can not contain special character or number and can not be empty", Toast.LENGTH_SHORT).show();


            }



        });


        log_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginPage.class));
                finish();
            }
        });





    }
}