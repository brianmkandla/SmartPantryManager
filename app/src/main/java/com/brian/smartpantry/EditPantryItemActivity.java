package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.PantryItem;

public class EditPantryItemActivity extends AppCompatActivity
{
    private EditText editItemName;
    private EditText editItemQuantity;
    private EditText editItemUnit;
    private EditText editItemExpiry;
    private Button buttonUpdateItem;

    private DatabaseHelper databaseHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pantry_item);

        // Connect the input fields and button from the XML layout.
        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editItemUnit = findViewById(R.id.editItemUnit);
        editItemExpiry = findViewById(R.id.editItemExpiry);
        buttonUpdateItem = findViewById(R.id.buttonUpdateItem);

        // Create the database helper used to update the pantry item.
        databaseHelper = new DatabaseHelper(this);

        // Get the ID of the pantry item selected for editing.
        itemId = getIntent().getIntExtra("item_id", -1);

        // Stop if no valid item ID was supplied.
        if (itemId == -1)
        {
            Toast.makeText(this, "Pantry item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load the existing pantry item into the form.
        loadPantryItem();

        // Update the item when the user presses the button.
        buttonUpdateItem.setOnClickListener(v ->
        {
            updatePantryItem();
        });
    }

    // Loads the selected pantry item from SQLite.
    private void loadPantryItem()
    {
        PantryItem item = databaseHelper.getPantryItemById(itemId);

        if (item == null)
        {
            Toast.makeText(this, "Pantry item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Place the existing values into the input fields.
        editItemName.setText(item.getName());
        editItemQuantity.setText(String.valueOf(item.getQuantity()));
        editItemUnit.setText(item.getUnit());
        editItemExpiry.setText(item.getExpiryDate());
    }

    // Validates the form and updates the pantry item in SQLite.
    private void updatePantryItem()
    {
        String name = editItemName.getText().toString().trim();
        String quantityText = editItemQuantity.getText().toString().trim();
        String unit = editItemUnit.getText().toString().trim();
        String expiryDate = editItemExpiry.getText().toString().trim();

        // Make sure the required fields have been entered.
        if (name.isEmpty())
        {
            editItemName.setError("Enter an item name");
            editItemName.requestFocus();
            return;
        }

        if (quantityText.isEmpty())
        {
            editItemQuantity.setError("Enter a quantity");
            editItemQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty())
        {
            editItemUnit.setError("Enter a unit");
            editItemUnit.requestFocus();
            return;
        }

        double quantity;

        // Convert the quantity from text into a number.
        try
        {
            quantity = Double.parseDouble(quantityText);
        }
        catch (NumberFormatException e)
        {
            editItemQuantity.setError("Enter a valid number");
            editItemQuantity.requestFocus();
            return;
        }

        // Quantity must be greater than zero.
        if (quantity <= 0)
        {
            editItemQuantity.setError("Quantity must be greater than zero");
            editItemQuantity.requestFocus();
            return;
        }

        // Create an updated PantryItem object.
        PantryItem pantryItem = new PantryItem(
                itemId,
                name,
                quantity,
                unit,
                expiryDate
        );

        // Update the existing database record.
        int result = databaseHelper.updatePantryItem(pantryItem);

        if (result > 0)
        {
            Toast.makeText(this, "Pantry item updated", Toast.LENGTH_SHORT).show();

            // Return to the pantry screen after saving.
            finish();
        }
        else
        {
            Toast.makeText(this, "Failed to update pantry item", Toast.LENGTH_SHORT).show();
        }
    }
}