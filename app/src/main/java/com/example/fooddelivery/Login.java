package com.example.fooddelivery;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    ProgressBar progressBar;
     TextView registerbtn;
     Button loginbt;

    Button loginactivity;

    EditText emailinput, passwordinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerbtn = findViewById(R.id.registerbtn);
        emailinput = findViewById(R.id.emailinput);
        passwordinput = findViewById(R.id.passwordinput);

        progressBar = findViewById(R.id.progressBar);

        loginbt = findViewById(R.id.login);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, dashboard.class));
        }

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String email = emailinput.getText().toString();
                String password = passwordinput.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Email required", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(password)) {

                    Toast.makeText(Login.this, "Password required", Toast.LENGTH_SHORT).show();

                } else {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN, response -> {

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
                                Intent intent = new Intent(Login.this, Authcheck.class);
                                startActivity(intent);
                                finish();

                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
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

                    }, error -> {
                        Toast.makeText(Login.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                    }){


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }


                    };
                    VolleySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);










                }
            }



        });













        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });



    }
}