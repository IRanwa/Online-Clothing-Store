package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private JSONArray mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public CategoryAdapter(Context context, JSONArray addressList) {
        mDataSet = addressList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ImageView imgView;
        public ViewHolder(View v){
            super(v);
            mTextView = v.findViewById(R.id.txtTitle);
            imgView = v.findViewById(R.id.imgCategory);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }
}
