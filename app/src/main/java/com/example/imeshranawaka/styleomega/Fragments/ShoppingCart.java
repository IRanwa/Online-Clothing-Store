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

import com.example.imeshranawaka.styleomega.Adapters.CartAdapter;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ShoppingCart extends Fragment {
    @BindView(R.id.orderProductsList)
    RecyclerView productsRecycleView;
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

        setupShoppingCart();
        return view;
    }

    private void setupShoppingCart() {
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        String email = sharedPref.getUserEmail();
        List<Orders> orders = Orders.find(Orders.class, "user_Email=? and order_Status=?", email, "pending");
        Orders order = orders.get(0);
        List<Order_Product> productsList = Order_Product.find(Order_Product.class, "order_No=?", order.getId().toString());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        productsRecycleView.setLayoutManager(layoutManager);

        CartAdapter adapter = new CartAdapter(getContext(), productsList,getActivity());
        productsRecycleView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
