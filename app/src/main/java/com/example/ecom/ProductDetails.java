package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom.models.OrderModel;
import com.example.ecom.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Timestamp;

public class ProductDetails extends AppCompatActivity {

    TextView p_name,p_type,p_price;
    Button p_edit,e_delete,e_order;
    String name = "",price="",cat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        String id = getIntent().getStringExtra("id");

        p_name = findViewById(R.id.p_name);
        p_type = findViewById(R.id.p_type);
        p_price = findViewById(R.id.p_price);
        p_edit = findViewById(R.id.p_edit);
        e_delete = findViewById(R.id.e_delete);
        e_order = findViewById(R.id.e_order);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = "";
                st += value.getString("userStatus");


                if(st.equals("Customer")){
                    e_delete.setVisibility(View.GONE);
                    p_edit.setVisibility(View.GONE);
                }
                else{
                    e_order.setVisibility(View.GONE);
                }

            }
        })
        ;

        DocumentReference documentReference = db.collection("products").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                p_name.setText(""+value.getString("pName"));
                p_type.setText(""+value.getString("pType"));
                p_price.setText(""+value.getString("pPrice"));

                name = ""+value.getString("pName");
                cat = ""+value.getString("pType");
                price = ""+value.getString("pPrice");

            }
        });


        e_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("products")
                        .document(id)
                        .delete();

                Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_SHORT).show();


                startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                finish();
            }
        });


        p_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),EditProduct.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });


        e_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long time = timestamp.getTime();
                String oID = "Order No :" + time;

                OrderModel order = new OrderModel(oID,name,cat,price,FirebaseAuth.getInstance().getUid());

                db.collection("orders")
                        .document(oID)
                        .set(order);


                Toast.makeText(getApplicationContext(), "Product Ordered", Toast.LENGTH_SHORT).show();

                e_order.setEnabled(false);


            }
        });





    }
}