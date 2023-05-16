package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantDomain;

public class MerchantDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "merchant";
    private final String first_col = "id";
    private final String second_col = "name";
    private final String third_col = "address";
    private final String fourth_col = "pic";

    public MerchantDB(FoodyDBHelper dBHelper) {
        this.dbHelper = dBHelper;
    }

    public long InsertMerchant(MerchantDomain merchantDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, merchantDomain.getId());
            contentValues.put(second_col, merchantDomain.getName());
            contentValues.put(third_col, merchantDomain.getAddress());
            contentValues.put(fourth_col, merchantDomain.getPic());

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

    public MerchantDomain SelectMerchantById(int merchant_id) {
        MerchantDomain merchant = null;
        String[] projection = {first_col, second_col, third_col, fourth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "id = ? ", new String[]{String.valueOf(merchant_id)}, null, null, null);
            db.endTransaction();
            if(cursor != null && cursor.moveToFirst()) {

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String pic = cursor.getString(3);

                System.out.println("ID: "+ id);
                System.out.println("name: "+ name);
                System.out.println("address: "+ address);

                merchant = new MerchantDomain(id, name, address, pic);

                System.out.println("Get data at ID: " + id + " successfully from table " + TABLE_NAME);
            }

            return merchant;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
}
