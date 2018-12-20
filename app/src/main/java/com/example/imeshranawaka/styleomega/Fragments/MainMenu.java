package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.StyleOmega;

import java.util.List;

public class MainMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arg = getArguments();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        enableDrawer();
        setNavHeader(arg.getString("email"));
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

    private void enableDrawer(){
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void setNavHeader(String loginEmail){
        TextView name = getActivity().findViewById(R.id.txtName);
        TextView email = getActivity().findViewById(R.id.txtHeadEmail);

        User user = User.find(User.class, "email=?", loginEmail).get(0);
        name.setText(user.getfName()+" "+user.getlName());
        email.setText(loginEmail);

    }
}
