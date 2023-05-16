package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;

public class CategoryDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "category";
    private final String first_col = "id";
    private final String second_col = "title";
    private final String third_col = "pic";

    public CategoryDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long InsertCategory(CategoryDomain categoryDomain){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{

            ContentValues contentValues = new ContentValues();
            contentValues.putNull(first_col);
            contentValues.put(second_col, categoryDomain.getTitle());
            contentValues.put(third_col, categoryDomain.getPic());
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
    public void DeleteCategory(int ID){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(ID)} );
            System.out.println("Delete at id" + ID + " from table " + TABLE_NAME);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed at id " + ID + " from table " + TABLE_NAME);
            return;
        }
    }
    public void DeleteAllCategories(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, null, null);

            System.out.println("Delete all categories");
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed");
            return;
        }
    }
    public ArrayList<CategoryDomain> SelectAllCategories(){
        ArrayList<CategoryDomain> returnList = new ArrayList<CategoryDomain>();
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String pic = cursor.getString(2);

                System.out.println("ID: "+ id);
                System.out.println("Title: "+ title);
                System.out.println("Pic: "+ pic);

                CategoryDomain category = new CategoryDomain(id, title, pic);
                returnList.add(category);
                System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            }
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
//    public ArrayList<CategoryDomain> SelectCategoryByName(String category){
//        ArrayList<FoodDomain> returnList = new ArrayList<FoodDomain>();
//        String[] projection = {first_rol, second_col, third_col};
//        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
//
//        Cursor cursor;
//        db.beginTransaction();
//        try{
//            cursor = db.query(TABLE_NAME, projection, "WHERE name = ? ", new String[]{productName}, null, null, null);
//            while (cursor.moveToNext()){
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                String pic = cursor.getString(2);
//                String description = cursor.getString(3);
//                Double price = cursor.getDouble(4);
//
//                System.out.println("ID: "+ id);
//                System.out.println("name: "+ name);
//                System.out.println("pic: "+ pic);
//                System.out.println("description: "+ description);
//                System.out.println("price: "+ price);
//
//                FoodDomain food = new FoodDomain(id, name, pic, description, price);
//                returnList.add(food);
//            }
//            System.out.println("Get " + returnList.size() + " rows of data successfully from table " + TABLE_NAME);
//            db.endTransaction();
//            return returnList;
//        }
//        catch (SQLiteConstraintException e){
//            System.out.println("Get data failed from table " + TABLE_NAME);
//            db.endTransaction();
//            return null;
//        }
//    }
}
