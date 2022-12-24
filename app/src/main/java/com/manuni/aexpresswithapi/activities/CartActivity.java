package com.manuni.aexpresswithapi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.manuni.aexpresswithapi.R;
import com.manuni.aexpresswithapi.adapters.CartAdapter;
import com.manuni.aexpresswithapi.databinding.ActivityCartBinding;
import com.manuni.aexpresswithapi.models.Product;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartAdapter cartAdapter;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        products  = new ArrayList<>();


        Cart cart = TinyCartHelper.getCart();

        for (Map.Entry<Item,Integer> item: cart.getAllItemsWithQty().entrySet()){
           Product product = (Product) item.getKey();
           int quantity = item.getValue();
           product.setQuantity(quantity);

           products.add(product);
        }


       /* products.add(new Product("Product name one","---","Some status",45,30,40,1));
        products.add(new Product("Product name two","---","Some status",45,30,40,1));
        products.add(new Product("Product name three","---","Some status",45,30,40,1));
        products.add(new Product("Product name four","---","Some status",45,30,40,1));
        products.add(new Product("Product name five","---","Some status",45,30,40,1));*/


        cartAdapter = new CartAdapter(this,products);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setLayoutManager(linearLayoutManager);


        binding.cartList.setAdapter(cartAdapter);


        binding.subTotal.setText(String.format("PKR %.2f",cart.getTotalPrice()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}