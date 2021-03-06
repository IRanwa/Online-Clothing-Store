package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imeshranawaka.styleomega.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlidingImageAdapter extends PagerAdapter {
    private List<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context, List<String> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @BindView(R.id.displayImg) ImageView image;
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.product_image, view, false);
        ButterKnife.bind(this,imageLayout);
        //ImageView image = imageLayout.findViewById(R.id.displayImg);
        Picasso.get().load(IMAGES.get(position)).fit().into(image);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
