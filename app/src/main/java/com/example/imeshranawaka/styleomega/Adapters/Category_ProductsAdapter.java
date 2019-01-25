package com.example.imeshranawaka.styleomega.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Category_ProductsAdapter extends RecyclerView.Adapter<Category_ProductsAdapter.ViewHolder> {
    private FragmentManager fm;
    private List<Category> categoryList;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public Category_ProductsAdapter(Context context, FragmentManager fm, List<Category> categoryList) {
        this.fm = fm;
        this.categoryList = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Category_ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mainpage_product_container,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Category_ProductsAdapter.ViewHolder viewHolder, int i) {

        Category category = categoryList.get(i);
        viewHolder.category.setText(category.getTitle());

        List<Product> tempProdList = Product.find(Product.class,
                "cat_Id=?",String.valueOf(category.getCatId()));
        List<Product> productsList = new ArrayList<>();
        for(int count = 0;count<tempProdList.size();count++){
            productsList.add(tempProdList.get(count));
            if(count>6){
                break;
            }
            count++;
        }

        viewHolder.itemsList.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        viewHolder.itemsList.setLayoutManager(layoutManager);

        ItemsAdapter itemsAdapter = new ItemsAdapter(mContext,fm, productsList,false);
        viewHolder.itemsList.setAdapter(itemsAdapter);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtCategory) TextView category;
        @BindView(R.id.itemsList) RecyclerView itemsList;
        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }
}
