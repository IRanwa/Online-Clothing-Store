package com.example.imeshranawaka.styleomega.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.imeshranawaka.styleomega.Adapters.CategoryAdapter;
import com.example.imeshranawaka.styleomega.Adapters.ProductsAdapter;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.loadJSONFromAsset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class MainMenu extends Fragment {
    @BindView(R.id.categoryList) RecyclerView catRecyclerView;
    @BindView(R.id.productsList) RecyclerView prodRecyclerView;
    private Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        unbind = ButterKnife.bind(this,v);
        enableDrawer();

        SharedPreferences prefs = getContext().getSharedPreferences("login", MODE_PRIVATE);
        //String restoredText = prefs.getString("text", null);
        if (prefs != null) {
            setNavHeader(prefs.getString("email", ""));
        }
        setCategoryList(v);
        //v.findViewById(R.id.btnSideMenu).setOnClickListener(new btnSideMenu_onClick());
        ((SearchView)v.findViewById(R.id.searchBar)).setOnQueryTextListener(new searchBar_onQueryListener());
        return v;
    }

    @OnClick(R.id.btnSideMenu)
    public void btnSideMenu_onClick(){
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.START);
    }

    private void enableDrawer(){
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
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
        JSONObject json = null;
        try {
            json = new JSONObject(new loadJSONFromAsset().readJson("Category.json",getActivity().getAssets()));
            JSONArray categoryList = json.getJSONArray("categories");

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            catRecyclerView.setLayoutManager(layoutManager);

            CategoryAdapter categoryAdapter = new CategoryAdapter(getFragmentManager(),getContext(), categoryList);
            catRecyclerView.setAdapter(categoryAdapter);

            setProductsList(categoryList,v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setProductsList(JSONArray categories,View v){
        JSONObject json = null;
        try {

            json = new JSONObject(new loadJSONFromAsset().readJson("Products.json",getActivity().getAssets()));
            JSONArray productsList = json.getJSONArray("products");

            prodRecyclerView.setNestedScrollingEnabled(false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            prodRecyclerView.setLayoutManager(layoutManager);

            ProductsAdapter productsAdapter = new ProductsAdapter(getContext(),getFragmentManager(), productsList, categories );
            prodRecyclerView.setAdapter(productsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class searchBar_onQueryListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            try {
                JSONObject json = new JSONObject(new loadJSONFromAsset().readJson("Products.json", getActivity().getAssets()));
                JSONArray prodList = json.getJSONArray("products");
                JSONArray productsList = new JSONArray();

                for(int count = 0; count<prodList.length();count++){
                    JSONObject obj = prodList.getJSONObject(count);
                    if(obj.has("title") && obj.getString("title").toLowerCase().contains(query.toLowerCase())){
                        productsList.put(obj);
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("products", productsList.toString());
                if(query.length()>25) {
                    query = query.substring(0, 25);
                }
                bundle.putString("title", query);

                SearchProducts searchProd = new SearchProducts();
                searchProd.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction().add(R.id.mainFragment, searchProd,"SearchProducts");
                transaction.addToBackStack("MainMenu");
                transaction.commit();

                new fragment_actions(MainMenu.this).hideKeyboard();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
