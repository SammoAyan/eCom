package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom.adapters.ProductAdapter;
import com.example.ecom.models.ProductModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryView extends AppCompatActivity {

    RecyclerView catProductRecyclerView;

    TextView cat_name;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> list;

    FirebaseFirestore db;
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        cat_name = findViewById(R.id.cat_name);

        name = getIntent().getStringExtra("category");

        cat_name.setText(name);



        catProductRecyclerView = findViewById(R.id.catProductRecyclerView);


        catProductRecyclerView.setHasFixedSize(true);
        catProductRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<ProductModel>();

        productAdapter = new ProductAdapter(getApplicationContext(), list);

        catProductRecyclerView.setAdapter(productAdapter);

        getData();

    }

    private void getData() {

        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                ProductModel productModel = dc.getDocument().toObject(ProductModel.class);

                                if(productModel.getpType().equals(name)){
                                    list.add(productModel);
                                }

                            }

                            productAdapter.notifyDataSetChanged();

                        }

                    }
                });

    }
}