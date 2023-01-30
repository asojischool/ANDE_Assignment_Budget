package com.example.ande_assignment_budget.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDbHandler extends SQLiteOpenHelper {

    private static final int db_version = 1;
    private static final String db = "budgetApp";

    public SqliteDbHandler(Context context) {
        // 3rd argument to be passed is CursorFactory instance
        super(context, db, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (userId TEXT NOT NULL, profilePicture TEXT UNIQUE NOT NULL, name TEXT NOT NULL, created TEXT DEFAULT " +
                "CURRENT_TIMESTAMP, PRIMARY KEY (userId));");
        db.execSQL("CREATE TABLE Expense (userId TEXT NOT NULL, expenseId INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, date TEXT DEFAULT" +
                " CURRENT_TIMESTAMP, category TEXT NOT NULL, notes TEXT NOT NULL, amount REAL NOT NULL, FOREIGN KEY (userId) REFERENCES User" +
                "(userId));");
        db.execSQL("CREATE TABLE Budget (userId TEXT NOT NULL, month INTEGER NOT NULL, budget TEXT NOT NULL, FOREIGN KEY (userId) REFERENCES User" +
                "(userId), PRIMARY KEY (userId, month));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // seed dummy data
    public void seedTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert data into the User table
        ContentValues userValues = new ContentValues();
        userValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        userValues.put("profilePicture", "user1.jpg");
        userValues.put("name", "John");
        db.insert("User", null, userValues);

        // Insert data into the Expense table
        ContentValues expenseValues = new ContentValues();
        expenseValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        expenseValues.put("title", "Lunch at a restaurant");
        expenseValues.put("category", "Food");
        expenseValues.put("notes", "Rice, Whole Fried Chicken and Soup.");
        expenseValues.put("amount", 4.50);
        db.insert("Expense", null, expenseValues);

        // Insert data into the Budget table
        ContentValues budgetValues = new ContentValues();
        budgetValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        budgetValues.put("month", 1);
        budgetValues.put("budget", "1000.00");
        db.insert("Budget", null, budgetValues);
    }

}
