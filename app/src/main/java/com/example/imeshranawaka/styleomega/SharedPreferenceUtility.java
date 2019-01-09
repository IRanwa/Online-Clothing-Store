package com.example.imeshranawaka.styleomega;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtility {
    private static SharedPreferenceUtility instance = new SharedPreferenceUtility();
    private static Context mContext;

    private SharedPreferenceUtility(){}

    public static SharedPreferenceUtility getInstance(Context context){
        mContext = context;
        return instance;
    }

    private SharedPreferences.Editor getEditor(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPref(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public void setUserId(long id){
        SharedPreferences.Editor editor = getEditor();
        editor.putLong("user",id);
        editor.apply();
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("email",email);
        editor.apply();
    }

    public void setUserPass(String pass){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("pass",pass);
        editor.apply();
    }

    public long getUserId(){
        SharedPreferences pref = getSharedPref();
        return pref.getLong("user",0);
    }

    public String getUserEmail(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("email","");
    }

    public String getUserPass(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("pass","");
    }
}
