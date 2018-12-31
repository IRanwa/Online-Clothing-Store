package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.ItemsAdapter;
import com.example.imeshranawaka.styleomega.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProducts extends Fragment {


    public SearchProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_products, container, false);
        String json = getArguments().getString("products");
        System.out.println(json);
        try {
            JSONArray array = new JSONArray(json);
            System.out.println(array);
            List<JSONObject> productsList = new ArrayList<>();
            for(int count = 0; count<array.length();count++){
                productsList.add(array.getJSONObject(count));
            }
            String title = getArguments().getString("title");

            ((TextView)v.findViewById(R.id.txtSearchName)).setText(title);
            setProducts(productsList,v);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

    private void setProducts(List<JSONObject> products, View v){
        RecyclerView recycleView = v.findViewById(R.id.productsList);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recycleView.setLayoutManager(layoutManager);

        ItemsAdapter productsAdapter = new ItemsAdapter(getContext(),getFragmentManager(),products,true);
        recycleView.setAdapter(productsAdapter);
    }

}
