package com.example.fooddelivery;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView loginbtn;
    Button registeractivity;
    ProgressBar progressBar;

    EditText emailinput, passwordinput, fullnameinput, phonenumberinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginbtn = findViewById(R.id.loginbtn);
        emailinput =(EditText) findViewById(R.id.emailinput);
        passwordinput =(EditText) findViewById(R.id.passwordinput);
        fullnameinput =(EditText) findViewById(R.id.fullnameinput);
        phonenumberinput =(EditText) findViewById(R.id.phonenumberinput);
        progressBar = findViewById(R.id.progressBar);

        registeractivity = findViewById(R.id.registeractivity);



        registeractivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                progressBar.setVisibility(View.VISIBLE);

                String email = emailinput.getText().toString();
                String fullname = fullnameinput.getText().toString();
                String password = passwordinput.getText().toString();
                String phonenumber = phonenumberinput.getText().toString();


                if (TextUtils.isEmpty(fullname)){
                    Toast.makeText(Register.this, "Fullname required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(TextUtils.isEmpty(phonenumber)){
                    Toast.makeText(Register.this, "Phonenumber required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Email required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(TextUtils.isEmpty(password)){

                    Toast.makeText(Register.this, "Password required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
               else{


                    StringRequest registrationRequest = new StringRequest(Request.Method.POST, Urls.URL_REGISTER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse= new JSONObject(response);

                                        if (jsonResponse.has("success")) {

                                            JSONObject userData = jsonResponse.getJSONObject("data");
                                            int userId = userData.getInt("user_id");
                                            String fullnameget = userData.getString("fullname");
                                            String phone = userData.getString("phonenumber");
                                            String userEmail = userData.getString("email");

                                            SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putInt("USER_ID", userId);
                                            editor.putString("FULLNAME", fullnameget);
                                            editor.putString("EMAIL", userEmail);
                                            editor.putString("PHONENUMBER", phone);
                                            editor.apply();
                                            Intent intent = new Intent(Register.this, Authcheck.class);
                                            startActivity(intent);
                                            finish();

                                            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        } else if (jsonResponse.has("error")) {
                                            // Registration failed
                                            String errorMessage = jsonResponse.getString("error");
                                            Log.e(TAG, "Registration failed: " + errorMessage);
                                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "Volley error: " + error.getMessage());
                                    Toast.makeText(getApplicationContext(), "Volley error", Toast.LENGTH_SHORT).show();
                                }
                            }) {


                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String, String> params = new HashMap<>();
                           params.put("fullname", fullname);
                           params.put("phonenumber", phonenumber);
                           params.put("email", email);
                           params.put("password", password);
                           return params;
                       }


                   };
                   VolleySingleton.getInstance(Register.this).addToRequestQueue(registrationRequest);


               }
            }
        });



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}