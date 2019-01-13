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

import com.example.imeshranawaka.styleomega.Adapters.AddressAdapter;
import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressBook extends Fragment {

    @BindView(R.id.addressList) RecyclerView addressListRecycle;
    private Unbinder unbonder;

    public MyAddressBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_address_book, container, false);
        unbonder = ButterKnife.bind(this,v);
        setList(v);
        return v;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(View v){
        new fragment_actions(this).onClick(v);
    }

    @OnClick(R.id.btnAddAddress)
    public void btnAddAddress_onClick(){
        FragmentManager fm = getFragmentManager();
        NewAddress newAddress = new NewAddress();
        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, newAddress,"NewAddress");
        transaction.addToBackStack("MyAddressBook");
        transaction.commit();
    }

    private void setList(View v) {
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        List<Address> addressList = Address.find(Address.class, "user_Email=?", sharedPref.getUserEmail());
        if(addressList.size()!=0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            addressListRecycle.setLayoutManager(layoutManager);

            AddressAdapter addressAdapter = new AddressAdapter(getContext(), addressList);
            addressListRecycle.setAdapter(addressAdapter);
        }else{
            addressListRecycle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbonder.unbind();
    }
}
