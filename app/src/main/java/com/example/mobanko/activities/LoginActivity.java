package com.example.mobanko.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.var;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        var username = new StringBuilder(binding.editTextTextPersonName.getText());
        var pass = new StringBuilder(binding.passwordTextTextPersonName.getText());

        //email and pass must not be empty
        if(username.toString().isEmpty() || pass.toString().isEmpty()){

            binding.loginError.setText("Numele de utilizator si parola nu pot fi goale!");
            binding.loginError.setVisibility(View.VISIBLE);
            return;
        }

        //validate email
        String regex = "^(.+)@(.+)$";
        var emailPattern = Pattern.compile(regex);

        var matcher = emailPattern.matcher(username);
        if(!matcher.matches()){
            binding.loginError.setText("Adresa de email nu este valida!");
            binding.loginError.setVisibility(View.VISIBLE);
            return;
        }

        var intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        var firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(username.toString().trim(), pass.toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                binding.loginError.setText("Numele de utilizator sau parola sunt incorecte!");
                binding.loginError.setVisibility(View.VISIBLE);

            }
        });

    }
}