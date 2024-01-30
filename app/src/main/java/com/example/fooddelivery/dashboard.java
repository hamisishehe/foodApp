package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fooddelivery.databinding.ActivityDashboardBinding;
import com.example.fooddelivery.databinding.ActivityMainBinding;

public class dashboard extends AppCompatActivity   {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new homefragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

           switch (item.getItemId()){

               case R.id.home:
                   replaceFragment (new homefragment());
                   break;
               case R.id.cart:
                   replaceFragment (new cartragment());
                   break;
               case R.id.profile:
                   replaceFragment (new profilefragment());
                   break;
           }

            return true;
        });


    }

    private  void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.commit();



    }
}