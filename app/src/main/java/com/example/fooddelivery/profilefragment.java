package com.example.fooddelivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class profilefragment extends Fragment {

    View view;
    TextView emailview, fullnameview, listname;
    TextView logout;
    ImageView listimage;

    ListView listView;
    String[] listnames= {"Personal Address", "Change Password", "Orders","Logout"};
    int [] listimages ={R.drawable.baseline_location_on_24, R.drawable.baseline_lock_24, R.drawable.baseline_local_shipping_24, R.drawable.baseline_logout_24};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        emailview = view.findViewById(R.id.emailview);
        fullnameview = view.findViewById(R.id.fullnameview);
        listView = view.findViewById(R.id.profileview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    Intent intent = new Intent(getContext(), PersonaAddress.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(getContext(), Changepassword.class);
                    startActivity(intent);
                }
                else if(position == 2){
                    Intent intent = new Intent(getContext(), OrdersView.class);
                    startActivity(intent);

                }
                else if(position == 3){
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(getActivity(), Authcheck.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });



        SharedPreferences sh = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String fullname = sh.getString("FULLNAME", "");
        String email = sh.getString("EMAIL", "");

        fullnameview.setText(fullname);
        emailview.setText(email);


        return  view;
    }

    private class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return listimages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.custom_list, null);
            TextView name = view1.findViewById(R.id.fullnameview);
            ImageView image = view1.findViewById(R.id.iconprofile);
            name.setText(listnames[position]);
            image.setImageResource(listimages[position]);
            return view1;

        }
    }


}