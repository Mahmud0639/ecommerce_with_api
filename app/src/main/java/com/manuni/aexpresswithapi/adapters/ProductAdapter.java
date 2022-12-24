package com.manuni.aexpresswithapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manuni.aexpresswithapi.R;
import com.manuni.aexpresswithapi.activities.Product_detail_activity;
import com.manuni.aexpresswithapi.databinding.ProductItemBinding;
import com.manuni.aexpresswithapi.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        Glide.with(context).load(product.getProductImage()).into(holder.binding.image);
        holder.binding.label.setText(product.getProductName());
        holder.binding.price.setText("PKR "+product.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Product_detail_activity.class);
                intent.putExtra("name",product.getProductName());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("image",product.getProductImage());
                intent.putExtra("id",product.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ProductItemBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductItemBinding.bind(itemView);
        }
    }
}
