package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.StyleOmega;

public class MainMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        enableDrawer(v);
        v.findViewById(R.id.btnSideMenu).setOnClickListener(new btnSideMenu_onClick());
        return v;
    }


    private class btnSideMenu_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.START);
        }
    }

    private void enableDrawer(View v){
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
