package com.example.imeshranawaka.styleomega.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyAccount extends Fragment {
    @BindView(R.id.txtUserName) TextView txtUserName;
    @BindView(R.id.txtEmail) TextView txtEmail;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        String email = sharedPref.getUserEmail();
        User user = User.find(User.class,"email=?",email).get(0);

        txtUserName.setText(user.getfName()+" "+user.getlName());
        txtEmail.setText(email);
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @OnClick(R.id.btnAccInfo)
    public void btnAccInfo_onClick(){
        AccountInfo accInfo = new AccountInfo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,accInfo,"AccountInfo");
        transaction.addToBackStack("MyAccount");

        transaction.commit();
    }

    @OnClick(R.id.btnChangePass)
    public void btnChangePass_onClick(){
        ChangePassword changePass = new ChangePassword();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,changePass,"ChangePassword");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @OnClick(R.id.btnAddBook)
    public void btnAddBook_onClick(){
        MyAddressBook add = new MyAddressBook();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,add,"MyAddressBook");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
