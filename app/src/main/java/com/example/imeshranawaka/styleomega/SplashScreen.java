package com.example.imeshranawaka.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InsertCategories();
        InsertProducts();
        startActivity(new Intent(SplashScreen.this,StyleOmega.class));
        finish();
    }

    private void InsertProducts() {
        try {
            JSONArray array = new JSONArray(new loadJSONFromAsset().readJson("Products.json",getAssets()));
            List<Product> productList = Product.findWithQuery(Product.class,"Select * from Product");

            if(productList.size()!=array.length()) {
                for (int count = 0; count < array.length(); count++) {

                    JSONObject obj = array.getJSONObject(count);
                    ArrayList<String> imagesList = new Gson().fromJson(obj.getString("images"), new TypeToken<List<String>>(){}.getType());
                    String title = obj.getString("title");
                    Product product = new Product(
                            obj.getInt("category"),
                            obj.getString("title"),
                            obj.getString("description"),
                            obj.getDouble("price"),
                            obj.getInt("quantity"),
                            obj.getString("images")
                    );
                    product.save();

                    System.out.println("images "+imagesList.size());

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void InsertCategories() {
        try {
            JSONArray array = new JSONArray(new loadJSONFromAsset().readJson("Category.json",getAssets()));
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
