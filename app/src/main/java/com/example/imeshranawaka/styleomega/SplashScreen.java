package com.example.imeshranawaka.styleomega;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getJsonData();
    }

    private void getJsonData() {
        queue = Volley.newRequestQueue(this);
        String url ="https://api.myjson.com/bins/1egyaw";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            InsertCategories(new JSONArray(response));
                        } catch (JSONException e) {
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
            }
        });
        stringRequest.setTag("categories");
        queue.add(stringRequest);

        url ="https://api.myjson.com/bins/e7nk8";
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            InsertProducts(new JSONArray(response));
                            startActivity(new Intent(SplashScreen.this,StyleOmega.class));
                        } catch (JSONException e) {

                        }
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
            }
        });
        stringRequest.setTag("products");
        queue.add(stringRequest);
    }

    private void InsertProducts(JSONArray array) {
        try {
            List<Product> productList = Product.listAll(Product.class);

            if(productList.size()!=array.length()) {
                for (int count = 0; count < array.length(); count++) {

                    JSONObject obj = array.getJSONObject(count);
                    ArrayList<String> imagesList = new Gson().fromJson(obj.getString("images"), new TypeToken<List<String>>(){}.getType());
                    String title = obj.getString("title");
                    String sizeString = "";
                    try {
                        JSONArray size = obj.getJSONArray("size");
                        sizeString = size.toString();
                    }catch(Exception ex){

                    }
                    Product product = new Product(
                            obj.getInt("category"),
                            obj.getString("title"),
                            obj.getString("description"),
                            obj.getDouble("price"),
                            obj.getInt("quantity"),
                            obj.getString("images"),
                            sizeString
                    );
                    product.save();

                    System.out.println("images "+imagesList.size());

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void InsertCategories(JSONArray array) {
        try {
            for(int count=0;count<array.length();count++){
                JSONObject obj = array.getJSONObject(count);
                List<Category> catList = Category.find(Category.class, "title=?", obj.getString("title"));
                if(catList.size()==0){
                    Category category = new Category(
                            obj.getInt("id"),
                            obj.getString("title"),
                            obj.getString("description"),
                            obj.getString("image"));
                    category.save();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("categories");
            queue.cancelAll("products");
        }
    }
}
