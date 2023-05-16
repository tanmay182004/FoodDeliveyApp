package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.TransactionDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class TransactionDB {
    private FoodyDBHelper dbHelper;

    private final String TABLE_NAME = "transaction_history";
    private final String first_col = "user_id";
    private final String second_col = "product_id";
    private final String third_col = "quantity";
    private final String fourth_col = "transaction_time";

    public TransactionDB (FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long InsertTransaction(TransactionDomain transactionDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, transactionDomain.getUser_id());
            contentValues.put(second_col, transactionDomain.getProduct_id());
            contentValues.put(third_col, transactionDomain.getQuantity());
            contentValues.put(fourth_col, transactionDomain.getTransaction_time());

            db.beginTransaction();
            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();

            System.out.println("User ID is " + transactionDomain.getUser_id() +
                    " and product id = " + transactionDomain.getProduct_id() + " and quantity = "
                    + transactionDomain.getQuantity() + " and transaction time = " + transactionDomain.getTransaction_time()
                    + " was inserted into table " + TABLE_NAME);

            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
            System.out.println(e);
            return -1;
        }
    }

    public TransactionDomain SelectTransactionByProductID(int productId) {
        TransactionDomain transactionDomain;
        String[] projection = {first_col, second_col, third_col, fourth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(productId)}, null, null, null);
            db.endTransaction();
            cursor.moveToNext();

            int user_id = cursor.getInt(0);
            int product_id = cursor.getInt(1);
            int quantity = cursor.getInt(2);
            String transaction_time = cursor.getString(3);

            System.out.println("User_id: "+ user_id);
            System.out.println("Product_id: "+ product_id);
            System.out.println("Quantity: "+ quantity);
            System.out.println("Transaction_time: "+ transaction_time);

            transactionDomain = new TransactionDomain(user_id, product_id, quantity, transaction_time);

            System.out.println("Get transaction item of user_id = " + user_id + " and product_id = " + product_id + " data from " + TABLE_NAME + " successfully");

            return transactionDomain;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }

    public ArrayList<TransactionDomain> SelectAllTransactionOfCurrentUser(){
        ArrayList<TransactionDomain> returnList = new ArrayList<TransactionDomain>();
        String[] projection = {first_col, second_col, third_col, fourth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? ", new String[]{String.valueOf(CurrentUser.getUser_id())}, null, null, null);
            db.endTransaction();

            if(cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()){
                    int user_id = cursor.getInt(0);
                    int product_id = cursor.getInt(1);
                    int quantity = cursor.getInt(2);
                    String transaction_time = cursor.getString(3);

                    System.out.println("User_id: "+ user_id);
                    System.out.println("Product_id: "+ product_id);
                    System.out.println("Quantity: "+ quantity);
                    System.out.println("Transaction_time: "+ transaction_time);

                    TransactionDomain transactionDomain = new TransactionDomain(user_id, product_id, quantity, transaction_time);
                    returnList.add(transactionDomain);

                    System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
                }
            }

            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }

    public void DeleteTransactionsOfCurrentUser() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            int affected_row_num = db.delete(TABLE_NAME, "user_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id())});
            System.out.println("Delete all items in order history where user_id = " + CurrentUser.getUser_id() + ", row effected: " + affected_row_num );

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed in order history where user_id = " + CurrentUser.getUser_id());
            return;
        }
    }

    public void DeleteAllTransactions() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all order history of all users");

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete all order history of all user failed");
            return;
        }
    }
}
