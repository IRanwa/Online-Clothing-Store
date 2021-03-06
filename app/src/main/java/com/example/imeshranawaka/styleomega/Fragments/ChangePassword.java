package com.example.imeshranawaka.styleomega.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.Login;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {
    @BindView(R.id.txtExistPass) EditText txtExistPass;
    @BindView(R.id.txtNewPass) EditText txtNewPass;
    @BindView(R.id.txtConPass) EditText txtConPass;
    private Unbinder unbinder;

    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @OnClick(R.id.btnSavePass)
    public void btnSavePass_onClick(View v){
        String existPass = txtExistPass.getText().toString();
        String newPass = txtNewPass.getText().toString();
        String conPass = txtConPass.getText().toString();

        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        long id = sharedPref.getUserId();
        Login login = Login.findById(Login.class,id);
        if(existPass.equals(login.getPass())){
            if(newPass.equals(conPass)){
                login.setPass(newPass);
                login.save();

                SharedPreferences.Editor editor = SharedPreferenceUtility.getInstance(getContext()).getEditor();
                editor.remove("email");
                editor.remove("user");
                editor.remove("pass");
                editor.commit();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.mainFragment, new SignIn(), "SignIn");
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                transaction.commit();
            }else{
                Toast.makeText(getContext(),"Password not Matched!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(),"Existing Password Incorrect!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
