package com.example.imeshranawaka.styleomega.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Adapters.QuestionsAdapter;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllQuestions extends Fragment {

    @BindView(R.id.quesList) RecyclerView quesListRecycle;
    private Unbinder unbinder;
    private Product product;
    private QuestionsAdapter reviewsAdapter;

    public AllQuestions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_questions, container, false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            product = Product.findById(Product.class,bundle.getLong("productNo",0));
            if(product!=null) {
                setupQuestions();
            }
        }
        return view;
    }

    private void setupQuestions() {
        List<Questions> reviesList = Questions.find(Questions.class, "prod_Id=? Order BY id desc",String.valueOf(product.getId()));
        if(reviesList.size()!=0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            quesListRecycle.setLayoutManager(layoutManager);

            reviewsAdapter = new QuestionsAdapter(getContext(), reviesList);
            quesListRecycle.setAdapter(reviewsAdapter);
        }else{
            quesListRecycle.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnAskQue)
    public void btnAskQue_onClick(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ask_question_layout);

        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lWindowParams);
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SharedPreferenceUtility.getInstance(getContext()).getUserEmail();
                String txtQuestion = ((EditText)dialog.findViewById(R.id.txtQuestion)).getText().toString();

                if(txtQuestion.trim().length()>0) {
                    Questions question = new Questions(product.getId(),email,txtQuestion);
                    question.save();
                    Toast.makeText(getContext(),
                            "Question Submitted Successfully!",
                            Toast.LENGTH_LONG).show();
                    if(reviewsAdapter!=null) {
                        reviewsAdapter.addNewQuestion(question);
                        reviewsAdapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),
                            "Fill all the details!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
