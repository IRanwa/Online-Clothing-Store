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
public class SignIn extends Fragment {


    public SignIn() {
        // Required empty public constructor
    }


    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment

        v.findViewById(R.id.btnRegister).setOnClickListener(new registerNow_onClick());

        return v;
    }

    private class registerNow_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Register register = new Register();
            transaction.add(R.id.mainFragment,register,"Register");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
