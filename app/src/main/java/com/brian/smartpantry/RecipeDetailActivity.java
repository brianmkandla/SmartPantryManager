package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.Recipe;
import com.brian.smartpantry.model.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity
{
    private DatabaseHelper databaseHelper;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Connect the database helper used to retrieve and update recipe information.
        databaseHelper = new DatabaseHelper(this);

        // Connect the TextViews used to display the recipe details.
        TextView textRecipeName =
                findViewById(R.id.textRecipeName);

        TextView textRecipeDescription =
                findViewById(R.id.textRecipeDescription);

        TextView textRecipeIngredients =
                findViewById(R.id.textRecipeIngredients);

        TextView textRecipeInstructions =
                findViewById(R.id.textRecipeInstructions);

        // Get the recipe ID passed from the Suggested Recipes screen.
        recipeId = getIntent().getIntExtra("recipe_id", -1);

        // Stop if a valid recipe ID was not provided.
        if (recipeId == -1)
        {
            finish();
            return;
        }

        // Find the selected recipe in the database.
        List<Recipe> recipes =
                databaseHelper.getAllRecipes();

        Recipe selectedRecipe = null;

        for (Recipe recipe : recipes)
        {
            if (recipe.getId() == recipeId)
            {
                selectedRecipe = recipe;
                break;
            }
        }

        // Stop if the recipe could not be found.
        if (selectedRecipe == null)
        {
            finish();
            return;
        }

        // Display the recipe name and description.
        textRecipeName.setText(
                selectedRecipe.getName()
        );

        textRecipeDescription.setText(
                selectedRecipe.getDescription()
        );

        // Get and display all ingredients required by the recipe.
        List<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientText =
                new StringBuilder();

        for (RecipeIngredient ingredient : ingredients)
        {
            ingredientText.append("• ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(formatQuantity(ingredient.getQuantity()))
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        textRecipeIngredients.setText(
                ingredientText.toString()
        );

        // Display the cooking instructions stored for the recipe.
        textRecipeInstructions.setText(
                selectedRecipe.getInstructions()
        );

        // Mark this recipe as cooked when the user taps the main action.
        Button buttonCooked =
                findViewById(R.id.buttonCooked);

        buttonCooked.setOnClickListener(v ->
        {
            markRecipeAsCooked();
        });

        // Return to the Suggested Recipes screen.
        Button buttonBack =
                findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(v ->
        {
            finish();
        });
    }

    // Increase the usage counter for the current recipe.
    private void markRecipeAsCooked()
    {
        int result =
                databaseHelper.markRecipeAsCooked(recipeId);

        if (result > 0)
        {
            Toast.makeText(
                    this,
                    "Recipe marked as cooked!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Removes unnecessary .0 from whole-number quantities.
    private String formatQuantity(double quantity)
    {
        if (quantity == (long) quantity)
        {
            return String.valueOf((long) quantity);
        }

        return String.valueOf(quantity);
    }
}