package com.manuni.aexpresswithapi.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manuni.aexpresswithapi.adapters.CategoryAdapter;
import com.manuni.aexpresswithapi.adapters.ProductAdapter;
import com.manuni.aexpresswithapi.databinding.ActivityMainBinding;
import com.manuni.aexpresswithapi.models.Category;
import com.manuni.aexpresswithapi.models.Product;
import com.manuni.aexpresswithapi.utils.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CategoryAdapter adapter;
    ArrayList<Category> categories;

    //for products

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categories();
        products();
        carouselData();

        /*binding.carousel.setShowCaption(false);
        binding.carousel.setShowNavigationButtons(false);
        binding.carousel.setAutoPlay(true);
        binding.carousel.setTouchToPause(true);*/
/*
       binding.carousel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int i = binding.carousel.getCurrentPosition();
               if (i==0){
                   Toast.makeText(MainActivity.this, "This is flowers", Toast.LENGTH_SHORT).show();
               }
           }
       });*/


    }

    private void getCategories() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("error", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("categories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Category data = new Category(
                                    object.getString("name"),
                                    Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(data);
                        }
                        adapter.notifyDataSetChanged();
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
        requestQueue.add(request);

    }

    private void getRecentOffers() {
        RequestQueue recentOffersQueue = Volley.newRequestQueue(this);
        StringRequest recentOffersRequest = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject recentOffersObj = new JSONObject(response);
                    if (recentOffersObj.getString("status").equals("success")) {
                        JSONArray recentOffersArray = recentOffersObj.getJSONArray("news_infos");
                        for (int i = 0; i < recentOffersArray.length(); i++) {
                            JSONObject valuesObject = recentOffersArray.getJSONObject(i);
                           /* binding.carousel.addData(new CarouselItem(Constants.NEWS_IMAGE_URL+valuesObject.getString("image"),
                                    valuesObject.getString("title")));*/
                            binding.carousel.addData(new CarouselItem(Constants.NEWS_IMAGE_URL+valuesObject.getString("image")
                            , valuesObject.getString("title")));
                        }
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
        recentOffersQueue.add(recentOffersRequest);
    }

    private void getProductsValues() {
        RequestQueue productQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCTS_URL + "?count=7";
        StringRequest productRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("error", response);
                    JSONObject productObject = new JSONObject(response);
                    if (productObject.getString("status").equals("success")) {
                        JSONArray productArray = productObject.getJSONArray("products");
                        for (int i = 0; i < productArray.length(); i++) {
                            JSONObject proObject = productArray.getJSONObject(i);
                            Product dataProduct = new Product(proObject.getString("name"),
                                    Constants.PRODUCTS_IMAGE_URL + proObject.getString("image"),
                                    proObject.getString("status"),
                                    proObject.getDouble("price"),
                                    proObject.getDouble("price_discount"),
                                    proObject.getInt("stock"),
                                    proObject.getInt("id"));
                            products.add(dataProduct);
                        }
                        productAdapter.notifyDataSetChanged();
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
        productQueue.add(productRequest);
    }

    private void carouselData() {
        getRecentOffers();
       /* binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/Kaktus%20Yang%20Cantik.jpg", "Beautiful flowers"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/We%20Join%20Smartphone%20Fair%20%20in%20Washington%20DC%20April%2078%202017%20Visit%20and%20Purchase%20our%20Product.jpg", "Camera category"));
*/
    }

    private void categories() {

        binding.categoriesList.setLayoutManager(new GridLayoutManager(this, 4));
        categories = new ArrayList<>();
       /* categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","first item",1));
        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#fb0504","first item",1));
        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#4186ff","first item",1));
        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#BF360C","first item",1));
        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#9c4db1","first item",1));
        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#ff870e","first item",1));*/
        getCategories();

        adapter = new CategoryAdapter(this, categories);
        binding.categoriesList.setAdapter(adapter);
    }

    private void products() {
        binding.productList.setLayoutManager(new GridLayoutManager(this, 2));
        products = new ArrayList<>();
        getProductsValues();
       /* products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));
        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1650597798314.jpg","",200,10,2,2));*/
        productAdapter = new ProductAdapter(this, products);
        binding.productList.setAdapter(productAdapter);
    }
}