package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.OrderProductAdapter;
import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PlaceOrder extends Fragment {
    @BindView(R.id.productsList) RecyclerView productsRecycleView;
    private Unbinder unbinder;
    private long orderNo;
    private List<Order_Product> productsList;
    private String orderStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);
        unbinder = ButterKnife.bind(this,view);


        Bundle bundle = getArguments();
        boolean checkAll = bundle.getBoolean("checkAll");
        if(checkAll){
            orderNo = bundle.getLong("orderNo");
            productsList = Order_Product.find(Order_Product.class,"order_No=?",String.valueOf(orderNo));
        }else{
            orderStatus = bundle.getString("OrderStatus");
            productsList = (List<Order_Product>) bundle.getSerializable("order_products");
        }

        setupProductsList(productsList);
        setTotalPrice(productsList,view);
        setAddress(view);
        return view;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    private void setAddress(View view) {
        if(orderNo!=0){
            Orders order = Orders.findById(Orders.class,orderNo);
            Address address=null;
            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
            String email = sharedPref.getUserEmail();

            List<Address> addressList = Address.find(Address.class, "user_Email=?", email);
            for(Address tempAddress : addressList){
                if(tempAddress.isDef()){
                    address = tempAddress;
                    break;
                }
            }
            if(address!=null) {
                order.setUserAddress(address.getId());
                order.save();
                ((TextView) view.findViewById(R.id.txtAddressName)).setText(address.getfName() + " " + address.getlName());
                ((TextView) view.findViewById(R.id.txtAddress)).setText(address.getAddress() + ", " + address.getCity() + ", " + address.getProvince());
            }else{
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction transaction = fm.beginTransaction();
                NewAddress newAddress = new NewAddress();
                Bundle bundle = new Bundle();
                bundle.putLong("orderNo",orderNo);
                newAddress.setArguments(bundle);
                transaction.replace(R.id.mainFragment,newAddress,"NewAddress");
                transaction.addToBackStack("ShoppingCart");
                transaction.commit();
            }
        }else{
            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
            String email = sharedPref.getUserEmail();

            List<Address> addressList = Address.find(Address.class, "user_Email=?", email);
            Address address= null;
            for(Address tempAddress : addressList){
                if(tempAddress.isDef()){
                    address = tempAddress;
                    break;
                }
            }
            if(address!=null){
                ((TextView) view.findViewById(R.id.txtAddressName)).setText(address.getfName() + " " + address.getlName());
                ((TextView) view.findViewById(R.id.txtAddress)).setText(address.getAddress() + ", " + address.getCity() + ", " + address.getProvince());
            }else{
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.mainFragment,new NewAddress(),"NewAddress");
                transaction.addToBackStack("ShoppingCart");
                transaction.commit();
            }
        }
    }

    private void setTotalPrice(List<Order_Product> productsList, View view) {
        double total = 0.0;
        for(Order_Product orderProd : productsList){
            Product prod = Product.findById(Product.class,orderProd.getProdId());
            total += prod.getPrice() * orderProd.getQuantity();
        }

        ((TextView)view.findViewById(R.id.txtTotalPrice)).setText("US$"+total);
    }

    private void setupProductsList(List<Order_Product> productsList) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        productsRecycleView.setLayoutManager(layoutManager);

        OrderProductAdapter adapter = new OrderProductAdapter(getContext(), productsList, getActivity(),"CartState");
        productsRecycleView.setAdapter(adapter);
        productsRecycleView.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.btnConfirmOrder)
    public void btnConfirmOrder(){
        if(orderNo!=0){
            Orders order = Orders.findById(Orders.class, orderNo);
            order.setOrderStatus("Delivery Pending");
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            order.setPurchasedDate(date);
            order.save();
            for(Order_Product orderProd : productsList){
                int quantity = orderProd.getQuantity();
                Product product = Product.findById(Product.class,orderProd.getProdId());
                product.setQuantity(product.getQuantity()-quantity);
                product.save();
            }
        }else{
            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
            String email = sharedPref.getUserEmail();
            List<Address> addressList = Address.find(Address.class, "user_Email=?", email);
            Address address=null;
            for(Address tempAdd : addressList){
                if(tempAdd.isDef()){
                    address = tempAdd;
                    break;
                }
            }
            Orders order = new Orders("Delivery Pending",email,address.getId());
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            order.setPurchasedDate(date);
            order.setUserAddress(address.getId());
            order.save();

            for(Order_Product orderProd : productsList){
                orderProd.setOrderNo(order.getId());
                orderProd.save();
                int quantity = orderProd.getQuantity();
                Product product = Product.findById(Product.class,orderProd.getProdId());
                product.setQuantity(product.getQuantity()-quantity);
                product.save();
            }
        }
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDestroy() {
        if(orderStatus!=null && orderStatus.equals("OrderNow")){
            productsList.get(0).delete();
        }
        super.onDestroy();
    }
}
