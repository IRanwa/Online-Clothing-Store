package com.example.imeshranawaka.styleomega.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    @BindView(R.id.txtEmail) EditText txtEmail;
    @BindView(R.id.txtPass) EditText txtPass;
    @BindView(R.id.txtCPass) EditText txtCPass;
    @BindView(R.id.txtFName) EditText txtFName;
    @BindView(R.id.txtLName) EditText txtLName;


    private Unbinder unbinder;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this,view);
        // Inflate the layout for this fragment
        return view;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @OnClick(R.id.btnRegister)
    public void btnRegister_onClick(View view){
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();
        String cpass = txtCPass.getText().toString();
        String fName = txtFName.getText().toString();
        String lName = txtLName.getText().toString();

        if(email.isEmpty() || pass.isEmpty() || cpass.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            Toast.makeText(view.getContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
        }else if(!cpass.equals(pass)){
            Toast.makeText(view.getContext(),"Password not matched.",Toast.LENGTH_SHORT).show();
        }else{
            //Validate the email address
            String checkingText ="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern pattern = Pattern.compile(checkingText,Pattern.CASE_INSENSITIVE);
            Matcher match = pattern.matcher(email);
            if(match.find()) {
                List<Login> login = Login.find(Login.class, "email=?", email);
                if (login.size() > 0) {
                    Toast.makeText(view.getContext(), "User already registered", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(email, fName, lName);
                    user.save();

                    Login userLogin = new Login(email, pass);
                    userLogin.save();

                    SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
                    sharedPref.setUserId(userLogin.getId());
                    sharedPref.setUserEmail(userLogin.getEmail());
                    sharedPref.setUserPass(userLogin.getPass());

                    FragmentManager fm = getFragmentManager();
                    MainMenu menu = new MainMenu();
                    FragmentTransaction transaction = fm.beginTransaction();

                    int backStackEntry = fm.getBackStackEntryCount();
                    List<Fragment> fragments = fm.getFragments();
                    if (backStackEntry > 0) {
                        for (int i = 0; i < backStackEntry; i++) {
                            fm.popBackStackImmediate();
                            transaction.remove(fragments.get(i));
                            fragments = fm.getFragments();
                        }
                    }
                    transaction.replace(R.id.mainFragment, menu, "MainMenu");
                    transaction.commit();

                    fragment_actions.getIntance(Register.this).hideKeyboard();
                }
            }else{
                Toast.makeText(getContext(),"Invalid Email Address!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
