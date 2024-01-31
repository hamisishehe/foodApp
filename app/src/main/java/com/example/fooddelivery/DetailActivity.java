package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView name,price;
    ImageView backbutton, d_imagedisplay;
    Button ordernow;
    EditText quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.d_namedisplay);
        price = findViewById(R.id.d_pricedisplay);
        ordernow = findViewById(R.id.ordernow);
        quantity = findViewById(R.id.quantity);
        backbutton = findViewById(R.id.backbutton);
        d_imagedisplay = findViewById(R.id.d_imagedisplay);

        Intent intent = getIntent();
        String getname = intent.getStringExtra("name");
        String getimage = intent.getStringExtra("image");
        int getprice = intent.getIntExtra("price", 0);
        int getid = intent.getIntExtra("food_id", 0);

        name.setText(getname);
        price.setText(getprice+ "  "+ "TSH");

        Picasso.get().load("https://testhamisi.000webhostapp.com/images/"+getimage).into(d_imagedisplay);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}