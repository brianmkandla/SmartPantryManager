package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect the back button from the layout.
        Button buttonBack = findViewById(R.id.buttonBack);

        // Return to the previous screen.
        buttonBack.setOnClickListener(v ->
        {
            finish();
        });
    }
}