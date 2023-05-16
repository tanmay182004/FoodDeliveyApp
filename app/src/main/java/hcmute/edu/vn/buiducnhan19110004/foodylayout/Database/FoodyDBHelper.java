package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FoodyDBHelper extends SQLiteOpenHelper {
    public FoodyDBHelper(@Nullable Context context) {
        super(context, "foody.db", null, 12);
    }
    // Ham Delete khong duoc de db.beginTransaction va db.endTransaction
    // Ham Insert phai co db.setTransactionSuccessful();

    // truy vấn không trả kết quả: CREATE, INSERT, UPDATE, DELETE
    public void QueryData(String query){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
    }
    // truy vấn có trả kết quả: SELECT
    public Cursor GetData(String query){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(query, null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NUll, full_name text, email text, password text, phone text)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS product (id INTEGER PRIMARY KEY NOT NUll, name text, pic text, description text, price real)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS category (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NUll, title text, pic text)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS cart (user_id INTEGER REFERENCES user(id), product_id INTEGER REFERENCES product(id), quantity INTEGER, PRIMARY KEY(user_id, product_id))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS food_variation (category_id INTEGER REFERENCES category(id), product_id INTEGER REFERENCES product(id), PRIMARY KEY(category_id, product_id))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS transaction_history (user_id INTEGER REFERENCES user(id), product_id INTEGER REFERENCES product(id), quantity INTEGER, transaction_time text, PRIMARY KEY(user_id, product_id, transaction_time))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS favorite (user_id INTEGER REFERENCES user(id), product_id INTEGER REFERENCES product(id), PRIMARY KEY(user_id, product_id))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS merchant (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name text, address text, pic text)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS merchant_product (merchant_id INTEGER REFERENCES merchant(id), product_id INTEGER REFERENCES product(id), PRIMARY KEY(merchant_id, product_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS product");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS category");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS food_variation");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS favorite");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS transaction_history");
        onCreate(sqLiteDatabase);
    }
}
