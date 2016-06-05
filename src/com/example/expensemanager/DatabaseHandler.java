package com.example.expensemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	public static final String DATABASE = "database";
	public static final int VERSION = 1;

	// TABLE EXPENSE
	public static final String TABLE_EXPENSE = "expense";
	public static final String EXP_ID = "id";
	public static final String EXP_AMOUNT = "amount";
	public static final String EXP_CATEGORY = "category";
	public static final String EXP_DESCRIPTION = "description";
	public static final String EXP_DATE = "date";
	public static final String EXP_MONTH = "month";
	public static final String EXP_YEAR = "year";
	public static final String EXP_MODE = "mode";

	// table income
	public static final String TABLE_INCOME = "income";
	public static final String INC_ID = "id";
	public static final String INC_AMOUNT = "amount";
	public static final String INC_SOURCE = "source";
	public static final String INC_DESCRIPTION = "description";
	public static final String INC_DATE = "date";
	public static final String INC_MONTH = "month";
	public static final String INC_YEAR = "year";
	public static final String INC_MODE = "mode";

	public DatabaseHandler(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// table expense
		String CREATE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + " ( "
				+ EXP_ID + " TEXT, " + EXP_AMOUNT + " REAL, " + EXP_CATEGORY
				+ " TEXT, " + EXP_DESCRIPTION + " TEXT, " + EXP_DATE
				+ " INTEGER, " + EXP_MONTH + " INTEGER, " + EXP_YEAR
				+ " INTEGER, " + EXP_MODE + " TEXT);";
		db.execSQL(CREATE_EXPENSE);

		// table income
		String CREATE_INCOME = "CREATE TABLE " + TABLE_INCOME + " ( " + INC_ID
				+ " TEXT, " + INC_AMOUNT + " REAL, " + INC_SOURCE + " TEXT, "
				+ INC_DESCRIPTION + " TEXT, " + INC_DATE + " INTEGER, "
				+ INC_MONTH + " INTEGER, " + INC_YEAR + " INTEGER, " + INC_MODE
				+ " TEXT);";
		db.execSQL(CREATE_INCOME);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// table expense
	void insertExpense(Model obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		String id = obj.getId();
		double amount = obj.getAmount();
		String category = obj.getCategory();
		String description = obj.getDescription();
		int date = obj.getDate();
		int month = obj.getMonth();
		int year = obj.getYear();
		String mode = obj.getMode();
		ContentValues values = new ContentValues();
		values.put(EXP_ID, id);
		values.put(EXP_AMOUNT, amount);
		values.put(EXP_CATEGORY, category);
		values.put(EXP_DESCRIPTION, description);
		values.put(EXP_DATE, date);
		values.put(EXP_MONTH, month);
		values.put(EXP_YEAR, year);
		values.put(EXP_MODE, mode);
		db.insert(TABLE_EXPENSE, null, values);
		db.close();

		// Log.e("shilpi",id+" "+amount+" "+category+" "+description+" "+date+" "+month+" "+year+" "+mode+" added ");
	}

	List<Model> getExpense() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Model> list = new ArrayList<Model>();
		Cursor cursor;
		cursor = db.rawQuery(" SELECT * FROM " + TABLE_EXPENSE + " ORDER BY "
				+ EXP_YEAR + " , " + EXP_MONTH + " , " + EXP_DATE + " ;", null);
		if (cursor.moveToLast()) {
			do {
				String id = cursor.getString(0);
				double amount = cursor.getDouble(1);
				String category = cursor.getString(2);
				String description = cursor.getString(3);
				int date = cursor.getInt(4);
				int month = cursor.getInt(5);
				int year = cursor.getInt(6);
				String mode = cursor.getString(7);
				Model obj = new Model(id, amount, category, description, date,
						month, year, mode);
				list.add(obj);

			} while (cursor.moveToPrevious());
			return list;
		} 
		return list;
	}

	double getTotalExpense() {
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + ";", null);
		if (cursor.moveToFirst()) {
			do {
				sum += cursor.getDouble(1);
			} while (cursor.moveToNext());
		}
		// Log.e("handler",sum);
		return sum;

	}

	double MonthlyExpense(int month, int year) {
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM  " + TABLE_EXPENSE + " WHERE "
				+ EXP_MONTH + " = " + month + " AND " + EXP_YEAR + " = " + year
				+ " ;", null);

		if (cursor.moveToFirst()) {
			do {
				Log.e("handler", "" + cursor.getDouble(1));
				sum = sum + cursor.getDouble(1);

			} while (cursor.moveToNext());
		}

		return sum;

	}

	double CategoryExpense(String s, int year) {
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE "
				+ EXP_CATEGORY + " = '" + s + "' AND " + EXP_YEAR + " = "
				+ year + ";", null);

		if (cursor.moveToFirst()) {
			do {
				sum += cursor.getDouble(1);
			} while (cursor.moveToNext());
		}
		Log.e("Handler", s + " : " + sum);
		return sum;
	}

	Model getExpense(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Model obj = new Model();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE "
				+ EXP_ID + " = '" + id.toString() + "';", null);
		if (cursor.moveToFirst()) {
			obj.setId(id);
			obj.setAmount(cursor.getDouble(1));
			obj.setCategory(cursor.getString(2));
			obj.setDescription(cursor.getString(3));
			obj.setDate(cursor.getInt(4));
			obj.setMonth(cursor.getInt(5));
			obj.setYear(cursor.getInt(6));
			obj.setMode(cursor.getString(7));
		} else
			obj = null;
		return obj;
	}

	void deleteExpense(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_EXPENSE, EXP_ID + " = '" + id + "'", null);
		db.close();
	}

	// table income
	void insertIncome(IncomeModel obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		String ID = obj.getId();
		double amount = obj.getAmount();
		String source = obj.getSource();
		String description = obj.getDescription();
		int date = obj.getDate();
		int month = obj.getMonth();
		int year = obj.getYear();
		String mode = obj.getMode();
		ContentValues values = new ContentValues();
		values.put(INC_ID, ID);
		values.put(INC_AMOUNT, amount);
		values.put(INC_SOURCE, source);
		values.put(INC_DESCRIPTION, description);
		values.put(INC_DATE, date);
		values.put(INC_MONTH, month);
		values.put(INC_YEAR, year);
		values.put(INC_MODE, mode);
		db.insert(TABLE_INCOME, null, values);
		db.close();
	}

	List<IncomeModel> getIncome() {
		List<IncomeModel> list = new ArrayList<IncomeModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery(" SELECT * FROM " + TABLE_INCOME + " ORDER BY "
				+ INC_YEAR + " , " + INC_MONTH + " , " + INC_DATE + " ;", null);
		if (cursor.moveToLast()) {
			do {
				String id = cursor.getString(0);
				double amount = cursor.getDouble(1);
				String source = cursor.getString(2);
				String description = cursor.getString(3);
				int date = cursor.getInt(4);
				int month = cursor.getInt(5);
				int year = cursor.getInt(6);
				String mode = cursor.getString(7);
				IncomeModel obj = new IncomeModel(id, amount, source,
						description, date, month, year, mode);
				list.add(obj);
			} while (cursor.moveToPrevious());
			return list;
		} 
		return list;
	}

	double MonthlyIncome(int month, int year) {
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME + " WHERE "
				+ INC_MONTH + " = " + month + " AND " + INC_YEAR + " = " + year
				+ ";", null);
		if (cursor.moveToFirst()) {
			do {
				sum += cursor.getDouble(1);
			} while (cursor.moveToNext());
		}

		return sum;

	}

	IncomeModel getIncome(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		IncomeModel obj = new IncomeModel();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME + " WHERE "
				+ EXP_ID + " = '" + id.toString() + "';", null);
		if (cursor.moveToFirst()) {
			obj.setId(id);
			obj.setAmount(cursor.getDouble(1));
			obj.setSource(cursor.getString(2));
			obj.setDescription(cursor.getString(3));
			obj.setDate(cursor.getInt(4));
			obj.setMonth(cursor.getInt(5));
			obj.setYear(cursor.getInt(6));
			obj.setMode(cursor.getString(7));
		} else
			obj = null;
		return obj;
	}

	void deleteIncome(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_INCOME, EXP_ID + " = '" + id + "'", null);
		db.close();
	}

	double getTotalIncome() {
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME, null);
		if (cursor.moveToFirst()) {
			do {
				sum+=cursor.getDouble(1);

			} while (cursor.moveToNext());
		}

		return sum;
	}

}
