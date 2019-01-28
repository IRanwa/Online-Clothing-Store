package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.Adapters.AddressAdapter;
import com.example.imeshranawaka.styleomega.Adapters.OrdersAdapter;
import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;
import com.orm.query.Select;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment {
    @BindView(R.id.ordersList) RecyclerView ordersListRecycle;
    private Unbinder unbinder;

    public MyOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOrdersList();
    }

    private void setupOrdersList() {
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        List<Orders> ordersList = Orders.find(Orders.class, "user_Email=? and order_Status!=? Order BY purchased_Date desc",
                sharedPref.getUserEmail(),"pending");
        if(ordersList.size()!=0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            ordersListRecycle.setLayoutManager(layoutManager);

            OrdersAdapter ordersAdapter = new OrdersAdapter(getContext(), ordersList, getActivity());
            ordersListRecycle.setAdapter(ordersAdapter);
        }else{
            ordersListRecycle.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onCLick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
