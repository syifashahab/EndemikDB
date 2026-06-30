package com.syifa.endemikdb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String PREF = "theme_pref";
    private static final String KEY = "dark";

    public static void applyTheme(Context context){

        SharedPreferences pref =
                context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        boolean dark = pref.getBoolean(KEY,false);

        if(dark){
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );
        }else{
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }

    }

    public static void toggleTheme(Context context){

        SharedPreferences pref =
                context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        boolean dark = pref.getBoolean(KEY,false);

        pref.edit()
                .putBoolean(KEY,!dark)
                .apply();

        applyTheme(context);

    }

}
