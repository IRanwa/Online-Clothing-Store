package com.example.imeshranawaka.styleomega.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    private List<Questions> mDataSet;
    private Context mContext;
    private static ArrayList<View> viewsList;

    public QuestionsAdapter(Context context, List<Questions> categoryList) {
        mDataSet = categoryList;
        mContext = context;
        viewsList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtQue) TextView txtQue;
        @BindView(R.id.txtAnswer) TextView txtAnswer;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
            viewsList.add(v);
        }
    }

    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.questions_container,viewGroup,false);
        QuestionsAdapter.ViewHolder vh = new QuestionsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.ViewHolder viewHolder, int position) {
        Questions question = mDataSet.get(position);
        viewHolder.txtQue.setText(question.getQuestion());
        if(question.getAnswer()!=null){
            viewHolder.txtAnswer.setText("Question : "+question.getAnswer());
        }else{
            viewHolder.txtAnswer.setText("Not answered yet!");
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addNewQuestion(Questions question){
        mDataSet.add(0,question);
    }
}
