package com.brian.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.database.RecipeSeeder;
import com.brian.smartpantry.model.PantryItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DatabaseHelper databaseHelper;
    private TextView textPantrySummary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPantrySummary = findViewById(R.id.textPantrySummary);

        databaseHelper = new DatabaseHelper(this);

        // Add the starter recipes the first time the app is opened.
        if (!databaseHelper.hasRecipes())
        {
            RecipeSeeder.seedRecipes(databaseHelper);
        }

        // Open the dedicated pantry screen from the home dashboard.
        Button buttonViewPantry = findViewById(R.id.buttonViewPantry);

        buttonViewPantry.setOnClickListener(v ->
        {
            Intent intent = new Intent(
                    MainActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        // Open the recipe suggestions screen.
        Button buttonSuggestedRecipes =
                findViewById(R.id.buttonSuggestedRecipes);

        buttonSuggestedRecipes.setOnClickListener(v ->
        {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        // Open the application settings screen.
        ImageButton buttonSettings =
                findViewById(R.id.buttonSettings);

        buttonSettings.setOnClickListener(v ->
        {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Refresh the pantry count whenever the user returns to Home.
        loadPantrySummary();
    }

    private void loadPantrySummary()
    {
        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        int pantryCount = pantryItems.size();

        textPantrySummary.setText(
                pantryCount
                        + (pantryCount == 1 ? " item" : " items")
                        + " currently in your pantry."
        );
    }
}