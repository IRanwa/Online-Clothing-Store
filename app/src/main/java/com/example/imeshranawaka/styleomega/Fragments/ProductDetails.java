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
import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetails extends Fragment {

    @BindView(R.id.imgDisplayPager) ViewPager imgDisplayPager;
    @BindView(R.id.detailsContainer) ConstraintLayout detailsContainer;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtPrice) TextView txtPrice;
    @BindView(R.id.txtDesc) TextView txtDesc;
    @BindView(R.id.txtRevName) TextView txtRevName;
    @BindView(R.id.txtRevDesc) TextView txtRevDesc;
    @BindView(R.id.txtQuestion) TextView txtQuestion;
    @BindView(R.id.txtAnswer) TextView txtAnswer;
    private Unbinder unbinder;

    public ProductDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this,v);
        String json = getArguments().getString("product");
        try {
            JSONObject product = new JSONObject(json);
            //System.out.println(product);
            //ViewPager mPager = v.findViewById(R.id.imgDisplayPager);
            imgDisplayPager.setAdapter(new SlidingImageAdapter(this.getContext(),product.getJSONArray("images")));

            //ConstraintLayout detailsContainer = v.findViewById(R.id.detailsContainer);
            txtPrice.setText("US$ ".concat(String.valueOf(product.getDouble("price"))));
            txtTitle.setText(product.getString("title"));
            txtDesc.setText(Html.fromHtml(product.getString("description")));


            List<Reviews> reviews = Reviews.find(Reviews.class, "prod_ID=?", product.getString("id"));
            List<Questions> questions = Questions.find(Questions.class, "prod_ID=?", product.getString("id"));

            if(reviews.size()==0){
                txtRevName.setText("No Reviews Found!");
                txtRevDesc.setVisibility(View.GONE);
            }else{
                Reviews rev = reviews.get(0);
                List<User> user = User.find(User.class, "email=?", rev.getUser_Email());
                txtRevName.setText(user.get(0).getfName().concat(" ").concat(user.get(0).getlName()));
                txtRevDesc.setText(rev.getRev_Desc());
            }

            if(questions.size()==0){
                txtQuestion.setText("No Questions Found!");
                txtAnswer.setVisibility(View.GONE);
            }else{
                Questions que = questions.get(0);
                txtQuestion.setText(que.getQuestion());
                txtAnswer.setText(que.getAnswer());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
