package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Fragments.PlaceOrder;
import com.example.imeshranawaka.styleomega.Fragments.ShoppingCart;
import com.example.imeshranawaka.styleomega.Fragments.fragment_actions;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private List<Order_Product> mDataSet;
    private List<CheckBox> checkboxList;
    private List<btnDelete_onClick> btnDeleteList;
    private Context mContext;
    private FragmentActivity mActivity;
    private static ArrayList<View> viewsList;

    public CartAdapter(Context context, List<Order_Product> productsList, FragmentActivity activity) {
        mDataSet = productsList;
        mContext = context;
        viewsList = new ArrayList<>();
        checkboxList = new ArrayList<>();
        btnDeleteList = new ArrayList<>();
        mActivity = activity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.prodImg) ImageView prodImg;
        @BindView(R.id.txtProductTitle) TextView prodTitle;
        @BindView(R.id.txtPrice) TextView prodPrice;
        @BindView(R.id.noOfQuantity) Spinner prodQty;
        @BindView(R.id.checkBox)CheckBox checkbox;
        @BindView(R.id.btnDelete) ImageView btnDelete;
        @BindView(R.id.btnUpdate) ImageView btnUpdate;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }


    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cart_products_container,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder viewHolder, int i) {
        Order_Product orderProduct = mDataSet.get(i);
        Product product = Product.findById(Product.class, orderProduct.getProdId());

        Display display = mActivity.getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;
        int height = size. y;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width/4;
        layoutParams.width = width/4;

        viewHolder.prodImg.setLayoutParams(layoutParams);

        Picasso.get().load(product.getImages().get(0)).fit().into(viewHolder.prodImg);
        viewHolder.prodTitle.setText(product.getTitle());
        viewHolder.prodPrice.setText(String.valueOf(product.getPrice()));

        int totalQty = product.getQuantity();
        ArrayList<Integer> qtyList = new ArrayList<>();
        for(int count=1;count<=totalQty;count++){
            qtyList.add(count);
        }
        ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_view,qtyList);
        qtyAdapter.setDropDownViewResource(R.layout.spinner_item_view);
        viewHolder.prodQty.setAdapter(qtyAdapter);

        int orderProdQty = orderProduct.getQuantity();
        if(orderProdQty>totalQty){
            viewHolder.prodQty.setSelection(totalQty-1);
        }else{
            viewHolder.prodQty.setSelection(orderProdQty-1);
        }

        viewHolder.prodQty.setOnItemSelectedListener(new QtyChangeListener(orderProduct));
        checkboxList.add(viewHolder.checkbox);
        viewHolder.checkbox.setOnClickListener(new checkbox_onClick());

        btnDelete_onClick deleteAction = new btnDelete_onClick(orderProduct, i);
        btnDeleteList.add(deleteAction);
        viewHolder.btnDelete.setOnClickListener(deleteAction);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    private class QtyChangeListener implements AdapterView.OnItemSelectedListener {
        private Order_Product orderProduct;
        public QtyChangeListener(Order_Product orderProduct) {
            this.orderProduct = orderProduct;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int itemQty = Integer.parseInt(parent.getSelectedItem().toString());
            orderProduct.setQuantity(itemQty);
            orderProduct.save();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void checkboxall_onClick(boolean status){
        for(CheckBox checkbox : checkboxList){
            checkbox.setChecked(status);
        }
    }

    private class checkbox_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            boolean status = true;
            for(CheckBox checkbox : checkboxList){
                if(!checkbox.isChecked()){
                    status = false;
                    break;
                }
            }
            if(!status){
                ((CheckBox)mActivity.findViewById(R.id.checkboxAll)).setChecked(status);
            }else{
                ((CheckBox)mActivity.findViewById(R.id.checkboxAll)).setChecked(status);
            }
        }
    }

    private class btnDelete_onClick implements View.OnClickListener {
        private Order_Product orderProduct;
        private int position;
        public btnDelete_onClick(Order_Product orderProduct, int position) {
            this.orderProduct = orderProduct;
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            deleteItem(position,orderProduct);
        }
    }

    private void deleteItem(int position,Order_Product orderProduct){
        long id = orderProduct.getOrderNo();
        orderProduct.delete();
        notifyItemRemoved(position);
        mDataSet.remove(position);
        checkboxList.remove(position);
        btnDeleteList.remove(position);

        for(int count=position;count<btnDeleteList.size();count++){
            btnDeleteList.get(position).setPosition(position);
        }

        if(mDataSet.size()==0){
            Orders order = Orders.findById(Orders.class,id);
            order.delete();

            Fragment shoppingCart = mActivity.getSupportFragmentManager().findFragmentByTag("ShoppingCart");
            if(shoppingCart!=null) {
                fragment_actions.getIntance(shoppingCart).btnBack_onClick();
            }
        }
    }

    public void btnDeleteSelected_onClick(View v){
        int checkboxSize = checkboxList.size();
        for(int count=0;count<checkboxSize;count++){
            if(checkboxList.get(0).isChecked()){
                btnDeleteList.get(0).onClick(v);
            }
        }
    }

    public void btnCheckout_onClick(boolean statusAll){
        List<Order_Product> orderProdList = mDataSet;

        Bundle bundle = new Bundle();
        boolean itemsSelected = false;
        if(!statusAll){
            orderProdList = new ArrayList<>();;
            for (int count=0;count<checkboxList.size();count++) {
                if (checkboxList.get(count).isChecked()) {
                    orderProdList.add(mDataSet.get(count));
                    itemsSelected = true;
                }
            }
            bundle.putSerializable("order_products", (Serializable) orderProdList);
        }else{
            bundle.putLong("orderNo",mDataSet.get(0).getOrderNo());
            itemsSelected = true;
        }
        bundle.putBoolean("checkAll", statusAll);

        if(itemsSelected) {
            FragmentManager fm = mActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            PlaceOrder placeOrder = new PlaceOrder();
            placeOrder.setArguments(bundle);

            transaction.replace(R.id.mainFragment, placeOrder,"PlaceOrder");
            transaction.addToBackStack("ShoppingCart");
            transaction.commit();
        }else{
            Toast.makeText(mContext,"No Items Selected!",Toast.LENGTH_SHORT).show();
        }
    }
}
