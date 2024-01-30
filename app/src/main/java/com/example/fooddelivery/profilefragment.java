package com.example.fooddelivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class profilefragment extends Fragment {

    View view;
    TextView emailview, fullnameview;
    TextView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        emailview = view.findViewById(R.id.emailview);
        fullnameview = view.findViewById(R.id.fullnameview);
        logout = view.findViewById(R.id.logout);

        SharedPreferences sh = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String fullname = sh.getString("FULLNAME", "");
        String email = sh.getString("EMAIL", "");

        fullnameview.setText(fullname);
        emailview.setText(email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                 Intent intent = new Intent(getActivity(), Authcheck.class);
                 startActivity(intent);
                 getActivity().finish();

            }
        });
        return  view;
    }
}