package com.manuni.aexpresswithapi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.manuni.aexpresswithapi.R;
import com.manuni.aexpresswithapi.databinding.ActivityProductDetailBinding;
import com.manuni.aexpresswithapi.models.Product;
import com.manuni.aexpresswithapi.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Product_detail_activity extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    Product currentProduct;

    String name;
    String image;
    Double price;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        price = getIntent().getDoubleExtra("price",0);
        id = getIntent().getIntExtra("id",0);

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(image).into(binding.productImage);
        getProductDetails(id);

        Cart cart = TinyCartHelper.getCart();

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);//here 1 is by default we want to add 1 product
            }
        });


    }
    private void getProductDetails(int id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_DETAILS_URL + id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject productDetailsObj = new JSONObject(response);
                    if (productDetailsObj.getString("status").equals("success")){
                        JSONObject object = productDetailsObj.getJSONObject("product");
                        binding.productDescription.setText(Html.fromHtml(object.getString("description")));
                        //below currentProduct is for add to card library(tiny cart)
                        currentProduct = new Product(object.getString("name"),Constants.PRODUCTS_IMAGE_URL + object.getString("image")
                        ,object.getString("status"),object.getDouble("price"),object.getDouble("price_discount"),object.getInt("stock")
                        ,object.getInt("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.cart){
            Intent intent = new Intent(Product_detail_activity.this,CartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}