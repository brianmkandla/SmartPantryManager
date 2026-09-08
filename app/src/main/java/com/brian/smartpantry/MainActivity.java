package com.brian.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.PantryItem;

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
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Reload the pantry whenever this screen becomes visible.
        // This allows newly added items to appear immediately.
        loadPantryItems();
    }

    // Retrieves pantry items from the database and displays them in the RecyclerView.
    private void loadPantryItems()
    {
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();

        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantry.setAdapter(pantryAdapter);
    }
}