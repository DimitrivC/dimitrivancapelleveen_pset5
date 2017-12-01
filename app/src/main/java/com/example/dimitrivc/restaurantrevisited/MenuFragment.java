package com.example.dimitrivc.restaurantrevisited;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {


    //List<String> price_list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // step 4 (soort van step 2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflaterMenu = inflater.inflate(R.layout.fragment_menu, container, false);

        final TextView textView2 = inflaterMenu.findViewById(R.id.textView2);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://resto.mprog.nl/menu";
        final List<String> listdata2 = new ArrayList<>();

        final ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        listdata2);

        final Bundle arguments = this.getArguments();

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView2.setText(response.toString());

                        // ook voor bundle
                        if (arguments.toString() != null){
                            String namePosition = "appetizers";
                            if (arguments.toString().equals("Bundle[{category=1}]")){
                                namePosition = "entrees";
                            }

                            try {

                                // convert JSONObject to JSONArray
                                JSONArray jsonArray2 = response.getJSONArray("items");

                                // VOOR REST: OM OFWEL APPETIZERS OFWEL ENTREES TE DOEN
                                if (jsonArray2 != null) {
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        if (namePosition.equals("entrees")) {
                                            if (jsonArray2.getJSONObject(i).getString("category").equals("entrees")) {
                                                listdata2.add(jsonArray2.getJSONObject(i).getString("name"));

                                                //listdata2.add(jsonArray2.getJSONObject(i).getString("price"));

                                            }
                                        }
                                        if (namePosition.equals("appetizers")) {
                                            if (jsonArray2.getJSONObject(i).getString("category").equals("appetizers")) {
                                                listdata2.add(jsonArray2.getJSONObject(i).getString("name"));

                                                //price_list.add(jsonArray2.getJSONObject(i).getString("price"));
                                                //listdata2.add(jsonArray2.getJSONObject(i).getString("price"));
                                            }
                                        }
                                    }
                                }


                                setListAdapter(adapter2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView2.setText("@string/error");
                    }
                });
        requestQueue.add(jsonObjectRequest2);

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    // add item hier.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        RestoDatabase db = RestoDatabase.getInstance(getActivity().getApplicationContext());

        final Bundle arguments = this.getArguments();
        if (arguments.toString() != null) {
            String namePosition = "appetizers";
            if (arguments.toString().equals("Bundle[{category=1}]")) {
                namePosition = "entrees";
            }

            db.insert(position, namePosition);
        }

    }// EIND ONLISTITEMCLICK

} // EIND FRAGMENT
