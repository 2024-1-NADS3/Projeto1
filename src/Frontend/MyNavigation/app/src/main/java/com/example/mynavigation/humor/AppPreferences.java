package com.example.mynavigation.humor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mynavigation.R;

public class AppPreferences {
    private int statusUsuario = 2;
    private String statusTela;
    private static final String PREFS_NAME = "MyAppPrefs"; // Nome do arquivo de preferências
    private static final String KEY_SELECTED_OPTION = "selected_option"; // Chave para a opção selecionada

    private static AppPreferences instance;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context.getApplicationContext());
        }
        return instance;
    }

    public void setSelectedOption(String option) {
        sharedPreferences.edit().putString(KEY_SELECTED_OPTION, option).apply();
    }

    public String getSelectedOption() {

        if (statusUsuario == 0){
            statusTela = "mal";
        }
        else if (statusUsuario == 1) {
            statusTela = "normal";
        }
        else {
            statusTela = "feliz";
        }

        return sharedPreferences.getString(KEY_SELECTED_OPTION, "feliz");
    }
}

