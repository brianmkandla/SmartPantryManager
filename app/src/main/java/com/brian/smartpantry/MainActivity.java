package com.brian.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.PantryItem;
import com.brian.smartpantry.database.RecipeSeeder;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerPantry;
    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the RecyclerView from the XML layout to this activity.
        recyclerPantry = findViewById(R.id.recyclerPantry);

        // Create the database helper used to access pantry data.
        databaseHelper = new DatabaseHelper(this);
		
		// Add the starter recipes the first time the database is used.
		if (!databaseHelper.hasRecipes())
		{
			RecipeSeeder.seedRecipes(databaseHelper);
		}

        // Set the RecyclerView to display items in a vertical list.
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        // Connect the Add Pantry Item button.
        Button buttonAddItem = findViewById(R.id.buttonAddItem);

        // Open the Add Pantry Item screen when the button is clicked.
        buttonAddItem.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, AddPantryItemActivity.class);
            startActivity(intent);
        });
		
		Button buttonSuggestedRecipes = findViewById(R.id.buttonSuggestedRecipes);

		buttonSuggestedRecipes.setOnClickListener(v ->
		{
			Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
			startActivity(intent);
		});
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Reload the pantry whenever this screen becomes visible.
        // This also refreshes the list after an add, edit or delete.
        loadPantryItems();
    }

    // Retrieves pantry items from the database and displays them in the RecyclerView.
    private void loadPantryItems()
    {
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();

        pantryAdapter = new PantryAdapter(
                pantryItems,
                new PantryAdapter.OnItemActionListener()
                {
                    @Override
                    public void onEdit(PantryItem item)
                    {
                        openEditScreen(item);
                    }

                    @Override
                    public void onDelete(PantryItem item)
                    {
                        showDeleteConfirmation(item);
                    }
                }
        );

        recyclerPantry.setAdapter(pantryAdapter);
    }

    // Opens the edit screen for the selected pantry item.
    private void openEditScreen(PantryItem item)
    {
        Intent intent = new Intent(MainActivity.this, EditPantryItemActivity.class);
        intent.putExtra("item_id", item.getId());
        startActivity(intent);
    }

    // Shows a confirmation dialog before deleting a pantry item.
    private void showDeleteConfirmation(PantryItem item)
    {
        new AlertDialog.Builder(this)
                .setTitle("Delete Pantry Item")
                .setMessage("Are you sure you want to delete " + item.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) ->
                {
                    deletePantryItem(item);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Deletes the selected pantry item from SQLite.
    private void deletePantryItem(PantryItem item)
    {
        int result = databaseHelper.deletePantryItem(item.getId());

        if (result > 0)
        {
            // Reload the list so the deleted item disappears.
            loadPantryItems();
        }
    }
}