package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom.adapters.CategoryAdapter;
import com.example.ecom.adapters.ProductAdapter;
import com.example.ecom.adapters.SliderAdapter;
import com.example.ecom.models.CategoryModel;
import com.example.ecom.models.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    View view;
    RecyclerView recyclerView, productRecyclerView;
    EditText search_et;
    TextView welcome;
    LinearLayout b1,b2;

    ImageView add_product_btn;

    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> list;

    FirebaseFirestore db;

    int[] categoryLogo = {
            R.drawable.clothes_hanger,
            R.drawable.running_shoes,
            R.drawable.electronic_device,
            R.drawable.phone_case,
            R.drawable.playtime,
            R.drawable.house,
            R.drawable.food,
            R.drawable.book,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,

    };

    String[] categoryName = {

            "Clothing",
            "Shoes",
            "Electronics",
            "Accessories",
            "Kids",
            "Home",
            "Food",
            "Books",
            "E-Shop",
            "Health",
            "Sports",
            "Others"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);


        add_product_btn = view.findViewById(R.id.add_product_btn);

        recyclerView = view.findViewById(R.id.categoryRecyclerView);
        search_et = view.findViewById(R.id.search_et);
        welcome = view.findViewById(R.id.welcome);
        b1 = view.findViewById(R.id.b1);
        b2 = view.findViewById(R.id.b2);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        categoryModels = new ArrayList<>();

        for (int i = 0; i < categoryLogo.length; i++) {
            CategoryModel catModel = new CategoryModel(categoryLogo[i], categoryName[i]);
            categoryModels.add(catModel);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryAdapter = new CategoryAdapter(getContext(), categoryModels);

        recyclerView.setAdapter(categoryAdapter);


        db = FirebaseFirestore.getInstance();

        list = new ArrayList<ProductModel>();

        productAdapter = new ProductAdapter(getContext(), list);

        productRecyclerView.setAdapter(productAdapter);

        getData();


        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
                if(s.length()>0){
                    recyclerView.setVisibility(View.GONE);
                    welcome.setVisibility(View.GONE);
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    welcome.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                }
            }
        });



        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = "";
                st += value.getString("userStatus");


                if(st.equals("Customer")){
                    add_product_btn.setVisibility(View.GONE);
                }

            }
        })
        ;


        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProduct.class));
            }
        });

        return view;
    }

    private void filter(String text) {

        ArrayList<ProductModel> filterList = new ArrayList<>();

        for(ProductModel productModel : list){

            if (productModel.getpName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(productModel);
            }

        }

        productAdapter.filteredList(filterList);

    }

    private void getData() {

        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                list.add(dc.getDocument().toObject(ProductModel.class));
                            }

                            productAdapter.notifyDataSetChanged();

                        }

                    }
                });

    }
}