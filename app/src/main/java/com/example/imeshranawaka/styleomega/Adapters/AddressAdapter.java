package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private List<String> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public AddressAdapter(Context context, List<String> addressList) {
        mDataSet = addressList;
        mContext = context;
        viewsList = new ArrayList<>();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public @BindView(R.id.txtName) TextView mTextView;
        public @BindView(R.id.defBtnRadio) RadioButton defBtnRadio;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }

        @OnClick(R.id.defBtnRadio)
        public void defBtnRadio_onClick(){
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
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText((String)mDataSet.get(i));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
