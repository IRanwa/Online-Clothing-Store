package com.example.imeshranawaka.styleomega.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewAddress extends Fragment {
    @BindView(R.id.txtFName) EditText txtFName;
    @BindView(R.id.txtLName) EditText txtLName;
    @BindView(R.id.txtContactNo) EditText txtContactNo;
    @BindView(R.id.txtCity) EditText txtCity;
    @BindView(R.id.txtAddress) EditText txtAddress;
    @BindView(R.id.txtZipCode) EditText txtZipCode;
    @BindView(R.id.provinceList) Spinner provinceList;
    @BindView(R.id.defaultSwitch) Switch defaultSwitch;
    private Unbinder unbinder;
    private long orderNo;
    public NewAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_address, container, false);
        unbinder = ButterKnife.bind(this,view);

        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(getContext(), R.array.province, R.layout.spinner_item_view);
        provinceAdapter.setDropDownViewResource(R.layout.spinner_item_view);
        provinceList.setAdapter(provinceAdapter);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            orderNo = bundle.getLong("orderNo");
        }
        return view;
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @OnClick(R.id.btnSave)
    public void btnSave_onClick(){
        String fName = txtFName.getText().toString();
        String lName = txtLName.getText().toString();
        String contactNo = txtContactNo.getText().toString();
        String city = txtCity.getText().toString();
        String address = txtAddress.getText().toString();
        String zipCode = txtZipCode.getText().toString();
        String province = provinceList.getSelectedItem().toString();
        boolean defaultAdd = defaultSwitch.isChecked();
        if(fName.isEmpty() || lName.isEmpty() || contactNo.isEmpty() || city.isEmpty() || address.isEmpty()
                || zipCode.isEmpty() || province.isEmpty()){
            Toast.makeText(getContext(),"Please fill all the details!",Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());

            List<Address> addressList = Address.find(Address.class, "province=? and city=? and address=? and user_Email=?"
                    ,province , city, address, sharedPref.getUserEmail());
            if(addressList.size()>0){
                Toast.makeText(getContext(),"Address Already Saved!",Toast.LENGTH_SHORT).show();
            }else{
                boolean defaultState = false;
                addressList = Address.find(Address.class, "user_Email=?"
                        , sharedPref.getUserEmail());
                if(defaultAdd){
                    for(Address tempAddress : addressList){
                        tempAddress.setDef(false);
                        tempAddress.save();
                    }
                }else{
                    for(Address tempAddress : addressList){
                        if(tempAddress.isDef()){
                            defaultState = true;
                            break;
                        }
                    }
                    if(!defaultState){
                        defaultAdd = true;
                    }
                }
                Address newAddress = new Address(sharedPref.getUserEmail(),fName,lName,address,city,Integer.parseInt(contactNo)
                        ,province,defaultAdd);
                newAddress.save();

                if(orderNo!=0){
                    Orders order = Orders.findById(Orders.class,orderNo);
                    order.setUserAddress(newAddress.getId());
                    order.save();
                }
                Fragment frag = getFragmentManager().findFragmentByTag("NewAddress");
                if(frag!=null) {
                    fragment_actions.getIntance(frag).btnBack_onClick();
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
