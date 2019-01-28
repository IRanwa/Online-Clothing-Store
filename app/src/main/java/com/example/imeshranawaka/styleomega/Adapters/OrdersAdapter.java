package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Fragments.OrderDetails;
import com.example.imeshranawaka.styleomega.Fragments.ProductDetails;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context mContext;
    private List<Orders> mDataset;
    private ArrayList<View> viewList;
    private FragmentActivity activity;

    public OrdersAdapter(Context mContext, List<Orders> mDataset, FragmentActivity activity) {
        this.mContext = mContext;
        this.mDataset = mDataset;
        this.viewList = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_container, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Orders order = mDataset.get(i);
        viewHolder.txtOrderNo.setText("Order No : "+order.getId().toString());
        viewHolder.txtOrderDate.setText("Order Date : "+ DateFormat.getDateInstance(DateFormat.MEDIUM).format(order.getPurchasedDate()));
        viewHolder.txtStatus.setText("Order Status : "+order.getOrderStatus());

        List<Order_Product> orderProductList = Order_Product.find(Order_Product.class, "order_No=?", order.getId().toString());

        double price=0;
        for(int count=0;count<orderProductList.size();count++){
            Order_Product orderProduct = orderProductList.get(count);
            Product product = Product.findById(Product.class,orderProduct.getProdId());
            price += product.getPrice() * orderProduct.getQuantity();

            if(count==0){
                Display display = activity.getWindowManager(). getDefaultDisplay();
                Point size = new Point();
                display. getSize(size);
                int width = size.y;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.height = width/6;
                layoutParams.width = width/6;

                viewHolder.dislayImage.setLayoutParams(layoutParams);

                Picasso.get().load(product.getImages().get(0)).resize(300,300)
                        .transform(new RoundedTransformation(75,0)).into(viewHolder.dislayImage);
                viewHolder.txtProductTitle.setText(product.getTitle());
            }
        }
        viewHolder.txtTotalPrice.setText("Total Price : US$."+price);
        viewHolder.orderContainer.setOnClickListener(new order_onClick(order.getId()));
    }

    public class RoundedTransformation implements com.squareup.picasso.Transformation {
        private final int radius;
        private final int margin;

        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin,
                    source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "rounded(r=" + radius + ", m=" + margin + ")";
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.orderContainer) LinearLayout orderContainer;
        @BindView(R.id.txtOrderNo) TextView txtOrderNo;
        @BindView(R.id.txtOrderDate) TextView txtOrderDate;
        @BindView(R.id.txtStatus) TextView txtStatus;
        @BindView(R.id.displayImage) ImageView dislayImage;
        @BindView(R.id.txtProductTitle) TextView txtProductTitle;
        @BindView(R.id.txtTotalPrice) TextView txtTotalPrice;
        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
            viewList.add(v);
        }
    }

    private class order_onClick implements View.OnClickListener {
        private long id ;
        public order_onClick(Long id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putLong("OrderNo",id);
            OrderDetails orderdetails = new OrderDetails();
            orderdetails.setArguments(bundle);

            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment,orderdetails , "OrderDetails");
            transaction.addToBackStack("MyOrders");
            transaction.commit();
        }
    }
}

