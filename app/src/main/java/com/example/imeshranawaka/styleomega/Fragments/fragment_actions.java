package com.example.imeshranawaka.styleomega.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.imeshranawaka.styleomega.R;

public class fragment_actions {
    private static fragment_actions instance = new fragment_actions();
    private static Fragment frag;

    private fragment_actions(){}

    public static fragment_actions getIntance(Fragment fragment) {
        frag = fragment;
        return instance;
    }

    public void btnBack_onClick() {
        frag.getFragmentManager().popBackStack();
        hideKeyboard();
    }

    public void hideKeyboard() {
        View view = frag.getView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) frag.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void btnCart_onClick(){
        FragmentManager fm = frag.getFragmentManager();
        ShoppingCart shoppingCart = new ShoppingCart();
        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, shoppingCart,"ShoppingCart");
        transaction.addToBackStack("ProductDetails");
        transaction.commit();
    }
}
