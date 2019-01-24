package com.example.imeshranawaka.styleomega.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Adapters.CartAdapter;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ShoppingCart extends Fragment {
    @BindView(R.id.orderProductsList) RecyclerView productsRecycleView;
    CartAdapter adapter;
    private Unbinder unbinder;

    public ShoppingCart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupShoppingCart();
    }

    private void setupShoppingCart() {
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        String email = sharedPref.getUserEmail();
        List<Orders> orders = Orders.find(Orders.class, "user_Email=? and order_Status=?", email, "pending");
        if(orders.size()>0) {
            Orders order = orders.get(0);
            List<Order_Product> productsList = Order_Product.find(Order_Product.class, "order_No=?", order.getId().toString());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            productsRecycleView.setLayoutManager(layoutManager);

            adapter = new CartAdapter(getContext(), productsList, getActivity());
            productsRecycleView.setAdapter(adapter);
        }else{
            productsRecycleView.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Cart Empty!",Toast.LENGTH_SHORT).show();
            fragment_actions.getIntance(this).btnBack_onClick();
        }
    }

    @OnClick(R.id.checkboxAll)
    public void checkboxAll_onClick(View v){
        CheckBox checkbox = v.findViewById(R.id.checkboxAll);
        boolean status = false;
        if(checkbox.isChecked()){
            status = true;
        }
        adapter.checkboxall_onClick(status);
    }

    @OnClick(R.id.btnDeleteSelected)
    public void btnDeleteSelected_onClick(View v){
        adapter.btnDeleteSelected_onClick(v);
    }

    @OnClick(R.id.btnCheckout)
    public void btnCheckout_onClick(View v){
        CheckBox checkbox = v.getRootView().findViewById(R.id.checkboxAll);
        boolean status = false;
        if(checkbox.isChecked()){
            status = true;
        }
        adapter.btnCheckout_onClick(status);
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
