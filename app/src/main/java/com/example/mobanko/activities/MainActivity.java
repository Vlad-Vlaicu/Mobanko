package com.example.mobanko.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobanko.R;
import com.example.mobanko.entities.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}