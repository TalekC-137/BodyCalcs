package com.example.bmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperBmr extends SQLiteOpenHelper {

    public static final String BMR_TABLE = "BMR_TABLE";
    public static final String COLUMN_BMR_DATE = "COLUMN_BMR_DATE";
    public static final String COLUMN_BMR = "COLUMN_BMR";
    public static final String COLUMN_BMR_NEED = "COLUMN_NEEDED";
    public static final String COLUMN_ID = "ID";


    public DataBaseHelperBmr(@Nullable Context context, int version) {
        super(context, "bmrrr.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + BMR_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BMR_DATE +" TEXT, " + COLUMN_BMR + " TEXT, " +  COLUMN_BMR_NEED + " TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean addOne (BmrModel bmrModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BMR_DATE, bmrModel.getData());
        cv.put(COLUMN_BMR, bmrModel.getBmr());
        cv.put(COLUMN_BMR_NEED, bmrModel.getNeeded());
        long insert =   db.insert(BMR_TABLE, null, cv);
        if( insert == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteOne(BmrModel bmrModel){
//usuwanie cwiczenia z bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString  = "DELETE FROM " + BMR_TABLE + " WHERE " + COLUMN_ID + " = " + bmrModel.getId();
        Cursor cursor=  db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;

        }else {
            return false;
        }
    }
    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BMR_TABLE,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public BmrModel getOne(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {COLUMN_ID, COLUMN_BMR_DATE,COLUMN_BMR, COLUMN_BMR_NEED};
        Cursor cursor=  db.query(BMR_TABLE,query,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();


        return new BmrModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)



        );

    }

    public long getBmrCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, BMR_TABLE);
        db.close();
        return count;
    }





    public List<BmrModel> getEveryone(){
        List <BmrModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + BMR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);


        if (cursor.moveToFirst()){
            do {
                int bmrID = cursor.getInt(0);
                String bmrData = cursor.getString(1);
                String bmrbmr = cursor.getString(2);
                String bmrNeed = cursor.getString(3);
                BmrModel newBmrModel = new BmrModel(bmrID,bmrData,bmrbmr,bmrNeed);

                returnList.add(newBmrModel);

            } while (cursor.moveToNext());



        }else{



        }   cursor.close();
        db.close();
        return returnList;
    }
}

