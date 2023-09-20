package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ecom.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;

public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String spinnerValue = "Select Category";
    EditText product_name,product_price;
    Button add_product_btn;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        spinner = findViewById(R.id.categorySelectSpinner);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        add_product_btn = findViewById(R.id.add_product_btn);

        spinner.setOnItemSelectedListener(this);
        String[] categoryName = getResources().getStringArray(R.array.category);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categoryName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pName = ""+ product_name.getText().toString();
                String pPrice = "" + product_price.getText().toString();
                String pType = spinnerValue;

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long time = timestamp.getTime();
                String pID = "CC" + time;


                if(pName.length()<1){
                    product_name.setError("Enter Product Name");
                }
                else if(product_price.length()<1){
                    product_price.setError("Enter Product Price");
                }
                else{
                    ProductModel product = new ProductModel(pID,pName,pType,pPrice);

                    db.collection("products")
                            .document(pID)
                            .set(product);

                    startActivity(new Intent(getApplicationContext(),LaunchPad.class));
                    finish();
                }


            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.categorySelectSpinner){
            spinnerValue = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}