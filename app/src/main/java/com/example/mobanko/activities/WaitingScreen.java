package com.example.mobanko.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.activities.adapters.OverviewAccountsAdapter;
import com.example.mobanko.databinding.ActivityMainBinding;
import com.example.mobanko.databinding.ActivityWaitingScreenBinding;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class WaitingScreen extends AppCompatActivity {

    private ActivityWaitingScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWaitingScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var user = FirebaseAuth.getInstance().getCurrentUser();

        var firebaseFirestore = FirebaseFirestore.getInstance();

        var docRef = firebaseFirestore.collection("Users").document(user.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User userInfo = document.toObject(User.class);
                        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
                        intent.putExtra("userInfo", userInfo);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "There was a problem getting data. Please check your internet connection!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(binding.getRoot().getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}