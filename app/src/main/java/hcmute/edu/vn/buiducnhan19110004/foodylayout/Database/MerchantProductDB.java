package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantProductDomain;

public class MerchantProductDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "merchant_product";
    private final String first_col = "merchant_id";
    private final String second_col = "product_id";

    public MerchantProductDB(FoodyDBHelper dBHelper) {
        this.dbHelper = dBHelper;
    }

    public long InsertMerchantProduct(MerchantProductDomain merchantProductDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, merchantProductDomain.getMerchant_id());
            contentValues.put(second_col, merchantProductDomain.getProduct_id());

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

    public MerchantProductDomain SelectMerchantFoodByProductId(int productId) {
        MerchantProductDomain merchantProduct = null;
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "product_id = ? ", new String[]{String.valueOf(productId)}, null, null, null);
            db.endTransaction();
            if(cursor != null && cursor.moveToFirst()) {

                int merchant_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);

                System.out.println("merchant_id: "+ merchant_id);
                System.out.println("product_id: "+ product_id);

                merchantProduct = new MerchantProductDomain(merchant_id, product_id);

                System.out.println("Get data at Merchant's ID: " + merchant_id + " successfully from table " + TABLE_NAME);
            }

            return merchantProduct;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }

    public ArrayList<Integer> SelectFoodIdListByMerchantId(int merchantId) {
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "merchant_id = ? ", new String[]{String.valueOf(merchantId)}, null, null, null);
            db.endTransaction();
            if(cursor != null && cursor.moveToFirst()) {
                while(cursor.moveToNext()) {
                    int product_id = cursor.getInt(1);
                    returnList.add(product_id);

                    System.out.println("product_id: "+ product_id);
                }
            }
            else {
                System.out.println("Get product failed at merchant_id = " + merchantId);
            }

            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
}
