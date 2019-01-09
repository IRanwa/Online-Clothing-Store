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
import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;
import com.example.imeshranawaka.styleomega.loadJSONFromAsset;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class MainMenu extends Fragment {
    @BindView(R.id.categoryList) RecyclerView catRecyclerView;
    @BindView(R.id.productsList) RecyclerView prodRecyclerView;
    @BindView(R.id.searchBar) SearchView searchBar;
    private Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        unbind = ButterKnife.bind(this,v);
        enableDrawer();


        setCategoryList(v);
        searchBar.setOnQueryTextListener(new searchBar_onQueryListener());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        if (sharedPref != null) {
            setNavHeader(sharedPref.getUserEmail());
        }
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

        List<Category> categoryList = Category.listAll(Category.class);
        System.out.println("category List : "+categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        catRecyclerView.setLayoutManager(layoutManager);
        //List<Iterator<Category>> categoryList = (Category.findAll(Category.class));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getFragmentManager(),getContext(), categoryList);
        catRecyclerView.setAdapter(categoryAdapter);

        setProductsList(categoryList);
    }

    private void setProductsList(List<Category> categories){
            prodRecyclerView.setNestedScrollingEnabled(false);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            prodRecyclerView.setLayoutManager(layoutManager);

            ProductsAdapter productsAdapter = new ProductsAdapter(getContext(),getFragmentManager(), categories );
            prodRecyclerView.setAdapter(productsAdapter);
    }

    private class searchBar_onQueryListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
               List<Product> tempList = Product.listAll(Product.class);
            ArrayList<Product> productsList = new ArrayList<>();
            for(Product prod : tempList){
                if(prod.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    productsList.add(prod);
                }
            }

                Bundle bundle = new Bundle();
                bundle.putSerializable("products",  productsList);
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
