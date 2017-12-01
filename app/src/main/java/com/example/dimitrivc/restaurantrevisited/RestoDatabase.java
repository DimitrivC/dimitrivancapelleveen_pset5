package com.example.dimitrivc.restaurantrevisited;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DimitrivC on 29-11-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {

    // LIJKT NODIG
    private static RestoDatabase instance;

    // WAS PUBLIC, IN TO DO PRIVATE GEMAAKT, DUS HIER OOK
    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE orders (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price DOUBLE, amount INT);");

    }

    // VERPLICHTE METHOD, DEZE INHOUD LIJKT ME NODIG, DIRECT VAN TO DO
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // hoezo int i en int il?
        String TABLE_NAME = "orders";
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // LIJKT NODIG
    public static RestoDatabase getInstance(Context context){
        if (instance == null){
            instance = new RestoDatabase(context, "table", null, 5 );
        }
        return instance;
    }

    // VOOR insert (voor addItem) om te checken of order al bestaat
    public Cursor selectAll() {
        // use getWritableDatabase() to open connection
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM orders";
        Cursor cursor = db.rawQuery(query, new String[] {});
        // ipv null iets van: new String[] {String.valueOf(stdId)

        return cursor;
    }

    // VOOR addItem() IN MenuFragment
    public void insert(Integer position, String namePosition) {

        String dishName = "Chicken Noodle Soup";
        Double dishPrice = 0.0;

        if (namePosition == "appetizers"){
            if (position == 0){
                dishName = "Chicken Noodle Soup";
                dishPrice = 3.0;
            }
            if (position == 1){
                dishName = "Italian Salad";
                dishPrice = 5.0;
            }
        }
        else if (namePosition == "entrees") {
            if (position == 0){
                dishName = "Spaghetti and Meatballs";
                dishPrice = 9.0;
            }
            if (position == 1){
                dishName = "Margherita Pizza";
                dishPrice = 10.0;
            }
            if (position == 2){
                dishName = "Grilled Steelhead Trout Sandwich";
                dishPrice = 9.0;
            }
            if (position == 3){
                dishName = "Pesto Linguini";
                dishPrice = 9.0;
            }
        }

        // get connection to database
        SQLiteDatabase db = getWritableDatabase();
        // get cursor to check if order has been placed already
        Cursor cursor = selectAll();

        boolean order_existence = false;

        Double name4 = 0.0;
        Integer name5 = 0;

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Integer name = cursor.getColumnIndex("name");
                String name2 = cursor.getString(name);

                if (name2.equals(namePosition)){
                    order_existence = true;

                    name5 = cursor.getColumnIndex("_id");

                    Integer name3 = cursor.getColumnIndex("amount");
                    name4 = cursor.getDouble(name3);
                }

                cursor.moveToNext();
           }
        }

        ContentValues contentValues = new ContentValues();

        // if the order exists: update
        if (order_existence == true) {
            Double new_amount = name4 +=1;
            contentValues.put("amount", new_amount);

            db.update("orders", contentValues, "_id = " + name5, null);

        }
        // if the order doesn't exist: insert
        else if (order_existence == false) {

            contentValues.put("name", dishName);
            contentValues.put("price", dishPrice);
            contentValues.put("amount", 1);

            db.insert("orders", null, contentValues);

        }

    // EINDE INSERT
    }

    public void clearOrders() {
        // deletes all orders from database

        SQLiteDatabase db = getWritableDatabase();

        // dit?
        //db.delete("orders", null, null);
        // maar delete dit niet de hele table?

        // of dit? dit lijkt logischer.
        db.execSQL("delete from orders");
    }


// EINDE ACTIVITY
}
