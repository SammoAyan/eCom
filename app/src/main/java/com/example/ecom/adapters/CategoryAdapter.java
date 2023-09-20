package com.example.ecom.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.CategoryView;
import com.example.ecom.R;
import com.example.ecom.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel> categoryModel;
    Context context;

    public CategoryAdapter(Context context,ArrayList<CategoryModel> categoryModel){

        this.context = context;
        this.categoryModel = categoryModel;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cat_image.setImageResource(categoryModel.get(position).getCategoryLogo());
        holder.cat_name.setText(categoryModel.get(position).getCategoryName());

        holder.cat_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cat = categoryModel.get(position).getCategoryName();

                Intent intent = new Intent(context, CategoryView.class);
                intent.putExtra("category",cat);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cat_image;
        TextView cat_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_image = itemView.findViewById(R.id.cat_image);
            cat_name = itemView.findViewById(R.id.cat_name);

        }
    }
}
