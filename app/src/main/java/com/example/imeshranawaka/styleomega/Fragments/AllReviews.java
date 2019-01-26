package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.Adapters.OrdersAdapter;
import com.example.imeshranawaka.styleomega.Adapters.ReviewsAdapter;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllReviews extends Fragment {

    @BindView(R.id.reviewsList) RecyclerView reviewsListRecycle;
    private Unbinder unbinder;

    public AllReviews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_reviews, container, false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            setupReviews(bundle.getLong("productNo",0));
        }
        return view;
    }

    private void setupReviews(long productNo) {
        List<Reviews> reviesList = Reviews.find(Reviews.class, "prod_Id=? Order BY id desc",String.valueOf(productNo));
        if(reviesList.size()!=0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            reviewsListRecycle.setLayoutManager(layoutManager);

            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getContext(), reviesList);
            reviewsListRecycle.setAdapter(reviewsAdapter);
        }else{
            reviewsListRecycle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
