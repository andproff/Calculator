package com.goroshevsky.calculator.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by vadim on 10-Jul-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "calculator.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "calc_data";
    public static final String USERNAME_COLUMN = "user_name";
    public static final String NR_BUTTONS_TAPPED_COLUMN = "nr_buttons_tapped";
    public static final String ENTERED_DATA = "data";
    public static final String RESULT = "result";


    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + USERNAME_COLUMN
            + " text not null, " + NR_BUTTONS_TAPPED_COLUMN + " integer, " + ENTERED_DATA
            + " text, " + RESULT + ");";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Update from version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, null, null, null, null, null, null);
    }


}
