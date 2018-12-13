package com.example.imeshranawaka.styleomega.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;

class btnBack_onClick implements View.OnClickListener {
    private Fragment frag;
    public btnBack_onClick(Fragment frag) {
        this.frag = frag;
    }

    @Override
    public void onClick(View v) {
        frag.getFragmentManager().popBackStack();
    }
}
