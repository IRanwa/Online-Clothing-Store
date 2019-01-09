package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private List<Address> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public AddressAdapter(Context context, List<Address> addressList) {
        mDataSet = addressList;
        mContext = context;
        viewsList = new ArrayList<>();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public @BindView(R.id.txtName) TextView txtName;
        public @BindView(R.id.txtAddress) TextView txtAddress;
        public @BindView(R.id.defBtnRadio) RadioButton defBtnRadio;
        public @BindView(R.id.btnDelete) ImageView btnDelete;
        public View currentView;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            currentView = v;
            viewsList.add(v);
        }

        @OnClick(R.id.defBtnRadio)
        void defBtnRadio_onClick(){
            for(View view : viewsList){
                RadioButton tempBtn = view.findViewById(R.id.defBtnRadio);
                if(tempBtn!=defBtnRadio){
                    tempBtn.setChecked(false);
                }
            }
        }
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.addressbook_container,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder viewHolder, int i) {
        Address address = mDataSet.get(i);
        viewHolder.txtName.setText(address.getfName().concat(" ").concat(address.getlName()));
        viewHolder.txtAddress.setText(address.getAddress().concat(", ").concat(address.getCity())
                .concat(", ").concat(address.getProvince()));
        if(address.isDef()){
            viewHolder.defBtnRadio.setChecked(true);
        }

        viewHolder.btnDelete.setOnClickListener(new btnDelete_onClick(address,i));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    private class btnDelete_onClick implements View.OnClickListener {
        private Address address;
        private int position;
        public btnDelete_onClick(Address address, int position) {
            this.address = address;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Address.deleteAll(Address.class,"id=?",address.getId().toString());
            viewsList.remove(position);
            mDataSet.remove(position);
            notifyItemRemoved(position);

            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(mContext);
            List<Address> addressList = Address.find(Address.class, "user_Email=?"
                    , sharedPref.getUserEmail());
            if (addressList.size()>0) {
                boolean status = false;
                for(Address tempAddress : addressList){
                    if(tempAddress.isDef()){
                        status = true;
                        break;
                    }
                }
                if(!status){
                    address = addressList.get(0);
                    address.setDef(true);
                    address.save();
                }
                for(View view : viewsList){
                    RadioButton tempBtn = view.findViewById(R.id.defBtnRadio);
                    tempBtn.setChecked(true);
                    break;
                }
            }
        }
    }
}
