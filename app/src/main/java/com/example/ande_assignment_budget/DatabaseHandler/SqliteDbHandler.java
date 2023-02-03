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
import com.example.ande_assignment_budget.Model.ExpenseAndrewModel;
import com.example.ande_assignment_budget.Model.ExpenseBudgetModel;
import com.example.ande_assignment_budget.Model.Expense;
import com.example.ande_assignment_budget.R;
import com.example.ande_assignment_budget.SetBudget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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

    // seed dummy data
    public void seedTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert data into the User table
        ContentValues userValues1 = new ContentValues();
        userValues1.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        userValues1.put("profilePicture", "profile1.jpg");
        userValues1.put("name", "John");
        db.insert("User", null, userValues1);

        ContentValues userValues2 = new ContentValues();
        userValues2.put("userId", "Exb6e0hvVaUMsWHmXMn6jKV2wHf2");
        userValues2.put("profilePicture", "profile2.jpg");
        userValues2.put("name", "Tom");
        db.insert("User", null, userValues2);

        // Insert data into the Expense table
        ContentValues expenseValues = new ContentValues();
        expenseValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        expenseValues.put("title", "Lunch at Song Garden Restaurant");
//        expenseValues.put("date", "2023-02-01 00:00");
        expenseValues.put("categoryId", "2");
        expenseValues.put("notes", "Rice, Whole Fried Chicken and Soup.");
        expenseValues.put("amount", 34.50);
        db.insert("Expense", null, expenseValues);

        expenseValues = new ContentValues();
        expenseValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        expenseValues.put("title", "Bus to school");
        expenseValues.put("date", "2022-12-29 12:01:18");
        expenseValues.put("categoryId", "1");
        expenseValues.put("notes", "Bus very good");
        expenseValues.put("amount", 1.53);
        db.insert("Expense", null, expenseValues);

        expenseValues = new ContentValues();
        expenseValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        expenseValues.put("title", "Car Petrol");
        expenseValues.put("date", "2023-02-01 00:00");
        expenseValues.put("categoryId", "1");
        expenseValues.put("notes", "At Shell.");
        expenseValues.put("amount", 99.99);
        db.insert("Expense", null, expenseValues);

        // Insert data into the Budget table
        ContentValues budgetValues = new ContentValues();
        budgetValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        budgetValues.put("month", 2);
        budgetValues.put("year", 2023);
        budgetValues.put("budgetAmount", 1000.00);
        budgetValues.put("categoryId", 2);
        db.insert("Budget", null, budgetValues);

        budgetValues = new ContentValues();
        budgetValues.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        budgetValues.put("month", 12);
        budgetValues.put("year", 2022);
        budgetValues.put("budgetAmount", 543);
        budgetValues.put("categoryId", 2);
        db.insert("Budget", null, budgetValues);

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

    // Budget Queries ----->
    public ArrayList<CategoryModel> getCurrentBudgetByMonth(int month, int year, String userId) {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        String query = "SELECT Category.categoryId, IFNULL(Budget.budgetAmount, 0.00), Category.categoryName " +
                "FROM Category LEFT JOIN Budget " +
                "ON Category.categoryId = Budget.categoryId " +
                "AND (Budget.userId = ? OR Budget.userId IS NULL) " +
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

    public void setCurrentBudgetByMonth(int catId, double budgetAmount, String userId) {
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
            values.put("userId", userId);
            values.put("month", month);
            values.put("year", year);
            values.put("budgetAmount", budgetAmount);
            values.put("categoryId", catId);
            db.insert("Budget", null, values);
        }

        cursor.close();
    }


    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userId", "D64TPnbOWUfZ3GcppDQVIQkHdgb2");
        values.put("title", expense.getTitle());
        values.put("categoryId", expense.getCategory());
        values.put("notes", expense.getNotes());
        values.put("amount", expense.getAmount());

        // Inserting Row
        db.insert("Expense", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<Expense> getAllExpense() {
        List<Expense> expenseList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Expense";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setAmount(Float.parseFloat(cursor.getString(6)));
                expense.setCategory(Integer.parseInt(cursor.getString(4)));
                expense.setTitle(cursor.getString(2));
                expense.setNotes(cursor.getString(5));
                // Adding contact to list
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;
    }

    public double getSumMonthlyBudget(int month, int year, String userId) {
        double sumBudgetAmount;
        
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT IFNULL(SUM(budgetAmount), 0.00) FROM Budget WHERE userId = ? AND month = ? AND year = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, String.valueOf(month), String.valueOf(year)});

        if (cursor.moveToFirst()) {
            sumBudgetAmount = cursor.getDouble(0);
            // Use sumBudgetAmount
        } else {
            sumBudgetAmount = 0.00;
        }

        cursor.close();
        return sumBudgetAmount;
    }

    // User Queries ----->
    public void createUser(String userId, String profilePicture, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put("userId", userId);
        userValues.put("profilePicture", profilePicture);
        userValues.put("name", name);
        db.insert("User", null, userValues);
    }

    // Expense Queries ----->
    // get all expense
    public double getSumExpenseByMonth(int month, int year, String userId) {
        double sumExpenseAmount;
        String date;

        if (month < 10 && month > 0) {
            date = "%" + year + "-0" + month + "%";
        } else {
            date = "%" + year + "-" + month + "%";
        }

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT IFNULL(SUM(amount), 0.00) FROM Expense WHERE userId = ? AND date LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId, date});

        if (cursor.moveToFirst()) {
            sumExpenseAmount = cursor.getDouble(0);
            Log.d("SumExpense", "Pass with value " + date);
            // Use sumAmount
        } else {
            Log.d("SumExpense", "Fail");
            sumExpenseAmount = 0.00;
        }
        cursor.close();
        return sumExpenseAmount;
    }

    public ArrayList<ExpenseAndrewModel> getSumExpenseEachCatByMonth(int month, int year, String userId) {
        ArrayList<ExpenseAndrewModel> expenseAndrewModel = new ArrayList<>();;
        SQLiteDatabase db = this.getReadableDatabase();
        String stringMonth = "";

        String query = "SELECT Category.categoryName, SUM(Expense.amount), Expense.categoryID " +
                "FROM Expense LEFT JOIN Category " +
                "ON Expense.categoryId = Category.categoryId " +
                "WHERE userId = ? " +
                "AND strftime('%m', Expense.date) = ? " +
                "AND strftime('%Y', Expense.date) = ? " +
                "GROUP BY Expense.categoryID";

        if (("" + month).length() == 1) {
            stringMonth = "0" + month;
        } else {
            stringMonth = "" + month;
        }

        Cursor cursor = db.rawQuery(query, new String[]{userId, stringMonth, ""+year});
        while (cursor.moveToNext()) {
            String categoryName = cursor.getString(0);
            double expenseAmount = cursor.getDouble(1);

            expenseAndrewModel.add(new ExpenseAndrewModel(expenseAmount, categoryName));
        }

        cursor.close();
        return expenseAndrewModel;
    }

    // Other/Mix Queries ----->
    public ArrayList<ExpenseBudgetModel> getTotalBudgetExpenseWithinRange(String userId) {
        ArrayList<ExpenseBudgetModel> expenseBudgetModel = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // get current year and month
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        String query = "SELECT month, year, SUM(budgetAmount) AS budgetAmount " +
                "FROM Budget " +
                "WHERE userId = ? " +
                "AND (year < ? OR (year = ? AND month < ?)) " +
                "GROUP BY month, year " +
                "ORDER BY year desc, month desc " +
                "LIMIT 4;";

        Cursor cursor = db.rawQuery(query, new String[]{userId, Integer.toString(currentYear), Integer.toString(currentYear),
                Integer.toString(currentMonth)});

        Log.d("MonthYearBudgetAmount", "3. " + cursor.getCount() + " Month = " + currentMonth + " Year = " + currentYear);

        while (cursor.moveToNext()) {
            int month = cursor.getInt(0);
            int year = cursor.getInt(1);
            double budgetAmount = cursor.getDouble(2);

            Log.d("MonthYearBudgetAmount", "1" + month + year + budgetAmount);

            // Do something with the data
            String expenseQuery = "SELECT SUM(amount) AS amount " +
                    "FROM Expense " +
                    "WHERE userId = ? " +
                    "AND strftime('%m', date) = ? " +
                    "AND strftime('%Y', date) = ?";

            String[] expenseArgs = {userId, Integer.toString(month), Integer.toString(year)};

            Cursor expenseCursor = db.rawQuery(expenseQuery, expenseArgs);

            if (expenseCursor.moveToNext()) {
                double amount = expenseCursor.getDouble(0);

                Log.d("MonthYearBudgetAmount", "2" + month + year + budgetAmount + amount);

                expenseBudgetModel.add(new ExpenseBudgetModel(month, year, budgetAmount, amount));
            }

            expenseCursor.close();
        }

        cursor.close();
        return expenseBudgetModel;
    }


}
