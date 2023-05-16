package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;

public class ProductDB {

    private final String TABLE_NAME = "product";
    public static final String first_col = "id";
    public static final String second_col = "name";
    public static final String third_col = "pic";
    public static final String forth_col = "description";
    public static final String fifth_col = "price";

    private FoodyDBHelper dbHelper;

    public ProductDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long InsertProduct(FoodDomain foodDomain){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, foodDomain.getId());
            contentValues.put(second_col, foodDomain.getTitle());
            contentValues.put(third_col, foodDomain.getPic());
            contentValues.put(forth_col, foodDomain.getDescription());
            contentValues.put(fifth_col, foodDomain.getFee());
            db.beginTransaction();
            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("ID is " + row + " was inserted");
            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed");
            return -1;
        }
    }
    public void DeleteProduct(int ID){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(ID)} );
            System.out.println("Delete at id" + ID + " from table " + TABLE_NAME);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed at id + ID");
            return;
        }
    }
    public void DeleteAllProducts(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            int affected_row = db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all product from " + TABLE_NAME + " affected row = " + affected_row);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed from " + TABLE_NAME);
            return;
        }
    }
    public ArrayList<FoodDomain> SelectAllProducts(){
        ArrayList<FoodDomain> returnList = new ArrayList<FoodDomain>();
        String[] projection = {first_col, second_col, third_col, forth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String pic = cursor.getString(2);
                String description = cursor.getString(3);
                Double price = cursor.getDouble(4);

                System.out.println("ID: "+ id);
                System.out.println("name: "+ name);
                System.out.println("pic: "+ pic);
                System.out.println("description: "+ description);
                System.out.println("price: "+ price);

                FoodDomain food = new FoodDomain(id, name, pic, description, price);
                returnList.add(food);
            }
            System.out.println("Get " + returnList.size() + " rows of data successfully from table " + TABLE_NAME);
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
    public FoodDomain SelectProductByName(String productName){
        FoodDomain food = null;
        String[] projection = {first_col, second_col, third_col, forth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "name = ? ", new String[]{productName}, null, null, null);
            db.endTransaction();

            if(cursor != null && cursor.moveToFirst()) {
                cursor.moveToNext();

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String pic = cursor.getString(2);
                String description = cursor.getString(3);
                Double price = cursor.getDouble(4);


                System.out.println("ID: "+ id);
                System.out.println("name: "+ name);
                System.out.println("pic: "+ pic);
                System.out.println("description: "+ description);
                System.out.println("price: "+ price);

                food = new FoodDomain(id, name, pic, description, price);

                System.out.println("Get data at ID: " + id + " successfully from table " + TABLE_NAME);
            }

            return food;
            }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
    public FoodDomain SelectProductByID(int productID){
        FoodDomain food = null;
        String[] projection = {first_col, second_col, third_col, forth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "id = ? ", new String[]{String.valueOf(productID)}, null, null, null);
            db.endTransaction();
            if(cursor != null && cursor.moveToFirst()) {

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String pic = cursor.getString(2);
                String description = cursor.getString(3);
                Double price = cursor.getDouble(4);

                System.out.println("ID: "+ id);
                System.out.println("name: "+ name);
                System.out.println("pic: "+ pic);
                System.out.println("description: "+ description);
                System.out.println("price: "+ price);

                food = new FoodDomain(id, name, pic, description, price);

                System.out.println("Get data at ID: " + id + " successfully from table " + TABLE_NAME);
            }
            else {
                System.out.println("Get product failed at product_id = " + productID);
            }

            return food;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
    public ArrayList<FoodDomain> SearchProductByKeyWord(String productName){
        ArrayList<FoodDomain> returnList = new ArrayList<FoodDomain>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projection = {first_col, second_col, third_col, forth_col, fifth_col};

        String query = //"SELECT * FROM " + TABLE_NAME + " WHERE " +
                "UPPER(name) LIKE UPPER('" + productName + "%') " +
                "or " +
                "UPPER(name) LIKE UPPER('%" + productName + "%') " +
                "or " +
                "UPPER(name) LIKE UPPER('" + productName + "_') " +
                "or " +
                "UPPER(name) LIKE UPPER('_" + productName + "') " +
                "or " +
                "UPPER(name) LIKE UPPER('_" + productName + "_') " +
                        "or " +
                        "UPPER(description) LIKE UPPER('" + productName + "%') " +
                        "or " +
                        "UPPER(description) LIKE UPPER('%" + productName + "%') " +
                        "or " +
                        "UPPER(description) LIKE UPPER('" + productName + "_') " +
                        "or " +
                        "UPPER(description) LIKE UPPER('_" + productName + "') " +
                        "or " +
                        "UPPER(description) LIKE UPPER('_" + productName + "_')";


        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, query, null, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String pic = cursor.getString(2);
                String description = cursor.getString(3);
                Double price = cursor.getDouble(4);

                System.out.println("ID: "+ id);
                System.out.println("name: "+ name);
                System.out.println("pic: "+ pic);
                System.out.println("description: "+ description);
                System.out.println("price: "+ price);

                FoodDomain food = new FoodDomain(id, name, pic, description, price);
                returnList.add(food);
            }
            System.out.println("Get " + returnList.size() + " rows of data successfully from table " + TABLE_NAME);
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
}
