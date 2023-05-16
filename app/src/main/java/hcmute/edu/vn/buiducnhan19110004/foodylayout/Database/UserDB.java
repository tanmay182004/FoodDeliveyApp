package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.net.ConnectException;
import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class UserDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "user";
    private final String first_col = "id";
    private final String second_col = "full_name";
    private final String third_col = "email";
    private final String fourth_col = "password";
    private final String fifth_col = "phone";

    public UserDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean CheckLoginUser(String email, String pass) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email='"+email+"' AND password='"+pass+"'", null);

        if(cursor.getCount() > 0)
            return true;

        return false;
    }

    public boolean CheckDuplicateEmail(UserDomain userDomain) {
        ArrayList<UserDomain> allUsers = SelectALlUsers();
        for (UserDomain user : allUsers) {
            if(userDomain.getEmail().equals(user.getEmail()))
                return true;
        }

        return false;
    }

    public long InsertUser(UserDomain userDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.putNull(first_col);
            contentValues.put(second_col, userDomain.getFull_name());
            contentValues.put(third_col, userDomain.getEmail());
            contentValues.put(fourth_col, userDomain.getPassword());
            contentValues.put(fifth_col, userDomain.getPhone());

            db.beginTransaction();
            long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();

            System.out.println("ID is " + row + " was inserted into table " + TABLE_NAME);

            return row;
        }
        catch (SQLiteConstraintException e) {
            System.out.println("Insert failed to table " + TABLE_NAME);
            System.out.println(e);

            return -1;
        }
    }

    public ArrayList<UserDomain> SelectALlUsers() {
        ArrayList<UserDomain> returnList = new ArrayList<UserDomain>();
        String[] projection = {first_col, second_col, third_col, fourth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int user_id = cursor.getInt(0);
                String full_name = cursor.getString(1);
                String emailResult = cursor.getString(2);
                String password = cursor.getString(3);
                String phone = cursor.getString(4);

                System.out.println("ID: " + user_id);
                System.out.println("Fullname: " + full_name);
                System.out.println("Email: " + emailResult);
                System.out.println("Password: " + password);
                System.out.println("Phone: " + phone);

                UserDomain userDomain = new UserDomain(user_id, full_name, emailResult, password, phone);
                returnList.add(userDomain);
            }

            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            db.endTransaction();

            return returnList;
        }
        catch (SQLiteConstraintException e) {
            System.out.println("Get data failed from table " + TABLE_NAME);
            db.endTransaction();

            return null;
        }
    }

    public UserDomain SelectUserByEmail(String email){
        UserDomain user;
        String[] projection = {first_col, second_col, third_col, fourth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "email = ? ", new String[]{email}, null, null, null);
            db.endTransaction();
            cursor.moveToNext();

            int user_id = cursor.getInt(0);
            String full_name = cursor.getString(1);
            String emailResult = cursor.getString(2);
            String password = cursor.getString(3);
            String phone = cursor.getString(4);

            System.out.println("User_id: "+ user_id);
            System.out.println("Full name : "+ full_name);
            System.out.println("emailResult: "+ emailResult);
            System.out.println("password: "+ password);
            System.out.println("phone: "+ phone);

            user = new UserDomain(user_id, full_name, emailResult, phone, password);

            System.out.println("Get user where id =  " + user_id + " and email = " + emailResult + " and name = " + full_name + " from " + TABLE_NAME + " successfully");

            return user;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }

    }

    public UserDomain SelectUserById(int id){
        UserDomain user;
        String[] projection = {first_col, second_col, third_col, fourth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "id = ? ", new String[]{String.valueOf(id)}, null, null, null);
            db.endTransaction();
            cursor.moveToNext();

            int user_id = cursor.getInt(0);
            String full_name = cursor.getString(1);
            String emailResult = cursor.getString(2);
            String password = cursor.getString(3);
            String phone = cursor.getString(4);

            System.out.println("User_id: "+ user_id);
            System.out.println("Full name : "+ full_name);
            System.out.println("emailResult: "+ emailResult);
            System.out.println("password: "+ password);
            System.out.println("phone: "+ phone);

            user = new UserDomain(user_id, full_name, emailResult, password, phone);

            System.out.println("Get user where id =  " + user_id + " and email = " + emailResult + " and name = " + full_name + " from " + TABLE_NAME + " successfully");

            return user;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }

    }

    public void UpdateCurrentUser(@NonNull UserDomain currentUser) {

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(second_col, currentUser.getFull_name());
            contentValues.put(third_col, currentUser.getEmail());
            contentValues.put(fourth_col, currentUser.getPassword());
            contentValues.put(fifth_col, currentUser.getPhone());

            db.beginTransaction();
            db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{String.valueOf(currentUser.getId())});
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("Update successfully user " + currentUser.getId());
        }
        catch (SQLiteException e) {
            System.out.println("Update user " + currentUser.getId() +" failed to table " + TABLE_NAME);
        }
    }

    public void DeleteUserById(int userId) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            int affected_row_num = db.delete(TABLE_NAME, "user_id = ?", new String[]{String.valueOf(userId)});
            System.out.println("Delete User where user_id = " + userId + ", row effected: " + affected_row_num );

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed where user_id = " + userId);
            return;
        }
    }

    public void DeleteAllUsers() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all User successfully");

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed");
            return;
        }
    }
}
