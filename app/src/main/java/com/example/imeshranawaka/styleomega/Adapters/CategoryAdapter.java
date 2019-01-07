package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Fragments.SearchProducts;
import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.loadJSONFromAsset;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final FragmentManager fm;
    private List<Category> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public CategoryAdapter(FragmentManager fm, Context context, List<Category> categoryList) {
        this.fm = fm;
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle) TextView mTextView;
        @BindView(R.id.imgCategory) ImageView imgView;
        @BindView(R.id.categoryContainer) LinearLayout conatiner;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_container,viewGroup,false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {
        System.out.println(mDataSet);
        Category category = mDataSet.get(i);
            System.out.println(category);
            viewHolder.mTextView.setText(category.getTitle());
            Picasso.get().load(category.getImage())
                    .resize(100,100).error(R.drawable.add_icon).into(viewHolder.imgView);
            viewHolder.conatiner.setOnClickListener(new category_onClick(category));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private class category_onClick implements View.OnClickListener {
        Category category;
        public category_onClick(Category category) {
            this.category = category;
        }

        @Override
        public void onClick(View v) {

            List<Product> prodList = Product.find(Product.class, "cat_Id=?", category.getId().toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("products", (Serializable) prodList);
                String title = category.getTitle();
                if(title.length()>25) {
                    title = title.substring(0, 25);
                }
                bundle.putString("title", title);

                SearchProducts searchProd = new SearchProducts();
                searchProd.setArguments(bundle);

                FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, searchProd,"SearchProducts");
                transaction.addToBackStack("MainMenu");
                transaction.commit();
        }
    }
}
