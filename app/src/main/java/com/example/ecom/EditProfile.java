package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {

    EditText name,addr;
    Button update_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.name);
        addr = findViewById(R.id.addr);
        update_profile = findViewById(R.id.update_profile);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                name.setText(""+value.getString("name"));
                addr.setText(""+value.getString("address"));

            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Addr = addr.getText().toString();


                if (!Name.matches(".*\\d.*")) {
                    if(Name.length()<3){
                        name.setError("Name should be more than 3 character");
                    }
                    else  if(Addr.length()<3){
                        addr.setError("Address should be more than 3 character");
                    }
                    else{
                        db.collection("users")
                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .update(
                                        "name",Name,
                                        "address",Addr
                                );

                        Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Name can not contain special character or number", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }
}