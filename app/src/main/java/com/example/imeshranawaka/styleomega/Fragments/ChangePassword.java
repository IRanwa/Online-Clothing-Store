package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {


    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        v.findViewById(R.id.btnBack).setOnClickListener(new btnBack_onClick(this));
        v.findViewById(R.id.btnSavePass).setOnClickListener(new btnSavePass_onClick());
        return v;
    }

    private class btnSavePass_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    }
}
