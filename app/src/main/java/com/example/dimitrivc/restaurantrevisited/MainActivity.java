package com.example.dimitrivc.restaurantrevisited;

import android.app.DownloadManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VAN TO DO
        final RestoDatabase db = RestoDatabase.getInstance(getApplicationContext());

        FragmentManager fm = getSupportFragmentManager();
        CategoriesFragment fragment = new CategoriesFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "categories");
        ft.commit();
    }

    // step 7: load action bar resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // step 7: handle events for action bar button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_order_overview:
        //Log.d("test3", onOptionsItemSelected'')
                Log.d("tag", "onOptionsItemSelected: ");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                OrderFragment fragment = new OrderFragment();
                fragment.show(ft, "dialog");

        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////// nu in MenuFragment
    // view = view voor menu: fragment_menu. daar van de listview.
    // maar hoe weet je welke het is van de listview.

//    public void addItem(View view){
//        // adds one dish to order: saving name, price, etc.
//        // if dish is already in order: increment amount
//
//        RestoDatabase db = RestoDatabase.getInstance(getApplicationContext());
//
//        String order = "order die het is";
//
//        // insert staat in de database.
//        db.insert(order, 1); // price = Integer
//
//        // in to do heb je ook:
//        // updateData();
//        // dat is een method die 'hieronder' staat, zie to do main
//
//    }
    ////////////// in menufragment

    public void clear(){
        // removes all orders from database
        RestoDatabase db = RestoDatabase.getInstance(getApplicationContext());
        db.clearOrders();

        // om het venster te refreshen nadat
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        OrderFragment fragment = new OrderFragment();
//        fragment.show(ft, "dialog");
        // of: (maar nee)
        //viewpager.setOffscreenPageLimit(1);
    }


// EINDE ACTIVITY
}
