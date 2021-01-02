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

public class DataBaseHelperBmi extends SQLiteOpenHelper {

    public static final String BMI_TABLE = "BMI_TABLE";
    public static final String COLUMN_BMI_DATA = "COLUMN_BMI_DATA";
    public static final String COLUMN_BMI = "COLUMN_BMI";
    public static final String COLUMN_ID = "ID";


    public DataBaseHelperBmi(@Nullable Context context, int version) {
        super(context, "bmii.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + BMI_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BMI_DATA +" TEXT, " + COLUMN_BMI + " TEXT )";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean addOne (BmiModel bmiModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BMI_DATA, bmiModel.getData());
        cv.put(COLUMN_BMI, bmiModel.getBmi());
        long insert =   db.insert(BMI_TABLE, null, cv);
        if( insert == -1){
            return false;
        }else{
            return true;
        }
    }


    public boolean deleteOne(BmiModel bmiModel){
//usuwanie cwiczenia z bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString  = "DELETE FROM " + BMI_TABLE + " WHERE " + COLUMN_ID + " = " + bmiModel.getId();
        Cursor cursor=  db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;

        }else {
            return false;
        }
    }




    public BmiModel getOne(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {COLUMN_ID, COLUMN_BMI_DATA,COLUMN_BMI};
        Cursor cursor=  db.query(BMI_TABLE,query,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new BmiModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)



        );

    }

    public long getBmiCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, BMI_TABLE);
        db.close();
        return count;
    }

    public List<BmiModel> getEveryone(){
        List <BmiModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + BMI_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);


        if (cursor.moveToFirst()){
            do {
                int bmiID = cursor.getInt(0);
                String bmiData = cursor.getString(1);
                String bmibmi = cursor.getString(2);
                BmiModel newBmiModel = new BmiModel(bmiID,bmiData,bmibmi);

                returnList.add(newBmiModel);

            } while (cursor.moveToNext());



        }else{



        }   cursor.close();
        db.close();
        return returnList;
    }
}
