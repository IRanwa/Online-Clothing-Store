package com.example.imeshranawaka.styleomega.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfo extends Fragment {
    @BindView(R.id.genderSelection) Spinner genderSelection;
    @BindView(R.id.btnCalendar) Button btnCalendar;
    @BindView(R.id.btnBack) ImageView btnBack;
    @BindView(R.id.btnUpdate) ImageView btnUpdate;

    @BindView(R.id.txtFName) EditText txtFName;
    @BindView(R.id.txtLName) EditText txtLName;
    @BindView(R.id.txtUserCon) EditText txtContact;
    @BindView(R.id.txtDOB) EditText txtDOB;
    private Unbinder unbinder;
    private boolean updateState = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accView = inflater.inflate(R.layout.fragment_account_info, container, false);
        unbinder = ButterKnife.bind(this,accView);

        setupAccInfo();
        return accView;
    }

    private void setupAccInfo() {
        genderSelection.setEnabled(false);

        SharedPreferences shared = getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        String email = shared.getString("email","");
        User user = User.find(User.class,"email=?",email).get(0);

        txtFName.setText(user.getfName());
        txtLName.setText(user.getlName());
        txtFName.setText(user.getfName());
        txtContact.setText(String.valueOf(user.getContactNo()));
        txtDOB.setText(user.getDob());
        if(user.getUserGender()!=null){
            if(user.getUserGender().equalsIgnoreCase("male")){
                genderSelection.setSelection(1);
            }else{
                genderSelection.setSelection(2);
            }
        }

    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(View v){
        new fragment_actions(this).onClick(v);
    }

    @OnClick(R.id.btnCalendar)
    public void btnCalendar_onClick(){
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DATE);

        new DatePickerDialog(getContext(), new dataPickerListener(),year,month,day).show();
    }

    private class dataPickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            String date  = DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());

            txtDOB.setText(date);
        }
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdate_onClick(){
        if(updateState) {
            String fName = txtFName.getText().toString();
            String lName = txtLName.getText().toString();
            int contactNo = 0;
            if(txtContact.getText().length()!=0) {
                contactNo = Integer.parseInt(txtContact.getText().toString());
            }
            String dob = txtDOB.getText().toString();
            String gender = genderSelection.getSelectedItem().toString();

            if(fName.isEmpty() || lName.isEmpty()){
                Toast.makeText(getContext(),"Please fill first and last name!", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences shared = getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
                String email = shared.getString("email","");
                User user = User.find(User.class,"email=?",email).get(0);

                user.setfName(fName);
                user.setlName(lName);
                user.setContactNo(contactNo);
                user.setDob(dob);
                user.setUserGender(gender);
                user.save();
                new fragment_actions(this).onClick(getView());
            }

        }else{
            txtFName.setEnabled(true);
            txtLName.setEnabled(true);
            txtContact.setEnabled(true);
            btnCalendar.setEnabled(true);
            genderSelection.setEnabled(true);
            btnUpdate.setImageResource(R.drawable.done_icon);
            updateState = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
