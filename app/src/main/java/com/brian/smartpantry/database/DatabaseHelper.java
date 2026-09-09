package com.brian.smartpantry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brian.smartpantry.model.PantryItem;
import com.brian.smartpantry.model.Recipe;
import com.brian.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "smart_pantry.db";
	private static final int DATABASE_VERSION = 2;
	
	// Table name 
	private static final String TABLE_PANTRY_ITEMS = "pantry_items";
	
	// Column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_QUANTITY = "quantity";
	private static final String COLUMN_UNIT = "unit";
	private static final String COLUMN_EXPIRY_DATE = "expiry_date";
	
	// Recipe table name.
    private static final String TABLE_RECIPES = "recipes";

    // Recipe ingredient table name.
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    // Recipe column names.
    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "name";
    private static final String COLUMN_RECIPE_DESCRIPTION = "description";
    private static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";

    // Recipe ingredient column names.
    private static final String COLUMN_RECIPE_INGREDIENT_ID = "id";
    private static final String COLUMN_RECIPE_INGREDIENT_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RECIPE_INGREDIENT_NAME = "ingredient_name";
    private static final String COLUMN_RECIPE_INGREDIENT_QUANTITY = "quantity";
    private static final String COLUMN_RECIPE_INGREDIENT_UNIT = "unit";
	
	
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
				
		// Creates the recipes table.
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_RECIPE_DESCRIPTION + " TEXT, " +
                COLUMN_RECIPE_INSTRUCTIONS + " TEXT NOT NULL)");

        // Creates the recipe_ingredients table.
        // Each ingredient belongs to one recipe.
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COLUMN_RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                COLUMN_RECIPE_INGREDIENT_NAME + " TEXT NOT NULL, " +
                COLUMN_RECIPE_INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                COLUMN_RECIPE_INGREDIENT_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_RECIPE_INGREDIENT_RECIPE_ID + ") " +
                "REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))");
    }
	
	// Called when the database version is increased.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Add the recipe tables when upgrading from version 1.
        if (oldVersion < 2)
        {
            db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                    COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                    COLUMN_RECIPE_DESCRIPTION + " TEXT, " +
                    COLUMN_RECIPE_INSTRUCTIONS + " TEXT NOT NULL)");

            db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                    COLUMN_RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPE_INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                    COLUMN_RECIPE_INGREDIENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_RECIPE_INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_RECIPE_INGREDIENT_UNIT + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_RECIPE_INGREDIENT_RECIPE_ID + ") " +
                    "REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))");
        }
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
	
	// Adds a recipe to the recipes table.
	public long addRecipe(Recipe recipe)
	{
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_RECIPE_NAME, recipe.getName());
		values.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());
		values.put(COLUMN_RECIPE_INSTRUCTIONS, recipe.getInstructions());

		return db.insert(TABLE_RECIPES, null, values);
	}

	// Adds an ingredient belonging to a recipe.
	public long addRecipeIngredient(RecipeIngredient ingredient)
	{
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_RECIPE_INGREDIENT_RECIPE_ID, ingredient.getRecipeId());
		values.put(COLUMN_RECIPE_INGREDIENT_NAME, ingredient.getIngredientName());
		values.put(COLUMN_RECIPE_INGREDIENT_QUANTITY, ingredient.getQuantity());
		values.put(COLUMN_RECIPE_INGREDIENT_UNIT, ingredient.getUnit());

		return db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
	}

	// Gets all recipes stored in the database.
	public List<Recipe> getAllRecipes()
	{
		List<Recipe> recipes = new ArrayList<>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(
				TABLE_RECIPES,
				null,
				null,
				null,
				null,
				null,
				COLUMN_RECIPE_ID + " ASC"
		);

		while (cursor.moveToNext())
		{
			int id = cursor.getInt(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID)
			);

			String name = cursor.getString(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME)
			);

			String description = cursor.getString(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_DESCRIPTION)
			);

			String instructions = cursor.getString(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INSTRUCTIONS)
			);

			recipes.add(new Recipe(
					id,
					name,
					description,
					instructions
			));
		}

		cursor.close();

		return recipes;
	}

	// Gets all ingredients belonging to a specific recipe.
	public List<RecipeIngredient> getRecipeIngredients(int recipeId)
	{
		List<RecipeIngredient> ingredients = new ArrayList<>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(
				TABLE_RECIPE_INGREDIENTS,
				null,
				COLUMN_RECIPE_INGREDIENT_RECIPE_ID + " = ?",
				new String[]{String.valueOf(recipeId)},
				null,
				null,
				COLUMN_RECIPE_INGREDIENT_ID + " ASC"
		);

		while (cursor.moveToNext())
		{
			int id = cursor.getInt(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENT_ID)
			);

			String ingredientName = cursor.getString(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENT_NAME)
			);

			double quantity = cursor.getDouble(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENT_QUANTITY)
			);

			String unit = cursor.getString(
					cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENT_UNIT)
			);

			ingredients.add(new RecipeIngredient(
					id,
					recipeId,
					ingredientName,
					quantity,
					unit
			));
		}

		cursor.close();

		return ingredients;
	}
	
	// Checks whether recipes have already been added to the database.
	public boolean hasRecipes()
	{
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"SELECT COUNT(*) FROM " + TABLE_RECIPES,
				null
		);

		boolean hasRecipes = false;

		if (cursor.moveToFirst())
		{
			hasRecipes = cursor.getInt(0) > 0;
		}

		cursor.close();

		return hasRecipes;
	}
}	






















