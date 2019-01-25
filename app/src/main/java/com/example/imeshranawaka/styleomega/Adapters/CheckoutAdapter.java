package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {
    private List<Order_Product> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;
    private FragmentActivity mActivity;

    public CheckoutAdapter(Context context, List<Order_Product> categoryList, FragmentActivity activity) {
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
        this.mActivity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.productImage) ImageView productImage;
        @BindView(R.id.prodTitle) TextView prodTitle;
        @BindView(R.id.prodPrice) TextView prodPrice;
        @BindView(R.id.prodQuantity) TextView prodQuantity;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }

    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.checkout_product_container,viewGroup,false);
        CheckoutAdapter.ViewHolder vh = new CheckoutAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder viewHolder, int position) {
        Order_Product orderProduct =  mDataSet.get(position);
        Product product = Product.findById(Product.class, orderProduct.getProdId());

        viewHolder.prodTitle.setText(product.getTitle());
        viewHolder.prodPrice.setText("Price : US$"+String.valueOf(product.getPrice()));
        viewHolder.prodQuantity.setText("Quantitiy : ".concat(String.valueOf(orderProduct.getQuantity())));

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

        viewHolder.productImage.setLayoutParams(layoutParams);

        Picasso.get().load(product.getImages().get(0)).fit().into(viewHolder.productImage);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}