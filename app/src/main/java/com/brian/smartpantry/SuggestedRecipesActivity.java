package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuggestedRecipesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        Button buttonBackToPantry = findViewById(R.id.buttonBackToPantry);

        buttonBackToPantry.setOnClickListener(v ->
        {
            finish();
        });
    }
}