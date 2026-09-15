package com.brian.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.PantryItem;

import java.util.List;

public class PantryActivity extends AppCompatActivity
{
    private RecyclerView recyclerPantry;
    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;
    private TextView textEmptyPantry;
    private TextView textPantrySummary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Connect the activity to the pantry screen views.
        recyclerPantry = findViewById(R.id.recyclerPantry);
        textEmptyPantry = findViewById(R.id.textEmptyPantry);
        textPantrySummary = findViewById(R.id.textPantrySummary);

        databaseHelper = new DatabaseHelper(this);

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        Button buttonAddItem = findViewById(R.id.buttonAddItem);

        buttonAddItem.setOnClickListener(v ->
        {
            // Open the existing screen used to add a new pantry item.
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddPantryItemActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Refresh the pantry whenever we return to this screen.
        loadPantryItems();
    }

    private void loadPantryItems()
    {
        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        int pantryCount = pantryItems.size();

        // Show a simple summary above the pantry list.
        textPantrySummary.setText(
                pantryCount
                        + (pantryCount == 1 ? " item" : " items")
                        + " currently in your pantry."
        );

        // Show the empty state when there are no pantry items.
        if (pantryItems.isEmpty())
        {
            recyclerPantry.setVisibility(RecyclerView.GONE);
            textEmptyPantry.setVisibility(TextView.VISIBLE);
        }
        else
        {
            recyclerPantry.setVisibility(RecyclerView.VISIBLE);
            textEmptyPantry.setVisibility(TextView.GONE);
        }

        // Reuse the existing adapter and edit/delete functionality.
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

    private void openEditScreen(PantryItem item)
    {
        // Pass the selected item's ID to the existing edit screen.
        Intent intent = new Intent(
                PantryActivity.this,
                EditPantryItemActivity.class
        );

        intent.putExtra("item_id", item.getId());

        startActivity(intent);
    }

    private void showDeleteConfirmation(PantryItem item)
    {
        // Ask for confirmation before permanently deleting an item.
        new AlertDialog.Builder(this)
                .setTitle("Delete Pantry Item")
                .setMessage(
                        "Are you sure you want to delete "
                                + item.getName()
                                + "?"
                )
                .setPositiveButton("Delete", (dialog, which) ->
                {
                    deletePantryItem(item);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deletePantryItem(PantryItem item)
    {
        int result =
                databaseHelper.deletePantryItem(item.getId());

        // Refresh the list after a successful deletion.
        if (result > 0)
        {
            loadPantryItems();
        }
    }
}