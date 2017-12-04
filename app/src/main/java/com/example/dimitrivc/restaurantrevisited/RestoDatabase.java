package com.example.dimitrivc.restaurantrevisited;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        sqLiteDatabase.execSQL("CREATE TABLE orders (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, price DOUBLE, amount INT);");

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

        Double itemAmount = 0.0;
        Integer itemID = 0;
        Double itemPrice = 0.0;

        Integer price_in_db = 0;

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Integer intNameColumn = cursor.getColumnIndex("name");
                String nameColumn = cursor.getString(intNameColumn);



                if (nameColumn.equals(dishName)){
                    order_existence = true;

                    itemID = cursor.getInt(0);
                    Log.d("itemID", String.valueOf(itemID));

                    Integer intAmountColumn = cursor.getColumnIndex("amount");
                    itemAmount = cursor.getDouble(intAmountColumn);
                    Log.d("itemAmount test", String.valueOf(itemAmount));

                    Integer intPriceColumn = cursor.getColumnIndex("price");
                    itemPrice = cursor.getDouble(intPriceColumn);
                    Log.d("intPriceColumn", String.valueOf(itemPrice));
                }

                cursor.moveToNext();
           }
        }

        ContentValues contentValues = new ContentValues();

        Log.d("name4_test2", String.valueOf(itemAmount));

        // if the order exists: update
        if (order_existence == true) {
            Log.d("if_order_existence_true", "gafh");

            Log.d("itemAmount", String.valueOf(itemAmount));
            itemAmount +=1;
            Log.d("new_amount", String.valueOf(itemAmount));
            contentValues.put("amount", itemAmount);

            Double new_price = itemPrice + dishPrice;
            Log.d("insert itemPrice", String.valueOf(itemPrice));
            Log.d("insert dishPrice", String.valueOf(dishPrice));
            // het optellen gaat goed.
            Log.d("insert new_price", String.valueOf(new_price));

            contentValues.put("price", new_price);

            // denk dat het hier misgaat: het gaat niet in de db.
            int test = db.update("orders", contentValues, "_id =" + itemID, null);
            Log.d("itemID update", Integer.toString(itemID));
            Log.d("return value insert", Integer.toString(test));

        }
        // if the order doesn't exist: insert
        else if (order_existence == false) {
            Log.d("if_order_doesnt exist", "dgahh");
            contentValues.put("name", dishName);
            contentValues.put("price", dishPrice);
            contentValues.put("amount", 1);

            db.insert("orders", null, contentValues);

            // OM TE ERROR CHECKEN:
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer intNameColumn = cursor.getColumnIndex("name");
                    String nameColumn = cursor.getString(intNameColumn);
                    Log.d("nameColumn", nameColumn);

                    cursor.moveToNext();
                }
            } ///
        }

    // EINDE INSERT
    }

    public void clearOrders() {
        // deletes all orders from database

        SQLiteDatabase db = getWritableDatabase();

        db.delete("orders", null, null);
    }


// EINDE ACTIVITY
}
