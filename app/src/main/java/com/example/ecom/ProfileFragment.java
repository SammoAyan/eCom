package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {

    View view;

    ImageView profile_image;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button logout_btn,edit_profile_btn;

    TextView name,profile_address,profile_mobile,profile_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        name = view.findViewById(R.id.profile_name);
        profile_email = view.findViewById(R.id.profile_email);
        profile_mobile = view.findViewById(R.id.profile_mobile);
        profile_address = view.findViewById(R.id.profile_address);

        logout_btn = view.findViewById(R.id.logout_btn);
        edit_profile_btn = view.findViewById(R.id.edit_profile_btn);

        mAuth =FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        String uid = user.getUid();


        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                name.setText(""+value.getString("name"));
                profile_mobile.setText("Mobile : "+value.getString("mobile"));
                profile_email.setText("Email: "+value.getString("email"));
                profile_address.setText("Address : "+value.getString("address"));


            }
        });


        logout_btn.setOnClickListener(v->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),MainActivity.class));
            getActivity().finish();

        });

        edit_profile_btn.setOnClickListener(v->{

            startActivity(new Intent(getContext(),EditProfile.class));
            getActivity().finish();

        });


        return view;
    }
}