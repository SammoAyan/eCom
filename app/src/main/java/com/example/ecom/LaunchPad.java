package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;

public class LaunchPad extends AppCompatActivity {


    FrameLayout frameLayout;
    ImageView home,cart,orders,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_pad);

        frameLayout = findViewById(R.id.frameLayout);
        home = findViewById(R.id.home);
        cart = findViewById(R.id.cart);
        orders = findViewById(R.id.orders);
        profile = findViewById(R.id.profile);

        cart.setVisibility(View.GONE);


        changeFrag(new ProductFragment());


        home.setOnClickListener(v->{

            changeFrag(new ProductFragment());

        });
        cart.setOnClickListener(v->{

            changeFrag(new CartFragment());

        });
        orders.setOnClickListener(v->{

            changeFrag(new OrdersFragment());

        });
        profile.setOnClickListener(v->{

            changeFrag(new ProfileFragment());

        });

    }

    void changeFrag(Fragment fragment){


        String frag = fragment.getClass().toString();

        if(frag.contains("ProductFragment")){
            home.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            cart.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            orders.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            profile.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        else if(frag.contains("CartFragment")){
            cart.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            home.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            orders.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            profile.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);

        }else if(frag.contains("OrdersFragment")){
            orders.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            home.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            cart.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            profile.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else if(frag.contains("ProfileFragment")){

            profile.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            home.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            cart.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);
            orders.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_700), android.graphics.PorterDuff.Mode.MULTIPLY);

        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}