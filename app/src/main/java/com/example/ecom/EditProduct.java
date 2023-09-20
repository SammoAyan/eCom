package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditProduct extends AppCompatActivity {

    EditText ep_name,ep_price;
    Button update_product_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        String id = getIntent().getStringExtra("id");

        ep_name = findViewById(R.id.ep_name);
        ep_price = findViewById(R.id.ep_price);
        update_product_btn = findViewById(R.id.update_product_btn);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("products").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                ep_name.setText(""+value.getString("pName"));
                ep_price.setText(""+value.getString("pPrice"));

            }
        });

        update_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ep_name.getText().toString();
                String price = ep_price.getText().toString();


                db.collection("products")
                        .document(id)
                        .update(
                                "pName",name,
                                "pPrice",price
                        );

                Toast.makeText(getApplicationContext(), "Item Updated Successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                finish();

            }
        });


    }
}