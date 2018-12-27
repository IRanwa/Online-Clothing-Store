package com.example.imeshranawaka.styleomega.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private JSONArray mDataSet;
    private JSONArray categoryList;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public ProductsAdapter(Context context, JSONArray productsList, JSONArray categoryList) {
        mDataSet = productsList;
        this.categoryList = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mainpage_product_container,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder viewHolder, int i) {
        try {
            JSONObject category = (JSONObject) categoryList.get(i);
            viewHolder.category.setText(category.getString("title"));

            int productNum = 0;
            List<JSONObject> productsList = new ArrayList<>();
            for(int count=0;count<mDataSet.length();count++){
                JSONObject product = (JSONObject) mDataSet.get(count);
                if(product.getInt("category")== category.getInt("id")){
                    productsList.add(product);
                    productNum++;
                }

                if(productNum>5){
                    break;
                }
            }


            viewHolder.itemsList.setNestedScrollingEnabled(false);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
            viewHolder.itemsList.setLayoutManager(layoutManager);

            ItemsAdapter itemsAdapter = new ItemsAdapter(mContext,productsList);
            viewHolder.itemsList.setAdapter(itemsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView category;
        public RecyclerView itemsList;
        public ViewHolder(@NonNull View v) {
            super(v);
            category = v.findViewById(R.id.txtCategory);
            itemsList = v.findViewById(R.id.itemsList);
            viewsList.add(v);
        }
    }
}
