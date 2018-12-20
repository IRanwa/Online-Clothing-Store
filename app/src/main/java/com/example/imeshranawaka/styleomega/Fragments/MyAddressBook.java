package com.example.imeshranawaka.styleomega.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeshranawaka.styleomega.Adapters.AddressAdapter;
import com.example.imeshranawaka.styleomega.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressBook extends Fragment {


    public MyAddressBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_address_book, container, false);
        setList(v);
        return v;
    }

    private void setList(View v) {
        /*ListView listview = (ListView) v.findViewById(R.id.addList);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(new AddressAdapter(v.getContext(), new String[] { "data1",
                "data2","data2","data2","data2","data2","data2","data2","data2","data2"}));*/

        // Initialize a new String array
        final String[] animals = {
                "Aardvark",
                "Albatross",
                "Alligator",
                "Alpaca",
                "Ant",
                "Anteater",
                "Antelope",
                "Ape",
                "Armadillo",
                "Donkey",
                "Baboon",
                "Badger",
                "Barracuda",
                "Bear",
                "Beaver",
                "Bee"
        };

        // Intilize an array list from array
        final List<String> animalsList = new ArrayList(Arrays.asList(animals));
        
        RecyclerView recycleView = v.findViewById(R.id.addressList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);

        AddressAdapter addressAdapter = new AddressAdapter(getContext(), animalsList);
        recycleView.setAdapter(addressAdapter);
    }

}
