package com.example.traintickets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseConnector extends SQLiteOpenHelper {

    public DatabaseConnector(@Nullable Context context) {
        super(context, "Train.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user_accounts(email text PRIMARY KEY, firstName text, lastName text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_accounts");
    }

    public boolean insert(String email, String firstName, String lastName, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("password", password);

        long insertionReturn = database.insert("user_accounts", null, contentValues);
        if(insertionReturn== -1) {
            return false;
        }
        return true;
    }

    public boolean findEmail(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT email FROM user_accounts WHERE email = ?", new String[]{email});
        int count = cursor.getCount();
        if(count == 0) {
            return false;
        }
        return true;
    }

    public boolean findRecord(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT email FROM user_accounts WHERE email = ? AND password = ?", new String[]{email, password});
        int count = cursor.getCount();
        if(count == 0) {
            return false;
        }
        return true;
    }

    public String[] returnRecord(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT email, firstName, lastName, password FROM user_accounts WHERE email = ?", new String[]{email});
        if(cursor != null) {
            cursor.moveToFirst();
            String []columns = new String[4];
            columns[0] = cursor.getString(0);
            columns[1] = cursor.getString(1);
            columns[2] = cursor.getString(2);
            columns[3] = cursor.getString(3);
            return columns;
        }
        return null;
    }

    public void updateEmail(String oldEmail, String newEmail) {
        SQLiteDatabase database = this.getWritableDatabase();
        String []columns = returnRecord(oldEmail);
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", newEmail);
        contentValues.put("firstName", columns[1]);
        contentValues.put("lastName", columns[2]);
        contentValues.put("password", columns[3]);
        database.update("user_accounts", contentValues, "email = ?", new String[]{oldEmail});
    }
}
