package com.example.medicare;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager{

    private  static SharedPrefManager mInstance;
    private static Context mCtx;

    private static  final String SHARED_PREF_NAME = "sharedPrefName12";
    private static  final String KEY_ID  = "mediaclId";
   //    private static  final String KEY_PASSWORD  = "password";

    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public  static synchronized  SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(String id ){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ID, id);
        //editor.putString(KEY_PASSWORD, password);

        editor.apply();
        return true;
    }
    public  boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ID, null) != null){
            return  true;
        }
        return false;
    }
    public boolean isLoggedOut(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        return true;
    }
}
