package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private List<Order_Product> mDataSet;
    private Context mContext;
    private FragmentActivity mActivity;
    private static ArrayList<View> viewsList;

    public CartAdapter(Context context, List<Order_Product> productsList, FragmentActivity activity) {
        mDataSet = productsList;
        mContext = context;
        viewsList = new ArrayList<>();
        mActivity = activity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.prodImg) ImageView prodImg;
        @BindView(R.id.txtProductTitle) TextView prodTitle;
        @BindView(R.id.txtPrice) TextView prodPrice;
        @BindView(R.id.noOfQuantity)
        Spinner prodQty;
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

        layoutParams.height = height/6;
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
        ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_item,qtyList);
        viewHolder.prodQty.setAdapter(qtyAdapter);

        int orderProdQty = orderProduct.getQuantity();
        if(orderProdQty>totalQty){
            viewHolder.prodQty.setSelection(totalQty-1);
        }else{
            viewHolder.prodQty.setSelection(orderProdQty-1);
        }

        viewHolder.prodQty.setOnItemSelectedListener(new QtyChangeListener(orderProduct));

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
}
