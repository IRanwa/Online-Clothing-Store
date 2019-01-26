package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Fragments.ProductDetails;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context mContext;
    private final FragmentManager fm;
    private List<Product> mDataset;
    private ArrayList<View> viewList;
    private boolean searchPage;

    public ItemsAdapter(Context mContext, FragmentManager fm, List<Product> mDataset, boolean searchPage) {
        this.mContext = mContext;
        this.fm = fm;
        this.mDataset = mDataset;
        this.viewList = new ArrayList<>();
        this.searchPage = searchPage;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.items_container, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Product product = mDataset.get(i);
        System.out.println(product);
            viewHolder.itemTitle.setText(product.getTitle());
            viewHolder.price.setText("$"+product.getPrice());

            Picasso.get().load(product.getImages().get(0)).resize(500,500)
                    .transform(new RoundedTransformation(75, 0)).into(viewHolder.itemImg);

        if(searchPage){
                viewHolder.container.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.button_layout));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 10, 10, 10);
                viewHolder.container.setLayoutParams(params);
            }
            viewHolder.container.setOnClickListener(new Product_onClick(product));

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
        @BindView(R.id.itemImg) ImageView itemImg;
        @BindView(R.id.txtTitle) TextView itemTitle;
        @BindView(R.id.txtPrice) TextView price;
        @BindView(R.id.itemsContainer) LinearLayout container;
        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
            viewList.add(v);
        }
    }

    private class Product_onClick implements View.OnClickListener {
        private Product product;
        public Product_onClick(Product product) {
            this.product = product;
        }

        @Override
        public void onClick(View v) {
            ProductDetails prodDetails = new ProductDetails();
            Bundle bundle = new Bundle();
            bundle.putLong("prodNo",product.getId());
            prodDetails.setArguments(bundle);

            FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, prodDetails,"ProductDetails");
            transaction.addToBackStack("MainMenu");
            transaction.commit();
        }
    }
}
