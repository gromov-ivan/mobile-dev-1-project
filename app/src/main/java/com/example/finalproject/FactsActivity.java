package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FactsActivity extends AppCompatActivity {
    private TextView textViewFact;
    private String currentFact;
    private static final String FACT_PREFS = "FactPrefs";
    private static final String CURRENT_FACT_KEY = "CurrentFactKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        textViewFact = findViewById(R.id.textViewFact);
        Button buttonNewFact = findViewById(R.id.buttonNewFact);
        Button buttonSearch = findViewById(R.id.buttonSearchFact);

        SharedPreferences prefs = getSharedPreferences(FACT_PREFS, MODE_PRIVATE);
        currentFact = prefs.getString(CURRENT_FACT_KEY, null);

        if (currentFact != null) {
            textViewFact.setText(currentFact);
        } else {
            getFact();
        }

        buttonNewFact.setOnClickListener(v -> getFact());

        buttonSearch.setOnClickListener(v -> searchOnGoogle());
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(FACT_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CURRENT_FACT_KEY, currentFact);
        editor.apply();
    }

    private void getFact() {
        new GetFactTask().execute();
    }

    private void searchOnGoogle() {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, currentFact);
        startActivity(intent);
    }

    private class GetFactTask extends AsyncTask<Void, Void, String> {
        private static final String API_URL = "https://api.api-ninjas.com/v1/facts?limit=1";
        private static final String API_KEY = "DiwMMMiAHyG0DZhOVl30Sg==jLWaiutCtnuOQLYG";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Api-Key", API_KEY);

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                currentFact = jsonArray.getJSONObject(0).getString("fact");
                textViewFact.setText(currentFact);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(FactsActivity.this, "Error: No API connection. Enable Internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
