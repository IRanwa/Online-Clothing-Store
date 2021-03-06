package com.example.imeshranawaka.styleomega.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.Login;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.txtEmail) EditText editTxtEmail;
    @BindView(R.id.txtPass) EditText editTxtPass;
    public SignIn() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btnRegister)
    public void registerNow_onClick(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Register register = new Register();
        //AccountInfo ac = new AccountInfo();
        transaction.replace(R.id.mainFragment,register,"Register");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @OnClick(R.id.btnSignIn)
    public void signIn_onClick(){
        String email = editTxtEmail.getText().toString();
        String pass = editTxtPass.getText().toString();
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
                        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
                        sharedPref.setUserId(u.getId());
                        sharedPref.setUserEmail(u.getEmail());
                        sharedPref.setUserPass(u.getPass());

                        FragmentManager fm = getFragmentManager();
                        MainMenu menu = new MainMenu();
                        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, menu,"MainMenu");
                        transaction.commit();

                        fragment_actions.getIntance(SignIn.this).hideKeyboard();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
