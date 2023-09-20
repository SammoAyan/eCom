package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OrderDetails extends AppCompatActivity {

    TextView order_id,pro_name,order_price,order_status,buyer_name,buyer_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        order_id = findViewById(R.id.order_id);
        pro_name = findViewById(R.id.pro_name);
        order_price = findViewById(R.id.order_price);
        order_status = findViewById(R.id.order_status);
        buyer_name = findViewById(R.id.buyer_name);
        buyer_add = findViewById(R.id.buyer_add);





        String id = getIntent().getStringExtra("id");


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("orders")
                .document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                order_id.setText(""+value.getString("oID"));
                pro_name.setText(""+value.getString("pName"));
                order_price.setText(""+value.getString("pPrice")+"৳");
                String uid = ""+value.getString("buyerUID");


                DocumentReference documentReference = db.collection("users").document(uid);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        buyer_name.setText(""+value.getString("name"));
                        buyer_add.setText(""+value.getString("address"));

                    }
                });



            }
        })
        ;
    }
}