package com.brian.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brian.smartpantry.database.DatabaseHelper;
import com.brian.smartpantry.model.PantryItem;

public class AddPantryItemActivity extends AppCompatActivity 
{

    private EditText editItemName;
    private EditText editItemQuantity;
    private EditText editItemUnit;
    private EditText editItemExpiry;
    private Button buttonSaveItem;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pantry_item);

        // Connect the input fields and button from the XML layout.
        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editItemUnit = findViewById(R.id.editItemUnit);
        editItemExpiry = findViewById(R.id.editItemExpiry);
        buttonSaveItem = findViewById(R.id.buttonSaveItem);

        // Create the database helper used to save the pantry item.
        databaseHelper = new DatabaseHelper(this);

        // Save the item when the user presses the Save Item button.
        buttonSaveItem.setOnClickListener(v -> savePantryItem());
    }

    // Validates the form and saves the pantry item to SQLite.
    private void savePantryItem() 
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

        // Create a PantryItem object using the values entered by the user.
        PantryItem pantryItem = new PantryItem(
                name,
                quantity,
                unit,
                expiryDate
        );

        // Save the pantry item in the SQLite database.
        long result = databaseHelper.addPantryItem(pantryItem);

        if (result != -1) 
		{
            Toast.makeText(this, "Pantry item added", Toast.LENGTH_SHORT).show();

            // Return to the main pantry screen after saving.
            finish();
        } 
		else 
		{
            Toast.makeText(this, "Failed to add pantry item", Toast.LENGTH_SHORT).show();
        }
    }
}