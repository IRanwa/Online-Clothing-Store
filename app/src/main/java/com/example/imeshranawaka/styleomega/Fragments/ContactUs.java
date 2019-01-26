package com.example.imeshranawaka.styleomega.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment implements OnMapReadyCallback{

    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtMessage) TextView txtMessage;
    private Unbinder unbinder;

    public ContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this,view);
        //storeLocation.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latlng = new LatLng(6.9271,
                79.8612);
        CameraUpdate center= CameraUpdateFactory.newLatLngZoom(latlng,15);

        googleMap.addMarker(new MarkerOptions().position(latlng).title("Style Omega").visible(true));
        googleMap.moveCamera(center);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmit_onClick(){
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String message = txtMessage.getText().toString();
        if(name.isEmpty() || email.isEmpty() || message.isEmpty()){
            Toast.makeText(getContext(),"Fill all the details!",Toast.LENGTH_SHORT).show();
        }else{
            Intent mail = new Intent(Intent.ACTION_SEND);
            mail.putExtra(Intent.EXTRA_EMAIL,new String[]{"imeshranwa2@hotmail.com"});
            mail.putExtra(Intent.EXTRA_SUBJECT, name+" "+email);
            mail.putExtra(Intent.EXTRA_TEXT, message);
            mail.setType("message/rfc822");
            startActivity(Intent.createChooser(mail, "Send email via:"));
        }
    }
}
