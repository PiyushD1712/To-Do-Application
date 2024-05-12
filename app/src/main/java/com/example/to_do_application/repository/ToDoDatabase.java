package com.example.to_do_application.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_do_application.model.Work;

import java.util.ArrayList;
import java.util.List;

public class ToDoDatabase extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="todoApp.db";
    private final static int DATABASE_VERSION=1;
    private final static String TABLE_NAME="todoList";
    private final static String COLUMN_ID="id";
    private final static String COLUMN_TASK="task";
    private final static String COLUMN_DATE="date";

    public ToDoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "Create Table "+TABLE_NAME+"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_TASK+" TEXT,"+COLUMN_DATE+" TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
    public void insertTask(Work work){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK,work.getTask());
        contentValues.put(COLUMN_DATE,work.getDate());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public List<Work> getTasks(){
        List<Work> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT *from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));

            Work work = new Work(id,task,date);
            list.add(0,work);
        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteTask(Work work){
        SQLiteDatabase db = getWritableDatabase();
        String whereClaus = COLUMN_ID+"=?";
        String[] whereArgs = new String[]{String.valueOf(work.getId())};
        db.delete(TABLE_NAME,whereClaus,whereArgs);
        db.close();
    }
    public void updateTask(Work work){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK,work.getTask());
        contentValues.put(COLUMN_DATE,work.getDate());
        String whereClaus = COLUMN_ID+"=?";
        String[] whereArgs = new String[]{String.valueOf(work.getId())};
        db.update(TABLE_NAME,contentValues,whereClaus,whereArgs);
        db.close();
    }
    public Work getTaskFromId(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select *from "+TABLE_NAME+" where "+COLUMN_ID+" = "+id;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int idNumber = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
        cursor.close();
        db.close();
        return new Work(idNumber,task,date);
    }
}
