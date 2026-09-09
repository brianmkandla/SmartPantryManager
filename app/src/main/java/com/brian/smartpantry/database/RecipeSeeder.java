package com.brian.smartpantry.database;

import com.brian.smartpantry.model.Recipe;
import com.brian.smartpantry.model.RecipeIngredient;

public class RecipeSeeder
{
    public static void seedRecipes(DatabaseHelper databaseHelper)
    {
        // Recipe 1
        addRecipe(
                databaseHelper,
                "Chicken Fried Rice",
                "Fried rice made with chicken and vegetables.",
                "Cook the rice and chicken. Stir-fry the vegetables, add the rice and chicken, then mix well.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("rice", 2, "cups"),
                        new RecipeIngredientData("chicken", 300, "g"),
                        new RecipeIngredientData("egg", 2, "items"),
                        new RecipeIngredientData("carrot", 1, "items"),
                        new RecipeIngredientData("peas", 1, "cups")
                }
        );

        // Recipe 2
        addRecipe(
                databaseHelper,
                "Spaghetti Bolognese",
                "Spaghetti served with a simple meat and tomato sauce.",
                "Cook the spaghetti. Brown the mince, add the tomato sauce and simmer. Serve with the spaghetti.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("spaghetti", 250, "g"),
                        new RecipeIngredientData("beef mince", 250, "g"),
                        new RecipeIngredientData("tomato", 2, "items"),
                        new RecipeIngredientData("onion", 1, "items"),
                        new RecipeIngredientData("garlic", 2, "cloves")
                }
        );

        // Recipe 3
        addRecipe(
                databaseHelper,
                "Chicken Sandwich",
                "A simple chicken sandwich with vegetables.",
                "Cook the chicken. Place the chicken, lettuce and tomato between two slices of bread.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("bread", 4, "slices"),
                        new RecipeIngredientData("chicken", 200, "g"),
                        new RecipeIngredientData("lettuce", 2, "leaves"),
                        new RecipeIngredientData("tomato", 1, "items")
                }
        );

        // Recipe 4
        addRecipe(
                databaseHelper,
                "Vegetable Omelette",
                "An omelette filled with mixed vegetables.",
                "Beat the eggs. Cook the vegetables, add the eggs and cook until set.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("egg", 3, "items"),
                        new RecipeIngredientData("onion", 0.5, "items"),
                        new RecipeIngredientData("tomato", 1, "items"),
                        new RecipeIngredientData("pepper", 1, "items")
                }
        );

        // Recipe 5
        addRecipe(
                databaseHelper,
                "Beef Burger",
                "A homemade beef burger with lettuce and tomato.",
                "Cook the beef patty. Place it inside the bun with lettuce and tomato.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("beef mince", 200, "g"),
                        new RecipeIngredientData("burger bun", 1, "items"),
                        new RecipeIngredientData("lettuce", 1, "leaves"),
                        new RecipeIngredientData("tomato", 1, "items"),
                        new RecipeIngredientData("onion", 0.5, "items")
                }
        );

        // Recipe 6
        addRecipe(
                databaseHelper,
                "Pancakes",
                "Simple homemade pancakes.",
                "Mix the flour, milk and egg into a batter. Cook the pancakes in a pan.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("flour", 1, "cups"),
                        new RecipeIngredientData("milk", 1, "cups"),
                        new RecipeIngredientData("egg", 1, "items"),
                        new RecipeIngredientData("sugar", 2, "tbsp")
                }
        );

        // Recipe 7
        addRecipe(
                databaseHelper,
                "Tuna Sandwich",
                "A quick sandwich made with tuna.",
                "Mix the tuna with mayonnaise. Add lettuce and place the mixture between slices of bread.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("bread", 4, "slices"),
                        new RecipeIngredientData("tuna", 1, "can"),
                        new RecipeIngredientData("mayonnaise", 2, "tbsp"),
                        new RecipeIngredientData("lettuce", 2, "leaves")
                }
        );

        // Recipe 8
        addRecipe(
                databaseHelper,
                "Tomato Pasta",
                "Simple pasta with tomato sauce.",
                "Cook the pasta. Cook the tomato, onion and garlic, then mix with the pasta.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("pasta", 250, "g"),
                        new RecipeIngredientData("tomato", 3, "items"),
                        new RecipeIngredientData("onion", 1, "items"),
                        new RecipeIngredientData("garlic", 2, "cloves")
                }
        );

        // Recipe 9
        addRecipe(
                databaseHelper,
                "Chicken Wrap",
                "Chicken and vegetables wrapped in a tortilla.",
                "Cook the chicken. Add the chicken and vegetables to the tortilla and wrap.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("chicken", 200, "g"),
                        new RecipeIngredientData("tortilla", 2, "items"),
                        new RecipeIngredientData("lettuce", 2, "leaves"),
                        new RecipeIngredientData("tomato", 1, "items")
                }
        );

        // Recipe 10
        addRecipe(
                databaseHelper,
                "French Toast",
                "Bread dipped in egg and fried until golden.",
                "Beat the eggs. Dip the bread into the egg mixture and fry on both sides.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("bread", 4, "slices"),
                        new RecipeIngredientData("egg", 2, "items"),
                        new RecipeIngredientData("milk", 0.5, "cups"),
                        new RecipeIngredientData("sugar", 1, "tbsp")
                }
        );

        // Recipe 11
        addRecipe(
                databaseHelper,
                "Chicken Curry",
                "Chicken cooked in a simple curry sauce.",
                "Cook the onion and chicken. Add curry powder and tomato, then simmer until cooked.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("chicken", 300, "g"),
                        new RecipeIngredientData("onion", 1, "items"),
                        new RecipeIngredientData("tomato", 2, "items"),
                        new RecipeIngredientData("curry powder", 2, "tbsp"),
                        new RecipeIngredientData("rice", 2, "cups")
                }
        );

        // Recipe 12
        addRecipe(
                databaseHelper,
                "Egg Fried Rice",
                "Fried rice with egg and vegetables.",
                "Cook the rice. Scramble the eggs, add the vegetables and rice, then stir-fry.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("rice", 2, "cups"),
                        new RecipeIngredientData("egg", 2, "items"),
                        new RecipeIngredientData("carrot", 1, "items"),
                        new RecipeIngredientData("peas", 1, "cups")
                }
        );

        // Recipe 13
        addRecipe(
                databaseHelper,
                "Grilled Cheese Sandwich",
                "Toasted bread filled with melted cheese.",
                "Place cheese between bread slices and toast until the bread is golden and the cheese melts.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("bread", 4, "slices"),
                        new RecipeIngredientData("cheese", 100, "g")
                }
        );

        // Recipe 14
        addRecipe(
                databaseHelper,
                "Macaroni and Cheese",
                "Macaroni covered in a simple cheese sauce.",
                "Cook the macaroni. Make a cheese sauce with milk and cheese, then mix with the macaroni.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("macaroni", 250, "g"),
                        new RecipeIngredientData("milk", 2, "cups"),
                        new RecipeIngredientData("cheese", 150, "g"),
                        new RecipeIngredientData("flour", 2, "tbsp")
                }
        );

        // Recipe 15
        addRecipe(
                databaseHelper,
                "Beef Stir Fry",
                "Beef cooked with vegetables in a pan.",
                "Slice and cook the beef. Add the vegetables and stir-fry until cooked.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("beef", 250, "g"),
                        new RecipeIngredientData("carrot", 1, "items"),
                        new RecipeIngredientData("pepper", 1, "items"),
                        new RecipeIngredientData("onion", 1, "items")
                }
        );

        // Recipe 16
        addRecipe(
                databaseHelper,
                "Vegetable Soup",
                "A simple soup made from mixed vegetables.",
                "Chop the vegetables. Add them to water and simmer until soft.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("carrot", 2, "items"),
                        new RecipeIngredientData("potato", 2, "items"),
                        new RecipeIngredientData("onion", 1, "items"),
                        new RecipeIngredientData("tomato", 2, "items")
                }
        );

        // Recipe 17
        addRecipe(
                databaseHelper,
                "Chicken Pasta",
                "Pasta with cooked chicken and tomato.",
                "Cook the pasta and chicken. Add tomato and mix everything together.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("pasta", 250, "g"),
                        new RecipeIngredientData("chicken", 200, "g"),
                        new RecipeIngredientData("tomato", 2, "items"),
                        new RecipeIngredientData("onion", 1, "items")
                }
        );

        // Recipe 18
        addRecipe(
                databaseHelper,
                "Mashed Potatoes",
                "Soft mashed potatoes with milk.",
                "Boil the potatoes until soft. Mash them and mix with milk.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("potato", 4, "items"),
                        new RecipeIngredientData("milk", 0.5, "cups")
                }
        );

        // Recipe 19
        addRecipe(
                databaseHelper,
                "Egg and Tomato Sandwich",
                "A simple sandwich made with egg and tomato.",
                "Cook the eggs. Add the eggs and sliced tomato between slices of bread.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("bread", 4, "slices"),
                        new RecipeIngredientData("egg", 2, "items"),
                        new RecipeIngredientData("tomato", 1, "items")
                }
        );

        // Recipe 20
        addRecipe(
                databaseHelper,
                "Chicken and Rice",
                "A simple meal of cooked chicken and rice.",
                "Cook the rice and chicken separately, then serve them together.",
                new RecipeIngredientData[]
                {
                        new RecipeIngredientData("rice", 2, "cups"),
                        new RecipeIngredientData("chicken", 300, "g")
                }
        );
    }

    private static void addRecipe(
            DatabaseHelper databaseHelper,
            String name,
            String description,
            String instructions,
            RecipeIngredientData[] ingredients)
    {
        Recipe recipe = new Recipe(
                name,
                description,
                instructions
        );

        long recipeId = databaseHelper.addRecipe(recipe);

        if (recipeId == -1)
        {
            return;
        }

        for (RecipeIngredientData ingredientData : ingredients)
        {
            RecipeIngredient ingredient = new RecipeIngredient(
                    (int) recipeId,
                    ingredientData.name,
                    ingredientData.quantity,
                    ingredientData.unit
            );

            databaseHelper.addRecipeIngredient(ingredient);
        }
    }

    private static class RecipeIngredientData
    {
        private String name;
        private double quantity;
        private String unit;

        private RecipeIngredientData(
                String name,
                double quantity,
                String unit)
        {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
        }
    }
}