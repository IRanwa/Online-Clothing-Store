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
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.loadJSONFromAsset;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final FragmentManager fm;
    private JSONArray mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public CategoryAdapter(FragmentManager fm, Context context, JSONArray categoryList) {
        this.fm = fm;
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ImageView imgView;
        public LinearLayout conatiner;
        public ViewHolder(View v){
            super(v);
            mTextView = v.findViewById(R.id.txtTitle);
            imgView = v.findViewById(R.id.imgCategory);
            conatiner = v.findViewById(R.id.categoryContainer);
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
        try {
            JSONObject category = (JSONObject) mDataSet.get(i);
            viewHolder.mTextView.setText(category.getString("title"));
            Picasso.get().load(category.getString("image"))
                    .resize(100,100).error(R.drawable.add_icon).into(viewHolder.imgView);
            viewHolder.conatiner.setOnClickListener(new category_onClick(category));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }

    private class category_onClick implements View.OnClickListener {
        JSONObject category;
        public category_onClick(JSONObject category) {
            this.category = category;
        }

        @Override
        public void onClick(View v) {
            try {
                JSONObject json = new JSONObject(new loadJSONFromAsset().readJson("Products.json", mContext.getAssets()));
                JSONArray prodList = json.getJSONArray("products");
                JSONArray productsList = new JSONArray();

                for(int count = 0; count<prodList.length();count++){
                    JSONObject obj = prodList.getJSONObject(count);
                    if(obj.getInt("category")==category.getInt("id")){
                        productsList.put(obj);
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("products", productsList.toString());
                String title = category.getString("title");
                if(title.length()>25) {
                    title = title.substring(0, 25);
                }
                bundle.putString("title", title);

                SearchProducts searchProd = new SearchProducts();
                searchProd.setArguments(bundle);

                FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, searchProd,"SearchProducts");
                transaction.addToBackStack("MainMenu");
                transaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
