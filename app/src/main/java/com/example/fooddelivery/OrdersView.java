package com.example.fooddelivery;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersView extends AppCompatActivity {

    ListView listView1;
    ProgressBar progressBar;
    ImageView backbutton;
    ArrayList<OrdersModel> orderslist = new ArrayList<OrdersModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_view);


        SharedPreferences sh = getSharedPreferences("auth", Context.MODE_PRIVATE);
        int user_id = sh.getInt("USER_ID", 0);


        progressBar = findViewById(R.id.order_progress);
        listView1 = findViewById(R.id.listview1);
        backbutton = findViewById(R.id.o_backbutton);
        OrdersAdapter ordersAdapter = new OrdersAdapter(this, orderslist);
        listView1.setAdapter(ordersAdapter);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ORDERS, response -> {
            progressBar.setVisibility(View.GONE);
            try {
                //converting the string to json array object
                JSONArray array = new JSONArray(response);

                //traversing through all the object
                for (int i = 0; i < array.length(); i++) {

                    //getting product object from json array
                    JSONObject orders = array.getJSONObject(i);

                    //adding the product to product list
                    orderslist.add( new OrdersModel(
                            orders.getInt("order_id"),
                            orders.getInt("totalprice"),
                            orders.getInt("quantity"),
                            orders.getInt("status"),
                            orders.getString("image"),
                            orders.getString("name")
                    ));


                    Log.d("response", "response"+orders.getString("name"));

                }

                ordersAdapter.notifyDataSetChanged();



            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                progressBar.setVisibility(View.GONE);
            }

        }, error -> {
            Toast.makeText(OrdersView.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                return params;
            }


        };
        VolleySingleton.getInstance(OrdersView.this).addToRequestQueue(stringRequest);



    }
}