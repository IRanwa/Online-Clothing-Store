package com.example.imeshranawaka.styleomega.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Login;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyAccount extends Fragment {
    @BindView(R.id.txtUserName) TextView txtUserName;
    @BindView(R.id.txtEmail) TextView txtEmail;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        String email = sharedPref.getUserEmail();
        User user = User.find(User.class,"email=?",email).get(0);

        txtUserName.setText(user.getfName()+" "+user.getlName());
        txtEmail.setText(email);
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @OnClick(R.id.btnAccInfo)
    public void btnAccInfo_onClick(){
        AccountInfo accInfo = new AccountInfo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,accInfo,"AccountInfo");
        transaction.addToBackStack("MyAccount");

        transaction.commit();
    }

    @OnClick(R.id.btnChangePass)
    public void btnChangePass_onClick(){
        ChangePassword changePass = new ChangePassword();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,changePass,"ChangePassword");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @OnClick(R.id.btnAddBook)
    public void btnAddBook_onClick(){
        MyAddressBook add = new MyAddressBook();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment,add,"MyAddressBook");
        transaction.addToBackStack("MyAccount");
        transaction.commit();
    }

    @OnClick(R.id.btnDelAcc)
    public void btnDelAcc(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Confirmation Message");
        dialog.setContentView(R.layout.delete_message_container);

        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lWindowParams);
        dialog.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long Id = SharedPreferenceUtility.getInstance(getContext()).getUserId();
                String email = SharedPreferenceUtility.getInstance(getContext()).getUserEmail();
                SharedPreferences.Editor editor = SharedPreferenceUtility.getInstance(getContext()).getEditor();
                editor.remove("email");
                editor.remove("user");
                editor.remove("pass");
                editor.commit();

                Login login = Login.findById(Login.class,Id);
                if(login!=null){
                    login.delete();
                }
                List<User> users = User.find(User.class, "email=?", email);
                for(User user : users){
                    user.delete();
                }
                List<Address> addressesList = Address.find(Address.class, "user_Email=?", email);
                for(Address address : addressesList){
                    address.delete();
                }
                List<Orders> ordersList = Orders.find(Orders.class, "user_Email=?", email);
                for(Orders order : ordersList){
                    List<Order_Product> orderProductList = Order_Product.find(Order_Product.class, "order_No=?"
                            , order.getId().toString());
                    for(Order_Product orderProd : orderProductList){
                        orderProd.delete();
                    }
                    order.delete();
                }
                List<Questions> questionsList = Questions.find(Questions.class, "user_Email=?", email);
                for(Questions questions : questionsList){
                    questions.delete();
                }
                List<Reviews> reviewsList = Reviews.find(Reviews.class, "user_Email=?", email);
                for(Reviews reviews : reviewsList){
                    reviews.delete();
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                int backStackEntry = fm.getBackStackEntryCount();
                List<Fragment> fragments = fm.getFragments();
                if (backStackEntry > 0) {
                    for (int i = 0; i < backStackEntry; i++) {
                        fm.popBackStackImmediate();
                        if(fragments.size()<i) {
                            Fragment frag = fragments.get(0);
                            transaction.remove(frag);
                        }
                        fragments = fm.getFragments();
                    }
                }
                transaction.
                        replace(R.id.mainFragment, new SignIn(), "SignIn").
                        commit();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
