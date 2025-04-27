package com.example.perchanceapiapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

    private EditText apiUrlEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        apiUrlEditText = findViewById(R.id.apiUrlEditText);
        saveButton = findViewById(R.id.saveButton);

        // Load saved API URL
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUrl = sharedPreferences.getString("api_url", "https://diy-perchance-api.glitch.me");
        apiUrlEditText.setText(savedUrl);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiUrl = apiUrlEditText.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("api_url", apiUrl);
                editor.apply();
                finish();
            }
        });
    }
}