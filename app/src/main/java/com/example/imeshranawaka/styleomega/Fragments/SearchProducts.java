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
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProducts extends Fragment {

    @BindView(R.id.txtSearchName) TextView txtSearchName;
    @BindView(R.id.productsList) RecyclerView productsList;
    private Unbinder unbinder;

    public SearchProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_products, container, false);
        unbinder = ButterKnife.bind(this,v);
        List<Product> productsList = (List<Product>) getArguments().getSerializable("products");

        String title = getArguments().getString("title");
        txtSearchName.setText(title);
        setProducts(productsList);


        return v;
    }

    @OnClick(R.id.btnCart)
    public void btnCart_onClick(){
        fragment_actions.getIntance(this).btnCart_onClick();
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    private void setProducts(List<Product> products){
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        productsList.setLayoutManager(layoutManager);

        ItemsAdapter productsAdapter = new ItemsAdapter(getContext(),getFragmentManager(),products,true);
        productsList.setAdapter(productsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
