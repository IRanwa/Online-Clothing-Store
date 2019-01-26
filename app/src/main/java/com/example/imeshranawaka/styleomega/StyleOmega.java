package com.example.imeshranawaka.styleomega;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Fragments.ContactUs;
import com.example.imeshranawaka.styleomega.Fragments.MainMenu;
import com.example.imeshranawaka.styleomega.Fragments.MyAccount;
import com.example.imeshranawaka.styleomega.Fragments.MyOrders;
import com.example.imeshranawaka.styleomega.Fragments.SignIn;
import com.example.imeshranawaka.styleomega.Fragments.fragment_actions;
import com.example.imeshranawaka.styleomega.Models.Category;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StyleOmega extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer_layout;
    @BindView(R.id.nav_view) NavigationView nav_view;

    private Unbinder unbinder;
    private static List<Fragment> fragList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_omega);
        unbinder = ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        nav_view.setEnabled(true);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (findViewById(R.id.mainFragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            String email = SharedPreferenceUtility.getInstance(this).getUserEmail();
            if(email.isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainFragment, new SignIn(), "SignIn").
                        commit();
            }else{
                SharedPreferenceUtility shraed = SharedPreferenceUtility.getInstance(this);
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainFragment, new MainMenu(), "MainMenu").
                        commit();
            }
        }
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        fragList.add(fragment);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            Fragment fragment = getSupportFragmentManager().findFragmentByTag("AccountInfo");
            if(fragment != null) {
                fragment_actions.getIntance(fragment).btnBack_onClick();
            }

            fragment = getSupportFragmentManager().findFragmentByTag("ChangePassword");
            if(fragment != null) {
                fragment_actions.getIntance(fragment).btnBack_onClick();
            }

            fragment = getSupportFragmentManager().findFragmentByTag("MyAddressBook");
            if(fragment != null) {
                fragment_actions.getIntance(fragment).btnBack_onClick();
            }

            fragment = getSupportFragmentManager().findFragmentByTag("NewAddress");
            if(fragment != null) {
                fragment_actions.getIntance(fragment).btnBack_onClick();
            }

            fragment = getSupportFragmentManager().findFragmentByTag("MyAccount");
            if(fragment != null) {
                fragment_actions.getIntance(fragment).btnBack_onClick();
            }

            transaction.replace(R.id.mainFragment, new MyAccount(), "MyAccount");
            transaction.addToBackStack("MainMenu");
            transaction.commit();
        } else if (id == R.id.nav_order) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFragment, new MyOrders(), "MyOrders");
            transaction.addToBackStack("MainMenu");
            transaction.commit();
        }else if(id == R.id.nav_contact){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFragment, new ContactUs(), "ContactUs");
            transaction.addToBackStack("MainMenu");
            transaction.commit();
        }else if(id == R.id.nav_logout){
            SharedPreferences.Editor editor = SharedPreferenceUtility.getInstance(this).getEditor();
            editor.remove("email");
            editor.remove("user");
            editor.remove("pass");
            editor.commit();


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFragment, new SignIn(), "SignIn");
            transaction.commit();
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
