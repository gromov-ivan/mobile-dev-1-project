package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFactsActivity(View view) {
        Intent openFacts = new Intent(this, FactsActivity.class);
        startActivity(openFacts);
    }

    public void changeLanguage(View view) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (conf.getLocales().get(0).getLanguage().equals("ru")) {
            conf.setLocale(new Locale("en"));
        } else {
            conf.setLocale(new Locale("ru"));
        }

        res.updateConfiguration(conf, dm);
        recreate();
    }
}
