package com.example.imeshranawaka.styleomega.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.OrderProductAdapter;
import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetails extends Fragment {
    @BindView(R.id.txtOrderStatus) TextView txtOrderStatus;
    @BindView(R.id.txtOrderDate) TextView txtOrderDate;
    @BindView(R.id.txtOrderNo) TextView txtOrderNo;
    @BindView(R.id.txtTotalPrice) TextView txtTotalPrice;
    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.txtAddress) TextView txtAddress;
    @BindView(R.id.productsList) RecyclerView productsListRecycle;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.btnConfirm) Button btnConfirm;
    @BindView(R.id.btnRemove) Button btnRemove;

    private Unbinder unbinder;

    public OrderDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if(bundle!=null){
            long orderNo = bundle.getLong("OrderNo");
            setupDetails(orderNo);
        }
        return view;
    }

    private void setupDetails(long orderNo) {
        Orders order = Orders.findById(Orders.class,orderNo);
        txtOrderStatus.setText(order.getOrderStatus());
        txtOrderDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getPurchasedDate()));
        txtOrderNo.setText(String.valueOf(orderNo));
        List<Order_Product> orderProductList = Order_Product.find(Order_Product.class, "order_No=?", order.getId().toString());

        double price=0;
        for(int count=0;count<orderProductList.size();count++) {
            Order_Product orderProduct = orderProductList.get(count);
            Product product = Product.findById(Product.class, orderProduct.getProdId());
            price += product.getPrice() * orderProduct.getQuantity();
        }
        txtTotalPrice.setText("US$"+price);

        Address address = Address.findById(Address.class,order.getUserAddress());
        if(address!=null) {
            txtName.setText(address.getfName() + " " + address.getlName());
            txtAddress.setText(address.getAddress());
        }else{
            txtName.setText("Shipped Address Not Found!");
            txtName.setTextColor(Color.RED);
            txtAddress.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        productsListRecycle.setLayoutManager(layoutManager);

        OrderProductAdapter adapter = new OrderProductAdapter(getContext(), orderProductList, getActivity(),"OrderState");
        productsListRecycle.setAdapter(adapter);
        productsListRecycle.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
