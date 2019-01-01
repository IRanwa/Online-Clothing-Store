package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imeshranawaka.styleomega.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyAccount extends Fragment {

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(View v){
        new fragment_actions(this).onClick(v);
    }

    @OnClick(R.id.btnAccInfo)
    public void btnAccInfo_onClick(){
        AccountInfo accInfo = new AccountInfo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.mainFragment,accInfo,"AccountInfo");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @OnClick(R.id.btnChangePass)
    public void btnChangePass_onClick(){
        ChangePassword changePass = new ChangePassword();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.mainFragment,changePass,"ChangePassword");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @OnClick(R.id.btnAddBook)
    public void btnAddBook_onClick(){
        MyAddressBook add = new MyAddressBook();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.mainFragment,add,"ChangePassword");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
