package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {


    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        v.findViewById(R.id.btnBack).setOnClickListener(new btnBack_onClick(this));
        v.findViewById(R.id.btnRegister).setOnClickListener(new btnRegister_onClick());
        // Inflate the layout for this fragment
        return v;
    }

    private class btnRegister_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            MainMenu menu = new MainMenu();
            FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, menu,"MainMenu");
            transaction.commit();
        }
    }
}
