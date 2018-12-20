package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.Login;
import com.example.imeshranawaka.styleomega.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends Fragment {


    public SignIn() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment

        view.findViewById(R.id.btnRegister).setOnClickListener(new registerNow_onClick());
        view.findViewById(R.id.btnSignIn).setOnClickListener(new signIn_onClick());
        return view;
    }

    private class registerNow_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Register register = new Register();
            //AccountInfo ac = new AccountInfo();
            transaction.add(R.id.mainFragment,register,"Register");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private class signIn_onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String email = ((EditText)view.findViewById(R.id.txtEmail)).getText().toString();
            String pass = ((EditText)view.findViewById(R.id.txtPass)).getText().toString();
            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(view.getContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
            }else{
                List<Login> users = Login.find(Login.class, "email=?", email);
                if(users.size()==0){
                    Toast.makeText(view.getContext(),"User not registered",Toast.LENGTH_SHORT).show();
                }else{
                    for(Login u : users){
                        if(!u.getPass().equals(pass)){
                            Toast.makeText(view.getContext(),"Incorrect password. Try again!",Toast.LENGTH_SHORT).show();
                        }else{
                            Bundle bundle=new Bundle();
                            bundle.putString("email",email);

                            FragmentManager fm = getFragmentManager();
                            MainMenu menu = new MainMenu();
                            menu.setArguments(bundle);
                            FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, menu,"MainMenu");
                            transaction.commit();

                            fragment_actions f = new fragment_actions(SignIn.this);
                            f.hideKeyboard();
                        }
                    }
                }
            }
        }
    }

}
