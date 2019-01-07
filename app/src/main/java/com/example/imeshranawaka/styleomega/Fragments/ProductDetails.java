package com.example.imeshranawaka.styleomega.Fragments;


import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.SlidingImageAdapter;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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
    @BindView(R.id.quantity) Spinner quantity;
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
        Product product = (Product) getArguments().getSerializable("product");

        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width;

        imgDisplayPager.setLayoutParams(layoutParams);


            imgDisplayPager.setAdapter(new SlidingImageAdapter(this.getContext(),product.getImages()));
imgDisplayPager.setMinimumHeight(width);
            //ConstraintLayout detailsContainer = v.findViewById(R.id.detailsContainer);
            txtPrice.setText("US$ ".concat(String.valueOf(product.getPrice())));
            txtTitle.setText(product.getTitle());

            int prodQty = product.getQuantity();
            ArrayList<Integer> qtyList = new ArrayList<>();
            for(int count=1;count<=prodQty;count++){
                qtyList.add(count);
            }
            ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,qtyList);
            quantity.setAdapter(qtyAdapter);

            txtDesc.setText(Html.fromHtml(product.getDesc()));


            List<Reviews> reviews = Reviews.find(Reviews.class, "prod_ID=?", product.getId().toString());
            List<Questions> questions = Questions.find(Questions.class, "prod_ID=?", product.getId().toString());

            if(reviews.size()==0){
                txtRevName.setText("No Reviews Found!");
                txtRevDesc.setVisibility(View.GONE);
            }else{
                Reviews rev = reviews.get(0);
                List<User> user = User.find(User.class, "email=?", rev.getUserEmail());
                txtRevName.setText(user.get(0).getfName().concat(" ").concat(user.get(0).getlName()));
                txtRevDesc.setText(rev.getRevDesc());
            }

            if(questions.size()==0){
                txtQuestion.setText("No Questions Found!");
                txtAnswer.setVisibility(View.GONE);
            }else{
                Questions que = questions.get(0);
                txtQuestion.setText(que.getQuestion());
                txtAnswer.setText(que.getAnswer());
            }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
