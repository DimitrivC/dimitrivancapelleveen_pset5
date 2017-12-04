package com.example.dimitrivc.restaurantrevisited;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by DimitrivC on 29-11-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {

    // constructor: heb ik de verkeerde constructor?
    public RestoAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, R.layout.row_orders, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView one = view.findViewById(R.id.text1);
        TextView two = view.findViewById(R.id.text2);
        TextView three = view.findViewById(R.id.text3);

        Integer name = cursor.getColumnIndex("name");
        String name2 = cursor.getString(name);
        one.setText(name2);

        Integer name3 = cursor.getColumnIndex("price");

        Double name4 = cursor.getDouble(name3);
        //Log.d("string_price", String.valueOf(name4));

        two.setText(" $" + String.valueOf(name4));

        Integer name5 = cursor.getColumnIndex("amount");

        Integer name6 = cursor.getInt(name5);
        //Log.d("string_amon", String.valueOf(name6));

        three.setText(" No." + String.valueOf(name6));

    }
}
