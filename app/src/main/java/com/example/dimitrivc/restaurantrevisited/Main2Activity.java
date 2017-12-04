package com.example.dimitrivc.restaurantrevisited;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by DimitrivC on 1-12-2017.
 */

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2main);

        Intent intent = getIntent();
        String OrderMessage = intent.getStringExtra("EXTRA");
        TextView textView = findViewById(R.id.textView3);
        textView.setText(OrderMessage);

        if (OrderMessage.equals("Your order has been placed! :)")) {
            // drop table
        }

    }
}

