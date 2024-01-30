package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Authcheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authcheck);

        SharedPreferences sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE);

// Check if a String value is null
        String storedValue = sharedPreferences.getString("EMAIL", null);
        if (storedValue != null) {
           Intent intent = new Intent(Authcheck.this, dashboard.class);
           startActivity(intent);
           finish();
        } else {
            Intent intent = new Intent(Authcheck.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}