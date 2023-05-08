package com.example.mobanko.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobanko.activities.homeFragments.OverviewFragment;
import com.example.mobanko.databinding.ActivityMainBinding;
import com.example.mobanko.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    User userInfo;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        userInfo = (User) intent.getSerializableExtra("userInfo");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var overviewFragment = new OverviewFragment();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.fragmentContainerView.getId(),
                        overviewFragment, null)
                .commit();
    }

    public User getUserInfo() {
        return userInfo;
    }

}