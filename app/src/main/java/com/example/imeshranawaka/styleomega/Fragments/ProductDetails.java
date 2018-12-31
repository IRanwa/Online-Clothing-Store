package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.SlidingImageAdapter;
import com.example.imeshranawaka.styleomega.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetails extends Fragment {


    public ProductDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);

        String json = getArguments().getString("product");
        try {
            JSONObject product = new JSONObject(json);
            System.out.println(product);
            ViewPager mPager = v.findViewById(R.id.imgDisplayPager);
            mPager.setAdapter(new SlidingImageAdapter(this.getContext(),product.getJSONArray("images")));

            ConstraintLayout detailsContainer = v.findViewById(R.id.detailsContainer);
            ((TextView)detailsContainer.findViewById(R.id.txtPrice)).setText("US$ ".concat(String.valueOf(product.getDouble("price"))));
            ((TextView)detailsContainer.findViewById(R.id.txtTitle)).setText(product.getString("title"));
            ((TextView)detailsContainer.findViewById(R.id.txtDesc))
                    .setText(Html.fromHtml(product.getString("description")));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

}
