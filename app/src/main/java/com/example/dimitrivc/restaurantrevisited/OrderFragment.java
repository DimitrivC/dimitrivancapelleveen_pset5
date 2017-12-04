package com.example.dimitrivc.restaurantrevisited;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener{
    // step 10: implements...:

    private RestoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // step 9
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // to get all items from the database

        RestoDatabase db = RestoDatabase.getInstance(getActivity().getApplicationContext());
        Cursor cursor = db.selectAll();

        adapter = new RestoAdapter(getActivity().getApplicationContext(), R.layout.fragment_order, cursor, 0);
        final ListView listCategories = getView().findViewById(R.id.listOrders);

        listCategories.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.fragment_order, container, false);

        Button b = inflated.findViewById(R.id.button);
        b.setOnClickListener(this);

        Button c = inflated.findViewById(R.id.button2);
        c.setOnClickListener(this);

        // Inflate the layout for this fragment
        Log.d("yfugihj", "onCreateView: ");
        return inflated;
    }


    // step 10:
    @Override
    public void onClick(View view) {

        RestoDatabase db = RestoDatabase.getInstance(getActivity().getApplicationContext());

        switch (view.getId()) {
            // if order is placed

            case R.id.button:

                final ListView listCategories = getView().findViewById(R.id.listOrders);

                Intent intent = new Intent(getActivity().getApplicationContext(), Main2Activity.class);

                if (listCategories.getCount() == 0) {
                    intent.putExtra("EXTRA", "No orders yet!");
                }
                else {
                    intent.putExtra("EXTRA", "Your order has been placed! :)");
                }

                String testIntent = intent.getStringExtra("EXTRA");
                Log.d("testIntent", testIntent);

                startActivity(intent);
                break;
            // if order is canceled
            case R.id.button2:
                db.clearOrders();
                Cursor cursor2 = db.selectAll();
                adapter.swapCursor(cursor2);
                break;
        }
    }


}
