package com.example.dslab.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.solver.widgets.Helper;

import junit.runner.Version;

import java.util.ArrayList;
import java.util.List;

public class StudentDB {
    private static String MYDATABASE_NAME = "student_db";
    private static final String MYDATABASE_TABLE = "STUDENT_LOGIN";

    public static final String COLUMN_ID="student_id";
    public static final String KEY_STUDENT="STUDENT";
    public static final String KEY_DOB="DOB";

    static String SCRIPT_CREATE = "CREATE TABLE IF NOT EXISTS "
          +MYDATABASE_TABLE+ " " +"( "
          +COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
          +KEY_STUDENT+ " TEXT ," +
          KEY_DOB+" TEXT ); ";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private Helper helper;

    public StudentDB(Context context) {
        this.context = context;
    }
public StudentDB openToRead() throws android.database.SQLException {
        helper = new Helper(context, MYDATABASE_NAME, null, 1);
        sqLiteDatabase = helper.getReadableDatabase();
        return this;
}
    public StudentDB openToWrite() throws android.database.SQLException {
        helper = new Helper(context, MYDATABASE_NAME, null, 1);
        sqLiteDatabase = helper.getWritableDatabase();
        return this;
    }
   public void close() {
        helper.close();
   }
   public boolean isopen() {
        return sqLiteDatabase!=null && sqLiteDatabase.isOpen();

   }

   public long insert(Student student) {
       try {
           ContentValues values = new ContentValues();
           values.put(KEY_STUDENT, student.getName());
           values.put(KEY_DOB, student.getDate());

           return sqLiteDatabase.insert(MYDATABASE_TABLE, null, values);

       } catch (Exception e) {
           e.printStackTrace();
       }
       return -1;
   }

   public long update(Student student,String newdob){
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_DOB, newdob);
           String where = KEY_STUDENT+ "= ?"; //NAME + " = ? AND " + LASTNAME + " = ? "
            String[] whereArgs = new String[]{student.getName()};

            return sqLiteDatabase.update(MYDATABASE_TABLE,values,where, whereArgs);

        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
   }

   public List<Student> getStudentList() {
       List<Student> studentList = new ArrayList<>();
       try {
           Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MYDATABASE_TABLE, null);
           if (mCursor != null) {
               if (mCursor.moveToFirst()) {
                   do {
                       Student student = new Student(R.drawable.taj, mCursor.getString(mCursor.getColumnIndex(KEY_STUDENT)), mCursor.getString(mCursor.getColumnIndex(KEY_DOB)));
                       studentList.add(student);
                   } while (mCursor.moveToNext());
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return studentList;
   }


    class Helper extends SQLiteOpenHelper {
        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
           db.execSQL(SCRIPT_CREATE);
            System.out.println("********************************");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}

