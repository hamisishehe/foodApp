package com.example.fooddelivery;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    TextView name,price;
    ImageView backbutton, d_imagedisplay;
    Button ordernow;
    private TextView tvQuantity;
    private int quantity = 1; // Initial quantity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.d_namedisplay);
        price = findViewById(R.id.d_pricedisplay);
        ordernow = findViewById(R.id.ordernow);
        backbutton = findViewById(R.id.backbutton);
        d_imagedisplay = findViewById(R.id.d_imagedisplay);
        tvQuantity = findViewById(R.id.tvQuantity);

        Intent intent = getIntent();
        String getname = intent.getStringExtra("name");
        String getimage = intent.getStringExtra("image");
        int getprice = intent.getIntExtra("price", 0);
        int getid = intent.getIntExtra("food_id", 0);



        name.setText(getname);
        price.setText(getprice+ "  "+ "TSH");

        Picasso.get().load("https://testhamisi.000webhostapp.com/images/"+getimage).into(d_imagedisplay);


        SharedPreferences sh = getSharedPreferences("auth", Context.MODE_PRIVATE);
        String userid = sh.getString("USER_ID", "");

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int totalprice = (getprice * quantity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN, response -> {

                    try {
                        JSONObject jsonResponse= new JSONObject(response);

                        if (jsonResponse.has("success")) {
                            Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        } else if (jsonResponse.has("error")) {
                            // Registration failed
                            String errorMessage = jsonResponse.getString("error");
                            Log.e(TAG, "Order failed: " + errorMessage);
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
//                        progressBar.setVisibility(View.GONE);
                    }

                }, error -> {
                    Toast.makeText(DetailActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

//                    progressBar.setVisibility(View.GONE);
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("total_price", String.valueOf(totalprice));
                        params.put("quantity", String.valueOf(quantity));
                        params.put("food_id", String.valueOf(getid));
                        params.put("user_id", userid);
                        return params;
                    }


                };
                VolleySingleton.getInstance(DetailActivity.this).addToRequestQueue(stringRequest);






            }
        });

    }

    public void decreaseQuantity(View view) {
        if (quantity > 1) {
            quantity--;
            displayQuantity();
        }
    }

    public void increaseQuantity(View view) {
        // You can set a maximum limit if needed
        quantity++;
        displayQuantity();
    }

    private void displayQuantity() {
        tvQuantity.setText(String.valueOf(quantity));
    }
}