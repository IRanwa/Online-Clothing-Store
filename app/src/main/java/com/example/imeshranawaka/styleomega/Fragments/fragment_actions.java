package com.example.imeshranawaka.styleomega.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

class fragment_actions implements View.OnClickListener {
    private Fragment frag;
    public fragment_actions(Fragment frag) {
        this.frag = frag;
    }

    @Override
    public void onClick(View v) {
        frag.getFragmentManager().popBackStack();
        hideKeyboard();
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = frag.getView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) frag.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
