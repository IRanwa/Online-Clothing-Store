package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Reviews> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public ReviewsAdapter(Context context, List<Reviews> categoryList) {
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ratingBar) RatingBar ratingBar;
        @BindView(R.id.txtRevName) TextView txtRevName;
        @BindView(R.id.txtRevDesc) TextView txtRevDesc;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.reviews_container,viewGroup,false);
        ReviewsAdapter.ViewHolder vh = new ReviewsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder viewHolder, int position) {
        Reviews review =  mDataSet.get(position);
        viewHolder.ratingBar.setRating(review.getRating());
        viewHolder.txtRevDesc.setText(review.getRevDesc());
        List<User> user = User.find(User.class, "email=?", review.getUserEmail());
        if(user.size()>0) {
            viewHolder.txtRevName.setText(user.get(0).getfName().concat(" ").concat(user.get(0).getlName()));
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
