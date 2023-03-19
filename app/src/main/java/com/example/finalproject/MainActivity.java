package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_LANGUAGE = "language";
    private static final String LANG_EN = "en";
    private static final String LANG_RU = "ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String lang = getLangFromPref();

        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang));
        res.updateConfiguration(conf, res.getDisplayMetrics());

        setContentView(R.layout.activity_main);
    }

    public void openFactsActivity(View view) {
        Intent openFacts = new Intent(this, FactsActivity.class);
        startActivity(openFacts);
    }

    public void changeLanguage(View view) {
        String lang = getLangFromPref().equals(LANG_EN) ? LANG_RU : LANG_EN;
        setLangToPref(lang);

        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang));
        res.updateConfiguration(conf, res.getDisplayMetrics());

        recreate();
    }

    private void setLangToPref(String lang) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString(PREF_LANGUAGE, lang).apply();
    }

    private String getLangFromPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString(PREF_LANGUAGE, LANG_EN);
    }
}
