package com.example.ecom;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.ecom.adapters.OrderAdapter;
import com.example.ecom.adapters.ProductAdapter;
import com.example.ecom.models.OrderModel;
import com.example.ecom.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

    View view;
    RecyclerView orderList;

    OrderAdapter orderAdapter;
    ArrayList<OrderModel> list;

    FirebaseFirestore db;
    String myStatus ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_orders, container, false);


        orderList = view.findViewById(R.id.orderList);

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<OrderModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        orderList.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        orderAdapter = new OrderAdapter(getContext(), list);
        orderList.setAdapter(orderAdapter);

        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = value.getString("userStatus");

                if (st.equals("Admin")) {
                    myStatus = "Admin";
                } else if (st.equals("Customer")) {
                    myStatus = "Customer";
                }



            }
        })
        ;


        db.collection("orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                OrderModel orderModel = dc.getDocument().toObject(OrderModel.class);



                                if(myStatus.equals("Admin")){
                                    list.add(dc.getDocument().toObject(OrderModel.class));


                                }
                                else{
                                    OrderModel order = dc.getDocument().toObject(OrderModel.class);

                                    if(order.getBuyerUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                        list.add(dc.getDocument().toObject(OrderModel.class));
                                    }
                                }

                            }

                            orderAdapter.notifyDataSetChanged();

                        }

                    }
                });

        return view;
    }
}