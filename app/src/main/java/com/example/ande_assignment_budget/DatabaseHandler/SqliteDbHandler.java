package com.example.ande_assignment_budget.DatabaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.ande_assignment_budget.Model.BillsModel;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.R;
import com.example.ande_assignment_budget.SetBudget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqliteDbHandler extends SQLiteOpenHelper {

    private static final int db_version = 1;
    private static final String db = "budgetApp";
    private final Context context;

    public SqliteDbHandler(Context context) {
        // 3rd argument to be passed is CursorFactory instance
        super(context, db, null, db_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User Table
        db.execSQL("CREATE TABLE User (userId TEXT NOT NULL PRIMARY KEY, profilePicture TEXT UNIQUE NOT NULL, name TEXT NOT NULL, created TEXT " +
                "DEFAULT " +
                "CURRENT_TIMESTAMP);");

        // Category Table
        db.execSQL("CREATE TABLE Category (categoryId INTEGER PRIMARY KEY AUTOINCREMENT, categoryName TEXT NOT NULL);");

        // Expense Table
        db.execSQL("CREATE TABLE Expense (userId TEXT NOT NULL, expenseId INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, date TEXT DEFAULT" +
                " CURRENT_TIMESTAMP, categoryId INTEGER NOT NULL, notes TEXT NOT NULL, amount REAL NOT NULL, FOREIGN KEY (userId) REFERENCES User" +
                "(userId), FOREIGN KEY (categoryId) REFERENCES Category(categoryId));");

        // Budget Table
        db.execSQL("CREATE TABLE Budget (budgetId INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT NOT NULL, month INTEGER NOT NULL, year INTEGER NOT" +
                " NULL, budgetAmount REAL NOT NULL, categoryId INTEGER NOT NULL, FOREIGN KEY (userId) REFERENCES User(userId), FOREIGN KEY " +
                "(categoryId) REFERENCES Category(categoryId));");

        // Bill Table
        db.execSQL("CREATE TABLE Bill (billName TEXT NOT NULL, billAmount REAL NOT NULL, billDay INTEGER NOT NULL, billStatus INTERGER NOT NULL, billUUID INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<CategoryModel> getCurrentBudgetByMonth() {
        String userId = "D64TPnbOWUfZ3GcppDQVIQkHdgb2";
        int month = 2;
        int year = 2023;
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        String query = "SELECT Category.categoryId, IFNULL(Budget.budgetAmount, 0.00), Category.categoryName " +
                "FROM Category LEFT JOIN Budget " +
                "ON Category.categoryId = Budget.categoryId " +
                "WHERE (Budget.userId = ? OR Budget.userId IS NULL) " +
                "AND (Budget.month = ? OR Budget.month IS NULL) " +
                "AND (Budget.year = ? OR Budget.year IS NULL)";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{userId, String.valueOf(month), String.valueOf(year)});

        while (cursor.moveToNext()) {
            int categoryId = cursor.getInt(0);
            double budgetAmount = cursor.getDouble(1);
            String categoryName = cursor.getString(2);

            categoryModels.add(new CategoryModel(categoryName, budgetAmount,
                    this.context.getResources().obtainTypedArray(R.array.icons).getResourceId(categoryId - 1, 0), categoryId));
        }
        cursor.close();
        return categoryModels;
    }

    public ArrayList<BillsModel> getAllBills(){
        String userId = "D64TPnbOWUfZ3GcppDQVIQkHdgb2";
        ArrayList<BillsModel> billsModels = new ArrayList<>();

        String query = "SELECT Bill.billName, Bill.billAmount, Bill.billDay, Bill.billStatus FROM Bill";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String billName = cursor.getString(0);
                @SuppressLint("Range") double billAmount = cursor.getDouble(1);
                @SuppressLint("Range") int billDueDay = cursor.getInt(2);
                @SuppressLint("Range") int billStatus = cursor.getInt(3);

                billsModels.add(new BillsModel(billName, billAmount, billDueDay, billStatus));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return billsModels;
    }

    public int getBillUUIDByName(String billName){
        String query = "SELECT Bill.billUUID FROM Bill WHERE Bill.billName = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public boolean createBill(String billName, double billAmt, int billDay, int billUUID){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String userId = "D64TPnbOWUfZ3GcppDQVIQkHdgb2";
            ContentValues values = new ContentValues();

//            values.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
            values.put("billName", billName);
            values.put("billAmount", billAmt);
            values.put("billDay", billDay);
            values.put("billStatus", 0);
            values.put("billUUID", billUUID);
            db.insert("Bill", null, values);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void updateBillStatus(String billName){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("billStatus", 1);

            int rowsUpdated = db.update("Bill", values, "billName = ?", new String[]{billName});
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void deleteBill(String billName){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete("Bill", "billName = ?", new String[]{billName});
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void setCurrentBudgetByMonth(int catId, double budgetAmount) {
        String userId = "D64TPnbOWUfZ3GcppDQVIQkHdgb2";
        int month = 2;
        int year = 2023;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT categoryId, budgetAmount " +
                "FROM Budget " +
                "WHERE userId = ? " +
                "AND month = ? " +
                "AND year = ? " +
                "AND categoryId = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userId, String.valueOf(month), String.valueOf(year), String.valueOf(catId)});

        if (cursor.getCount() == 1) {
            String updateQuery = "UPDATE Budget SET budgetAmount = ? WHERE userId = ? AND month = ? AND year = ? AND categoryId = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindDouble(1, budgetAmount);
            statement.bindString(2, userId);
            statement.bindLong(3, month);
            statement.bindLong(4, year);
            statement.bindLong(5, catId);

            statement.execute();
        } else {
            ContentValues values = new ContentValues();
            values.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
            values.put("month", month);
            values.put("year", year);
            values.put("budgetAmount", budgetAmount);
            values.put("categoryId", catId);
            db.insert("Budget", null, values);
        }

        cursor.close();
    }

    // seed dummy data
    public void seedTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert data into the User table
        ContentValues userValues = new ContentValues();
        userValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        userValues.put("profilePicture", "profile1.jpg");
        userValues.put("name", "John");
        db.insert("User", null, userValues);

        // Insert data into the Expense table
        ContentValues expenseValues = new ContentValues();
        expenseValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        expenseValues.put("title", "Lunch at Song Garden Restaurant");
        expenseValues.put("date", "2023-02-01 00:00");
        expenseValues.put("categoryId", "2");
        expenseValues.put("notes", "Rice, Whole Fried Chicken and Soup.");
        expenseValues.put("amount", 34.50);
        db.insert("Expense", null, expenseValues);

        // Insert data into the Budget table
        ContentValues budgetValues = new ContentValues();
        budgetValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        budgetValues.put("month", 2);
        budgetValues.put("year", 2023);
        budgetValues.put("budgetAmount", 1000.00);
        budgetValues.put("categoryId", 2);
        db.insert("Budget", null, budgetValues);

        ContentValues billValues = new ContentValues();
//        billValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        billValues.put("billName", "Test 1");
        billValues.put("billAmount", 555);
        billValues.put("billDay", 22);
        billValues.put("billStatus", 0);
        db.insert("Bill", null, billValues);


        ContentValues categoryValues = new ContentValues();
        categoryValues.put("categoryName", "Transport");
        db.insert("Category", null, categoryValues);

        categoryValues = new ContentValues();
        categoryValues.put("categoryName", "Food");
        db.insert("Category", null, categoryValues);

        categoryValues = new ContentValues();
        categoryValues.put("categoryName", "Personal");
        db.insert("Category", null, categoryValues);
    }

}
