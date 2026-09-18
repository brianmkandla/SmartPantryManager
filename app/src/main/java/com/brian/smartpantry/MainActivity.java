package com.brian.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.database.RecipeSeeder;
import com.brian.smartpantry.model.PantryItem;
import com.brian.smartpantry.model.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DatabaseHelper databaseHelper;
    private TextView textPantrySummary;
    private LinearLayout mostCookedContainer;
    private TextView textNoMostCooked;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPantrySummary = findViewById(R.id.textPantrySummary);
        mostCookedContainer = findViewById(R.id.mostCookedContainer);
        textNoMostCooked = findViewById(R.id.textNoMostCooked);

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

        // Refresh both Home sections whenever the user returns.
        loadPantrySummary();
        loadMostCookedRecipes();
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

    private void loadMostCookedRecipes()
    {
        List<Recipe> recipes =
                databaseHelper.getMostCookedRecipes();

        mostCookedContainer.removeAllViews();

        if (recipes.isEmpty())
        {
            textNoMostCooked.setVisibility(View.VISIBLE);
            return;
        }

        textNoMostCooked.setVisibility(View.GONE);

        for (Recipe recipe : recipes)
        {
            TextView recipeRow = new TextView(this);

            recipeRow.setText(recipe.getName());
            recipeRow.setTextColor(
                    getResources().getColor(R.color.pantry_text)
            );
            recipeRow.setTextSize(16);
            recipeRow.setTypeface(null, Typeface.BOLD);
            recipeRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
            recipeRow.setPadding(18, 18, 18, 18);
            recipeRow.setBackgroundResource(
                    R.drawable.recipe_usage_row_background
            );

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            params.setMargins(0, 0, 0, 10);

            recipeRow.setLayoutParams(params);

            recipeRow.setOnClickListener(v ->
            {
                Intent intent = new Intent(
                        MainActivity.this,
                        RecipeDetailActivity.class
                );

                intent.putExtra("recipe_id", recipe.getId());

                startActivity(intent);
            });

            mostCookedContainer.addView(recipeRow);
        }
    }
}