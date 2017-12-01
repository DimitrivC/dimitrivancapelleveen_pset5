package com.example.dimitrivc.restaurantrevisited;


import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends ListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // step 2: de inhoud van onCreateView moest eigenlijk in onCreate, maar dat lijkt niet erg te zijn
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflaterCategoriesFragment = inflater.inflate(R.layout.fragment_categories, container, false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //final ListView listCategories = inflaterCategoriesFragment.findViewById(R.id.list);
        final TextView textViewFragment = inflaterCategoriesFragment.findViewById(R.id.textViewFragment);
        String url = "https://resto.mprog.nl/categories";
        final List<String> listdata = new ArrayList<>();

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        listdata);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textViewFragment.setText(response.toString());
                        try {

                            // convert JSONObject to JSONArray
                            JSONArray jsonArray = response.getJSONArray("categories");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    listdata.add(jsonArray.getString(i));
                                }
                            }

                            setListAdapter(adapter);
                            //listCategories.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewFragment.setText("@string/error");
                    }
                });
        requestQueue.add(jsonObjectRequest);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        MenuFragment menuFragment = new MenuFragment();
        Log.d("test", String.valueOf(menuFragment));

        String position_category = String.valueOf(position);

        Bundle args = new Bundle();
        args.putString("category", position_category);
        menuFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment, "item")
                .addToBackStack(null)
                .commit();

        //Intent intent = new Intent(this, MenuFragment);

        // van main:
//        FragmentManager fm = getSupportFragmentManager();
//        CategoriesFragment fragment = new CategoriesFragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment_container, fragment, "categories");
//        ft.commit();


    }
}
