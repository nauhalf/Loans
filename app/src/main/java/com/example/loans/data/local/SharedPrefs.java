package com.example.loans.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loans.data.remote.model.Loan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPrefs {

    private Context context;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public final static String PREFS_NAME = "com.example.loans.data.local";
    public final static String KEY_LOANS = PREFS_NAME + "LOANS";


    public List<Loan> getLoansList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Loan>>() {
        }.getType();
        List<Loan> loans = gson.fromJson(getStr(KEY_LOANS), type);
        return loans;
    }

    private void setInt(String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(key, 0);
    }

    private void setStr(String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key, "DNF");
    }

    private void setBool(String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(key, false);
    }

    public boolean sharedPreferenceExist(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.contains(key);
    }

    public void setLoansList(List<Loan> list) {
        String loansAsJson = new Gson().toJson(list);
        setStr(KEY_LOANS, loansAsJson);
    }
}
