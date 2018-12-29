package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context mContext;
    private List<JSONObject> mDataset;
    private ArrayList<View> viewList;
    private boolean searchPage;

    public ItemsAdapter(Context mContext, List<JSONObject> mDataset, boolean searchPage) {
        this.mContext = mContext;
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
        try {
            JSONObject product = mDataset.get(i);
            System.out.println(product);
            viewHolder.itemTitle.setText(product.getString("title"));
            viewHolder.price.setText("$"+String.valueOf(product.getDouble("price")));
            Picasso.get().load(product.getJSONArray("images").get(0).toString()).resize(500,500)
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        public ImageView itemImg;
        public TextView itemTitle;
        public TextView price;
        public LinearLayout container;
        public ViewHolder(@NonNull View v) {
            super(v);
            itemImg = v.findViewById(R.id.itemImg);
            itemTitle = v.findViewById(R.id.txtTitle);
            price = v.findViewById(R.id.txtPrice);
            container = v.findViewById(R.id.itemsContainer);
            viewList.add(v);
        }
    }
}
