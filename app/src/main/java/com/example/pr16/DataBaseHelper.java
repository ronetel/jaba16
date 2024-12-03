package com.example.pr16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="book_db";
    private static final int SCHEMA=1;
    static final String TABLE_NAME="books";
    public static final String COLUMN_ID="id_book";
    public static final String COLUMN_NAME = "book_name";
    public static final String COLUMN_AUTHOR = "book_author";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AUTHOR + " TEXT)");
        //синтаксис во, повесился 3 раза пока искал лишний пробел!

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addBook(String name, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AUTHOR, author);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }



    public int deleteBookById(long bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "= ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }

    public Cursor getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?",
                new String[]{String.valueOf(bookId)});
        // пол метода в ретюрне)
    }

    public boolean updateBook(int bookId, String name, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AUTHOR, author);

        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        // как я в ретюрне могу написать условие! КАК ЭТО ВОЗМОЖНО?
        return rowsAffected > 0;
    }



}
