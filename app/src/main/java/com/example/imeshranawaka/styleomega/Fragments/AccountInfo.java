package com.example.imeshranawaka.styleomega.Fragments;


import android.app.DatePickerDialog;
import android.media.Image;
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
import android.widget.TextView;

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

    @BindView(R.id.txtFName) TextView txtFName;
    @BindView(R.id.txtLName) TextView txtLName;
    @BindView(R.id.txtContact) TextView txtContact;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accView = inflater.inflate(R.layout.fragment_account_info, container, false);
        unbinder = ButterKnife.bind(this,accView);
        genderSelection.setEnabled(false);
        return accView;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(View v){
        new fragment_actions(this).onClick(v);
    }

    @OnClick(R.id.btnCalendar)
    public void btnCalendar_onClick(){
        DatePickerFragment dateFrag = new DatePickerFragment();
        dateFrag.show(getFragmentManager(),"date picker");
    }

    public static DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @BindView(R.id.txtDOB) EditText txtDOB;
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            String date  = DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
            txtDOB.setText(date);
        }
    };

    @OnClick(R.id.btnUpdate)
    public void btnUpdate_onClick(){
        txtFName.setEnabled(true);
        txtLName.setEnabled(true);
        txtContact.setEnabled(true);
        btnCalendar.setEnabled(true);
        genderSelection.setEnabled(true);
        btnUpdate.setImageResource(R.drawable.done_icon);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
