package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class CartDB {
    private FoodyDBHelper dbHelper;
    private ProductDB productDB;

    private final String TABLE_NAME = "cart";
    private final String first_col = "user_id";
    private final String second_col = "product_id";
    private final String third_col = "quantity";

    public CartDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.productDB  = new ProductDB(dbHelper);
    }

    public long InsertCart(CartDomain cart){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, cart.getUser_id());
            contentValues.put(second_col, cart.getProduct_id());
            contentValues.put(third_col, cart.getQuantity());
            db.beginTransaction();
            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("User ID is " + cart.getUser_id() +
                    " and product id = " + cart.getProduct_id() + " and quantity = "
                    + cart.getQuantity() + " was inserted into table " + TABLE_NAME);
            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
            System.out.println(e);
            return -1;
        }
    }
    public void DeleteCartItemByID(int product_id){

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(product_id)} );
            System.out.println("Delete at userid = " + CurrentUser.getUser_id() + " and product id " + product_id + " from table " + TABLE_NAME);

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed at userid = " + CurrentUser.getUser_id() + " and product id " + product_id + " from table " + TABLE_NAME);
            return;
        }
    }
    public void DeleteAllItemsInCart(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            int affected_row_num = db.delete(TABLE_NAME, "user_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id())});
            System.out.println("Delete all items in cart where user_id = " + CurrentUser.getUser_id() + ", row effected: " + affected_row_num );

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed in cart where user_id = " + CurrentUser.getUser_id());
            return;
        }
    }
    public void ClearCartTable(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all cart item of all users");

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed");
            return;
        }
    }

    public ArrayList<CartDomain> SelectAllItemsInCartOfCurrentUser(){
        ArrayList<CartDomain> returnList = new ArrayList<CartDomain>();
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? ", new String[]{String.valueOf(CurrentUser.getUser_id())}, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int user_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);
                int quantity = cursor.getInt(2);

                System.out.println("User_id: "+ user_id);
                System.out.println("Product_id: "+ product_id);
                System.out.println("Quantity: "+ quantity);

                CartDomain cartItem = new CartDomain(user_id, product_id, quantity);
                returnList.add(cartItem);

                System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            }
            cursor.close();
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
    public ArrayList<CartDomain> SelectAllItemsInCartOfAllUsers(){
        ArrayList<CartDomain> returnList = new ArrayList<CartDomain>();
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int user_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);
                int quantity = cursor.getInt(2);

                System.out.println("User_id: "+ user_id);
                System.out.println("Product_id: "+ product_id);
                System.out.println("Quantity: "+ quantity);

                CartDomain cartItem = new CartDomain(user_id, product_id, quantity);
                returnList.add(cartItem);
                System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            }
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            System.out.println(e);
            return null;
        }
    }
    public Double CalculateCart(){
        ArrayList<CartDomain> cartItems = this.SelectAllItemsInCartOfCurrentUser();
        Double total = Double.valueOf(0);
        for(CartDomain cartItem: cartItems){
            FoodDomain product = productDB.SelectProductByID(cartItem.getProduct_id());
            total = total + (product.getFee() * Double.valueOf(cartItem.getQuantity()) );
        }
        return total;
    }
    public int CountCart(){
        ArrayList<CartDomain> cartItems = this.SelectAllItemsInCartOfCurrentUser();
        return cartItems.size();
    }

    public CartDomain SelectCartByProductID(int productID){
        CartDomain cartItem;
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(productID)}, null, null, null);
            db.endTransaction();
            cursor.moveToNext();

            int user_id = cursor.getInt(0);
            int product_id = cursor.getInt(1);
            int quantity = cursor.getInt(2);

            System.out.println("User_id: "+ user_id);
            System.out.println("Product_id: "+ product_id);
            System.out.println("Quantity: "+ quantity);

            cursor.close();

            cartItem = new CartDomain(user_id, product_id, quantity);

            System.out.println("Get cart item of user_id = " + user_id + " and product_id = " + product_id + " data from " + TABLE_NAME + " successfully");

            return cartItem;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }

    }
    // Add Quantity By One
    public void PlusOneQuantity(int productID){
        CartDomain cartItem = this.SelectCartByProductID(productID);
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, cartItem.getUser_id());
            contentValues.put(second_col, cartItem.getProduct_id());
            contentValues.put(third_col, cartItem.getQuantity());
            db.beginTransaction();
            db.update(TABLE_NAME, contentValues, "product_id = ? ", new String[]{String.valueOf(productID)});
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("User ID is " + cartItem.getUser_id() +
                    " and product id = " + cartItem.getProduct_id() + " quantity = "
                    + cartItem.getQuantity() + " was inserted into table " + TABLE_NAME);

        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
        }
    }
    // Minus Quantity By One
    public void MinusOneQuantity(int productID){
        CartDomain cartItem = this.SelectCartByProductID(productID);
        if (cartItem.getQuantity() > 0){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();

            try{

                ContentValues contentValues = new ContentValues();
                contentValues.put(first_col, cartItem.getUser_id());
                contentValues.put(second_col, cartItem.getProduct_id());
                contentValues.put(third_col, cartItem.getQuantity());
                db.beginTransaction();

                db.update(TABLE_NAME, contentValues, "product_id = ? ", new String[]{String.valueOf(productID)});
                db.setTransactionSuccessful();
                db.endTransaction();

                System.out.println("User ID is " + cartItem.getUser_id() +
                        " and product id = " + cartItem.getProduct_id() + " quantity = "
                        + cartItem.getQuantity() + " was inserted into table " + TABLE_NAME);

            }
            catch (SQLiteConstraintException e){
                System.out.println("Insert failed to table " + TABLE_NAME);
            }
        }
        else {
            System.out.println("Quantity = 0");
        }
    }
    public boolean isCartExists(int productID){
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(productID)}, null, null, null);
            db.endTransaction();
            if(cursor.getCount() > 0){
                System.out.println("Item exists int cart of of user_id = " + CurrentUser.getUser_id() + " and product_id = " + productID + " data from " + TABLE_NAME);

                return true;
            }
            else{
                System.out.println("Item does not exist int cart of of user_id = " + CurrentUser.getUser_id()  + " and product_id = " + productID + " data from " + TABLE_NAME);

                return false;
            }
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return false;
        }
    }
}
