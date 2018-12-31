package com.example.imeshranawaka.styleomega.Fragments;

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
        ((SearchView)v.findViewById(R.id.searchBar)).setOnQueryTextListener(new searchBar_onQueryListener());
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
        JSONObject json = null;
        try {
            json = new JSONObject(new loadJSONFromAsset().readJson("Category.json",getActivity().getAssets()));
            JSONArray categoryList = json.getJSONArray("categories");

            RecyclerView recycleView = v.findViewById(R.id.categoryList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recycleView.setLayoutManager(layoutManager);

            CategoryAdapter categoryAdapter = new CategoryAdapter(getFragmentManager(),getContext(), categoryList);
            recycleView.setAdapter(categoryAdapter);

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

            RecyclerView recycleView = v.findViewById(R.id.productsList);
            recycleView.setNestedScrollingEnabled(false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recycleView.setLayoutManager(layoutManager);

            ProductsAdapter productsAdapter = new ProductsAdapter(getContext(),getFragmentManager(), productsList, categories );
            recycleView.setAdapter(productsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class searchBar_onQueryListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            System.out.println(query);
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
}
