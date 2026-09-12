package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.logic.RecipeMatcher;
import com.brian.smartpantry.model.PantryItem;
import com.brian.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity
{
    private RecyclerView recyclerRecipes;
    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;
    private TextView textNoRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        textNoRecipes = findViewById(R.id.textNoRecipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestedRecipes();

        Button buttonBackToPantry = findViewById(R.id.buttonBackToPantry);

        buttonBackToPantry.setOnClickListener(v ->
        {
            finish();
        });
    }

    private void loadSuggestedRecipes()
    {
        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        List<Recipe> suggestedRecipes = new ArrayList<>();

        RecipeMatcher recipeMatcher = new RecipeMatcher();

        for (Recipe recipe : allRecipes)
        {
            if (recipeMatcher.canMakeRecipe(
                    recipe,
                    databaseHelper.getRecipeIngredients(recipe.getId()),
                    pantryItems))
            {
                suggestedRecipes.add(recipe);
            }
        }

        if (suggestedRecipes.isEmpty())
        {
            recyclerRecipes.setVisibility(RecyclerView.GONE);
            textNoRecipes.setVisibility(TextView.VISIBLE);
        }
        else
        {
            recyclerRecipes.setVisibility(RecyclerView.VISIBLE);
            textNoRecipes.setVisibility(TextView.GONE);

            recipeAdapter = new RecipeAdapter(suggestedRecipes);
            recyclerRecipes.setAdapter(recipeAdapter);
        }
    }
}