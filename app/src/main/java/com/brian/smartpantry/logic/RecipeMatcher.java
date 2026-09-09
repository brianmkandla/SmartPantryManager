package com.brian.smartpantry.logic;

import com.brian.smartpantry.model.PantryItem;
import com.brian.smartpantry.model.Recipe;
import com.brian.smartpantry.model.RecipeIngredient;

import java.util.List;

public class RecipeMatcher
{
    // Checks whether all ingredients required by a recipe
    // are available in the pantry in sufficient quantities.
    public boolean canMakeRecipe(
            Recipe recipe,
            List<RecipeIngredient> recipeIngredients,
            List<PantryItem> pantryItems)
    {
        for (RecipeIngredient recipeIngredient : recipeIngredients)
        {
            boolean ingredientAvailable = false;

            for (PantryItem pantryItem : pantryItems)
            {
                if (ingredientsMatch(recipeIngredient, pantryItem))
                {
                    if (pantryItem.getQuantity() >= recipeIngredient.getQuantity())
                    {
                        ingredientAvailable = true;
                        break;
                    }
                }
            }

            // One missing ingredient means the recipe cannot be made.
            if (!ingredientAvailable)
            {
                return false;
            }
        }

        return true;
    }

    // Checks whether a pantry ingredient matches a recipe ingredient.
    private boolean ingredientsMatch(
            RecipeIngredient recipeIngredient,
            PantryItem pantryItem)
    {
        String recipeName = recipeIngredient.getIngredientName().trim();
        String pantryName = pantryItem.getName().trim();

        String recipeUnit = recipeIngredient.getUnit().trim();
        String pantryUnit = pantryItem.getUnit().trim();

        return recipeName.equalsIgnoreCase(pantryName)
                && recipeUnit.equalsIgnoreCase(pantryUnit);
    }
}