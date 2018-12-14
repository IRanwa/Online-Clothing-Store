package com.example.imeshranawaka.styleomega.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.imeshranawaka.styleomega.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfo extends Fragment {

    static View accView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accView = inflater.inflate(R.layout.fragment_account_info, container, false);
        accView.findViewById(R.id.genderSelection).setEnabled(false);

        accView.findViewById(R.id.btnCalendar).setOnClickListener(new btnCalendar_onClick());
        accView.findViewById(R.id.btnBack).setOnClickListener(new btnBack_onClick(this));
        accView.findViewById(R.id.btnUpdate).setOnClickListener(new btnUpdate_onClick());
        return accView;
    }

    private class btnCalendar_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DatePickerFragment dateFrag = new DatePickerFragment();
            dateFrag.show(getFragmentManager(),"date picker");

        }
    }

    public static DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            String date  = DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
            ((EditText)accView.findViewById(R.id.txtDOB)).setText(date);
        }
    };

    private class btnUpdate_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            accView.findViewById(R.id.txtFName).setEnabled(true);
            accView.findViewById(R.id.txtLName).setEnabled(true);
            accView.findViewById(R.id.txtContact).setEnabled(true);
            accView.findViewById(R.id.btnCalendar).setEnabled(true);
            accView.findViewById(R.id.genderSelection).setEnabled(true);
            ((ImageView)accView.findViewById(R.id.btnUpdate)).setImageResource(R.drawable.done_icon);
        }
    }
}
