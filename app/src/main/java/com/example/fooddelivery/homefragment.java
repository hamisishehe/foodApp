package com.example.fooddelivery;

import static android.content.Context.MODE_APPEND;

import static com.android.volley.VolleyLog.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
public class homefragment extends Fragment {

    TextView viewname;
    View view;
    RecyclerView foodview;
    ArrayList<FoodModel> foodlist = new ArrayList<>();
    Foodadapter foodadapter;
    ProgressDialog progressDialog;

    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homefragment, container, false);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        foodview = view.findViewById(R.id.foodview);
        foodadapter = new Foodadapter(getContext(),foodlist);
        shimmerFrameLayout = view.findViewById(R.id.shimmerdisplay);

        foodview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        foodview.setAdapter(foodadapter);



        SharedPreferences sh = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String fullname = sh.getString("FULLNAME", "");


        shimmerFrameLayout.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.URL_FOOD, response -> {

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            try {
                //converting the string to json array object
                JSONArray array = new JSONArray(response);

                //traversing through all the object
                for (int i = 0; i < array.length(); i++) {

                    //getting product object from json array
                    JSONObject food = array.getJSONObject(i);

                    //adding the product to product list
                    foodlist.add(new FoodModel(
                            food.getInt("food_id"),
                            food.getString("name"),
                            food.getInt("price"),
                            food.getString("image")
                    ));

                    Log.d("response", "response"+foodlist.get(i).getName());
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                foodadapter.notifyDataSetChanged();


            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());

            }


        }, error -> {

            Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();


        } );
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);




        return view;

    }


}