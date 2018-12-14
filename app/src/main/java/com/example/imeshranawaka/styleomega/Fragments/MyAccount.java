package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.R;


public class MyAccount extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);

        v.findViewById(R.id.btnBack).setOnClickListener(new btnBack_onClick(this));
        v.findViewById(R.id.btnAccInfo).setOnClickListener(new btnAccInfo_onClick());
        v.findViewById(R.id.btnChangePass).setOnClickListener(new btnChangePass_onClick());
        return v;
    }

    private class btnAccInfo_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AccountInfo accInfo = new AccountInfo();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.mainFragment,accInfo,"AccountInfo");
            transaction.addToBackStack("MyAccount");
            transaction.commit();
        }
    }

    private class btnChangePass_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChangePassword changePass = new ChangePassword();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.mainFragment,changePass,"ChangePassword");
            transaction.addToBackStack("MyAccount");
            transaction.commit();
        }
    }
}
