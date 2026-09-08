package com.brian.smartpantry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brian.smartpantry.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "smart_pantry.db";
	private static final int DATABASE_VERSION = 1;
	
	// Table name 
	private static final String TABLE_PANTRY_ITEMS = "pantry_items";
	
	// Column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_QUANTITY = "quantity";
	private static final String COLUMN_UNIT = "unit";
	private static final String COLUMN_EXPIRY_DATE = "expiry_date";
	
	public DatabaseHelper(Context context) 
	{
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates the pantry_items table when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) 
	{
        db.execSQL("CREATE TABLE " + TABLE_PANTRY_ITEMS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " REAL NOT NULL, " +
                COLUMN_UNIT + " TEXT NOT NULL, " +
                COLUMN_EXPIRY_DATE + " TEXT)");
    }
	
	// Called when the database version is increased
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEMS);
        onCreate(db);
    }
	
	
	// Adds a new pantry item to the database
	public long addPantryItem(PantryItem item)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, item.getName());
		values.put(COLUMN_QUANTITY, item.getQuantity());
		values.put(COLUMN_UNIT, item.getUnit());
		values.put(COLUMN_EXPIRY_DATE, item.getExpiryDate());
		
		// insert() returns the ID of the new row, or -1 if the insert fails
		long id = db.insert(TABLE_PANTRY_ITEMS, null, values);
		
		db.close();
		
		return id;
	}
	
	// Retrives all pantry items from the database
	public List<PantryItem> getAllPantryItems()
	{
		List<PantryItem> pantryItems = new ArrayList<>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(
				TABLE_PANTRY_ITEMS, 
				null,
				null,
				null,
				null,
				null,
				COLUMN_NAME + " ASC"
		);
		
		// Move through each database row and convert it into a PantryItem object
		if (cursor.moveToFirst())
		{
			do 
			{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));

                PantryItem item = new PantryItem(id, name, quantity, unit, expiryDate);
                pantryItems.add(item);
            } while (cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return pantryItems;
	}
	
	// Updates an existing pantry item using its ID.
    public int updatePantryItem(PantryItem item) 
	{
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_UNIT, item.getUnit());
        values.put(COLUMN_EXPIRY_DATE, item.getExpiryDate());

        int rowsUpdated = db.update(
                TABLE_PANTRY_ITEMS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return rowsUpdated;
    }

    // Deletes a pantry item using its ID.
    public int deletePantryItem(int id)
	{	
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(
                TABLE_PANTRY_ITEMS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsDeleted;
    }
	
	    // Retrieves one pantry item using its database ID.
    public PantryItem getPantryItemById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY_ITEMS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        PantryItem item = null;

        // Check whether a matching item was found.
        if (cursor.moveToFirst())
        {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));

            item = new PantryItem(id, name, quantity, unit, expiryDate);
        }

        cursor.close();
        db.close();

        return item;
    }
}	






















