# Smart Pantry Manager

Smart Pantry Manager is a Java Android application that helps users manage their pantry ingredients and find recipes they can make using the ingredients they already have.

The application was developed as part of the Mobile App Development 700 assignment.

## Features

* Add pantry ingredients
* View all pantry ingredients
* Edit pantry ingredients
* Delete pantry ingredients
* View the total number of pantry items
* Match pantry ingredients against recipes
* Strict recipe matching based on ingredient quantity and unit
* View recipe ingredients and cooking instructions
* Track recipes that have been cooked
* Display the user's most used recipes
* Empty-state messages when there are no pantry items or matching recipes
* Settings screen
* 20 built-in recipes

## Recipe Matching

The recipe matching system only suggests a recipe when all of its required ingredients are available in the pantry.

The matching checks:

* Ingredient name
* Required quantity
* Measurement unit

For example, if a recipe requires 4 slices of bread, having 3 slices is not enough. Having 4 items of bread also does not count as a match because the unit is different.

This ensures that the suggested recipes are based on what the user can actually make with their current pantry items.

## Technologies Used

* Java
* Android Studio
* SQLite
* Android SQLiteOpenHelper
* RecyclerView
* Android XML layouts
* Git and GitHub

## Application Structure

The application contains several screens:

* Home – Provides an overview of the pantry and most used recipes.
* Pantry – Displays pantry items and allows the user to add, edit and delete items.
* Find Recipes – Displays recipes that can currently be made from the pantry.
* Recipe Details – Displays the ingredients and instructions for a selected recipe.
* Settings – Provides application settings.

## Database

SQLite is used to store the application's data locally.

The database stores:

* Pantry items
* Recipes
* Recipe ingredients
* Recipe usage information

Recipe usage is tracked so that the application can display the user's most cooked recipes on the Home screen.

## Getting Started

### Requirements

* Android Studio
* Java
* Android SDK
* Android device or Android emulator

### Running the Application

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and finish indexing.
4. Connect an Android device with USB debugging enabled, or start an Android emulator.
5. Build and run the application.

The application automatically creates its local SQLite database when it is first started and adds the 20 starter recipes.

## Project Information

Project: Smart Pantry Manager
Language: Java
Platform: Android
Database: SQLite
Course: Mobile App Development 700

## Author

Brian Mkandla

Richfield Graduate Institute of Information Technology
