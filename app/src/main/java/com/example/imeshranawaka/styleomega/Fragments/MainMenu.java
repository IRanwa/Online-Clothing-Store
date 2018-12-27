package com.example.imeshranawaka.styleomega.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.CategoryAdapter;
import com.example.imeshranawaka.styleomega.Adapters.ProductsAdapter;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arg = getArguments();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        enableDrawer();
        setNavHeader(arg.getString("email"));
        setCategoryList(v);
        v.findViewById(R.id.btnSideMenu).setOnClickListener(new btnSideMenu_onClick());
        return v;
    }


    private class btnSideMenu_onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.START);
        }
    }

    private void enableDrawer(){
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void setNavHeader(String loginEmail){
        TextView name = getActivity().findViewById(R.id.txtName);
        TextView email = getActivity().findViewById(R.id.txtHeadEmail);

        User user = User.find(User.class, "email=?", loginEmail).get(0);
        name.setText(user.getfName()+" "+user.getlName());
        email.setText(loginEmail);

    }

    private void setCategoryList(View v){

        /*final String[] animals = {
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

        RecyclerView recycleView = v.findViewById(R.id.categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapte = new CategoryAdapter(getContext(), animalsList);
        recycleView.setAdapter(categoryAdapte);*/
        JSONObject json = null;
        try {
            json = new JSONObject(loadJSONFromAsset("Category.json"));
            JSONArray categoryList = json.getJSONArray("categories");

            RecyclerView recycleView = v.findViewById(R.id.categoryList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recycleView.setLayoutManager(layoutManager);

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
            recycleView.setAdapter(categoryAdapter);

            setProductsList(categoryList,v);
            /*RecyclerView rcView = v.findViewById(R.id.productsList);
            rcView.setNestedScrollingEnabled(false);
            LinearLayoutManager layoutMg = new LinearLayoutManager(getContext());
            rcView.setLayoutManager(layoutMg);

            CategoryAdapter productsAdapter = new CategoryAdapter(getContext(), categoryList);
            rcView.setAdapter(productsAdapter);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setProductsList(JSONArray categories,View v){
        JSONObject json = null;
        try {
            json = new JSONObject(loadJSONFromAsset("Products.json"));
            JSONArray productsList = json.getJSONArray("products");

            RecyclerView recycleView = v.findViewById(R.id.productsList);
            recycleView.setNestedScrollingEnabled(false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recycleView.setLayoutManager(layoutManager);

            ProductsAdapter productsAdapter = new ProductsAdapter(getContext(), productsList, categories );
            recycleView.setAdapter(productsAdapter);

            /*RecyclerView rcView = v.findViewById(R.id.productsList);
            rcView.setNestedScrollingEnabled(false);
            LinearLayoutManager layoutMg = new LinearLayoutManager(getContext());
            rcView.setLayoutManager(layoutMg);

            CategoryAdapter productsAdapter = new CategoryAdapter(getContext(), categoryList);
            rcView.setAdapter(productsAdapter);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
