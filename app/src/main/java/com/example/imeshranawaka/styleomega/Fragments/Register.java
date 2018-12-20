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
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    private View view;
    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.btnBack).setOnClickListener(new fragment_actions(this));
        view.findViewById(R.id.btnRegister).setOnClickListener(new btnRegister_onClick());
        // Inflate the layout for this fragment
        return view;
    }

    private class btnRegister_onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String email = ((EditText)view.findViewById(R.id.txtEmail)).getText().toString();
            String pass = ((EditText)view.findViewById(R.id.txtPass)).getText().toString();
            String cpass = ((EditText)view.findViewById(R.id.txtCPass)).getText().toString();
            String fName = ((EditText)view.findViewById(R.id.txtFName)).getText().toString();
            String lName = ((EditText)view.findViewById(R.id.txtLName)).getText().toString();

            if(email.isEmpty() || pass.isEmpty() || cpass.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
                Toast.makeText(view.getContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
            }else if(!cpass.equals(pass)){
                Toast.makeText(view.getContext(),"Password not matched.",Toast.LENGTH_SHORT).show();
            }else{
                List<Login> login = Login.find(Login.class, "email=?", email);
                if(login.size()>0){
                    Toast.makeText(view.getContext(),"User already registered",Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User(email,fName,lName);
                    user.save();

                    Login userLogin = new Login(email,pass);
                    userLogin.save();

                    Bundle bundle=new Bundle();
                    bundle.putString("email",email);

                    FragmentManager fm = getFragmentManager();
                    MainMenu menu = new MainMenu();
                    menu.setArguments(bundle);
                    FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, menu,"MainMenu");
                    transaction.commit();

                    fragment_actions f = new fragment_actions(Register.this);
                    f.hideKeyboard();
                }
            }


        }
    }
}
