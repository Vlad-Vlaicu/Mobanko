package com.example.mobanko.activities;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import com.example.mobanko.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Executor executor;
    private BiometricPrompt biometricPrompt;

    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());

        var user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new androidx.biometric.BiometricPrompt(LoginActivity.this, executor,
                    new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            Toast.makeText(getApplicationContext(), "Auth error: " + errString, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            var intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(getApplicationContext(), "Auth failed: ", Toast.LENGTH_SHORT).show();
                        }
                    });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric credentials")
                    .setSubtitle("Log in using your biometric credentials")
                    .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)
                    .build();

            biometricPrompt.authenticate(promptInfo);

        }

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        binding.regiserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toOnboardingActivity();
            }
        });
    }

    private void toOnboardingActivity() {
        var intent = new Intent(this, OnboardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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