package com.example.ecom.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.ProductDetails;
import com.example.ecom.R;
import com.example.ecom.models.OrderModel;
import com.example.ecom.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModel> list;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_view_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel product = list.get(position);

        holder.pTitle.setText(product.getpName());
        holder.pPrice.setText(product.getpPrice()+"৳");

        holder.pTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("id",product.getpID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.logo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("id",product.getpID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long time = timestamp.getTime();
                String oID = "Order No :" + time;

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                OrderModel order = new OrderModel(oID, product.getpName(), product.getpType(), product.getpPrice(), FirebaseAuth.getInstance().getUid());

                db.collection("orders")
                        .document(oID)
                        .set(order);


                Toast.makeText(context, "Product Ordered", Toast.LENGTH_SHORT).show();

                holder.order.setEnabled(false);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredList(ArrayList<ProductModel> filterList) {

        list = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pTitle,pPrice,order;
        ImageView logo_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pTitle = itemView.findViewById(R.id.pTitle);
            pPrice = itemView.findViewById(R.id.pPrice);
            order = itemView.findViewById(R.id.order);
            logo_img = itemView.findViewById(R.id.logo_img);


        }
    }
}

