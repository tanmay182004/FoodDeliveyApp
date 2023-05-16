package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodVariationDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class FoodVariationDB implements Serializable {

    private FoodyDBHelper dbHelper;
    private ProductDB productDB;
    private ProductDB categoryDB;

    private final String TABLE_NAME = "food_variation";
    private final String first_col = "category_id";
    private final String second_col = "product_id";

    public FoodVariationDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long InsertFoodVariation(FoodVariationDomain foodVariationDomain){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, foodVariationDomain.getCategory_id());
            contentValues.put(second_col, foodVariationDomain.getProduct_id());
            db.beginTransaction();
            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("ID is " + row + " was inserted into table " + TABLE_NAME);
            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
            return -1;
        }
    }
    public ArrayList<FoodVariationDomain> SelectAllVariations(){
        ArrayList<FoodVariationDomain> returnList = new ArrayList<FoodVariationDomain>();
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int category_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);

                System.out.println( first_col + ": " + category_id);
                System.out.println(second_col + ": " + product_id);

                FoodVariationDomain foodVariation = new FoodVariationDomain(category_id, product_id);
                returnList.add(foodVariation);
            }
            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }public ArrayList<FoodVariationDomain> SelectAllVariationByCategoryID(int categoryID){
        ArrayList<FoodVariationDomain> returnList = new ArrayList<FoodVariationDomain>();
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "category_id = ?", new String[]{String.valueOf(categoryID)}, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int category_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);

                System.out.println( first_col + ": " + category_id);
                System.out.println(second_col + ": " + product_id);

                FoodVariationDomain foodVariation = new FoodVariationDomain(category_id, product_id);
                returnList.add(foodVariation);
            }
            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
    public void DeleteAllVariations(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all variations");
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete all variations failed");
            return;
        }
    }
}
