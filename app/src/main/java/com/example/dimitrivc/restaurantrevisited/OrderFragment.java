package com.example.dimitrivc.restaurantrevisited;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
// class must either be declared abstract or
    // step 10: implements...:

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SQLiteDatabase db = this.getWritableDatabase();

    }

    // step 9
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // to get all items from the database

        RestoDatabase db = RestoDatabase.getInstance(getActivity().getApplicationContext());
        Cursor cursor = db.selectAll();

        RestoAdapter adapter = new RestoAdapter(getActivity().getApplicationContext(), R.layout.fragment_order, cursor, 0);
        final ListView listCategories = getView().findViewById(R.id.listOrders);

        listCategories.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.fragment_order, container, false);

        // step 10:
        Button b = inflated.findViewById(R.id.button);
        b.setOnClickListener(this);

        Button c = (Button) inflated.findViewById(R.id.button2);
        c.setOnClickListener(this);
            // einde step 10


        // Inflate the layout for this fragment
        Log.d("yfugihj", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    // step 10:
    @Override
    public void onClick(View view) {

        Log.d("onclick", "hoi");

        switch (view.getId()) {
            // if order is placed
            case R.id.button:
                TextView textBox = view.findViewById(R.id.textView);
                textBox.setText("Your order has been placed (but not for real...)");

                break;
            // if order is canceled
            case R.id.button2:
                RestoDatabase db = RestoDatabase.getInstance(getActivity().getApplicationContext());
                db.clearOrders();
                break;
        }
    }


}
