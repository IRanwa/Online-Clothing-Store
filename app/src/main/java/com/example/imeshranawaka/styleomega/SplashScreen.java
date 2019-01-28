package com.example.imeshranawaka.styleomega;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = "https://api.myjson.com/bins/1egyaw";
        new MyAsyncTask().execute(url,"categories");
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {

        InputStream inputStream = null;
        String status;
        @Override
        protected String doInBackground(String... params) {
            status = params[1];
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
            } catch (Exception ex){

            }

            String result=null;
            if(inputStream!=null) {
                try {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    inputStream.close();
                    result = sBuilder.toString();

                } catch (Exception e) {

                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result!=null) {
                try {
                    JSONArray array = new JSONArray(result);
                    if(status.equalsIgnoreCase("categories")){
                        InsertCategories(array);
                        String url = "https://api.myjson.com/bins/e7nk8";
                        new MyAsyncTask().execute(url,"products");
                    }else{
                        InsertProducts(array);
                        startActivity(new Intent(SplashScreen.this,StyleOmega.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
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
}
