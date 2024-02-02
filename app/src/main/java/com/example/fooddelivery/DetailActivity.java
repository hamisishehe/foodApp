package com.example.fooddelivery;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    Button ordernow, b_increment, b_decrement;
    private TextView tvQuantity;
     int quantity = 1; //

    private Dialog dialog;

    ProgressBar orderprogress;

    // Initial quantity
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
        orderprogress = findViewById(R.id.orderprogress);

        b_increment = findViewById(R.id.btnIncrease);
        b_decrement = findViewById(R.id.btnDecrease);

        b_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity ++ ;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        b_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity --;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        Intent intent = getIntent();
        String getname = intent.getStringExtra("name");
        String getimage = intent.getStringExtra("image");
        int getprice = intent.getIntExtra("price", 0);
        int getid = intent.getIntExtra("id", 0);



        name.setText(getname);
        price.setText(getprice+ "  "+ "TSH");

        Picasso.get().load("https://testhamisi.000webhostapp.com/images/"+getimage).into(d_imagedisplay);


        SharedPreferences sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE);

        int userid = sharedPreferences.getInt("USER_ID", 0);

        AlertDialog dialog1 = createdialog();




        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                orderprogress.setVisibility(View.VISIBLE);
                double totalprice =  getprice * quantity;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_INSERTORDER, response -> {

                    try {
                        JSONObject jsonResponse= new JSONObject(response);

                        if (jsonResponse.has("success")) {

                            dialog1.show();
//                            Toast.makeText(DetailActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                            orderprogress.setVisibility(View.GONE);
                        } else if (jsonResponse.has("error")) {
                            // Registration failed
                            String errorMessage = jsonResponse.getString("error");
                            Log.e(TAG, "Order failed: " + errorMessage);
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                         orderprogress.setVisibility(View.GONE);
                    }

                }, error -> {
                    Toast.makeText(DetailActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                    orderprogress.setVisibility(View.GONE);
                }){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("totalprice", String.valueOf(totalprice));
                        params.put("quantity", String.valueOf(quantity));
                        params.put("food_id", String.valueOf(getid));
                        params.put("user_id", String.valueOf(userid));
                        return params;
                    }


                };
                VolleySingleton.getInstance(DetailActivity.this).addToRequestQueue(stringRequest);






            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    AlertDialog createdialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order Successfully Placed");

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Make another Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return  builder.create();
    }






}