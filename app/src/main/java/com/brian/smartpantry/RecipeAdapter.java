package com.brian.smartpantry;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes)
    {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position)
    {
        Recipe recipe = recipes.get(position);

        holder.textRecipeName.setText(recipe.getName());
        holder.textRecipeDescription.setText(recipe.getDescription());

        // Open the recipe details screen when a recipe is selected.
        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra("recipe_id", recipe.getId());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder
    {
        TextView textRecipeName;
        TextView textRecipeDescription;

        public RecipeViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            textRecipeDescription =
                    itemView.findViewById(R.id.textRecipeDescription);
        }
    }
}