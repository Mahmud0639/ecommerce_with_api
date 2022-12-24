package com.manuni.aexpresswithapi.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manuni.aexpresswithapi.R;
import com.manuni.aexpresswithapi.databinding.ItemCartBinding;
import com.manuni.aexpresswithapi.databinding.QuantityDialogBinding;
import com.manuni.aexpresswithapi.models.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    Context context;
    ArrayList<Product> products;

    public CartAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);

        Glide.with(context).load(product.getProductImage()).into(holder.binding.image);
        holder.binding.productName.setText(product.getProductName());
        holder.binding.price.setText("PKR "+product.getPrice());
        holder.binding.quantity.setText(product.getQuantity()+" item(s)");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                quantityDialogBinding.productName.setText(product.getProductName());
                quantityDialogBinding.productStock.setText("Stock "+product.getStock());
                quantityDialogBinding.quantity.setText(""+product.getQuantity());

                int stock = product.getStock();

                quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ItemCartBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemCartBinding.bind(itemView);
        }
    }
}
