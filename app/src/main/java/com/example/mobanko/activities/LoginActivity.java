package com.example.mobanko.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityLoginBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.MultiFactorAssertion;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorGenerator;
import com.google.firebase.auth.PhoneMultiFactorInfo;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Executor executor;
    private BiometricPrompt biometricPrompt;

    private BiometricPrompt.PromptInfo promptInfo;
    private View view;

    private String mVerificationId;

    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private EditText num5;
    private EditText num6;

    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        view = binding.getRoot();

        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());

        var user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !user.isEmailVerified()) {
            user.delete();
        }

        if(user != null){

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
        var email = new StringBuilder(binding.editTextTextPersonName.getText().toString().trim());
        var pass = new StringBuilder(binding.passwordTextTextPersonName.getText().toString().trim());

        //email and pass must not be empty
        if(email.toString().isEmpty() || pass.toString().isEmpty()){

            binding.loginError.setText("Numele de utilizator si parola nu pot fi goale!");
            binding.loginError.setVisibility(View.VISIBLE);
            return;
        }

        //validate email
        String regex = "^(.+)@(.+)$";
        var emailPattern = Pattern.compile(regex);

        var matcher = emailPattern.matcher(email);
        if(!matcher.matches()){
            binding.loginError.setText("Adresa de email nu este valida!");
            binding.loginError.setVisibility(View.VISIBLE);
            return;
        }

        var intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        var callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                // Auto-retrieval or instant verification is successful.
                // Proceed with signing up.
                System.out.println("Verification Completed");

                MultiFactorAssertion multiFactorAssertion
                        = PhoneMultiFactorGenerator.getAssertion(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Verification failed.
                Toast.makeText(view.getContext(), "Verification failed: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Code sent successfully. Save verification ID and token.
                mVerificationId = verificationId;
                mResendToken = token;

                /*
                messageSent = true;
                sendButton.setVisibility(GONE);
                sendButtonDisabled.setVisibility(VISIBLE);

                 */

            }
        };


        var firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.toString(), pass.toString())
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User is not enrolled with a second factor and is successfully
                                    // signed in.
                                    // ...
                                    return;
                                }
                                if (task.getException() instanceof FirebaseAuthMultiFactorException) {
                                    FirebaseAuthMultiFactorException e =
                                            (FirebaseAuthMultiFactorException) task.getException();

                                    MultiFactorResolver multiFactorResolver = e.getResolver();

                                    // Ask user which second factor to use.
                                    MultiFactorInfo selectedHint =
                                            multiFactorResolver.getHints().get(0);

                                    // Send the SMS verification code.
                                    PhoneAuthProvider.verifyPhoneNumber(
                                            PhoneAuthOptions.newBuilder()
                                                    .setActivity(LoginActivity.this)
                                                    .setMultiFactorHint((PhoneMultiFactorInfo) selectedHint)
                                                    .setMultiFactorSession(multiFactorResolver.getSession())
                                                    .setCallbacks(callback)
                                                    .setTimeout(30L, TimeUnit.SECONDS)
                                                    .requireSmsValidation(true)
                                                    .build());

                                    ViewGroup parentLayout = findViewById(R.id.loginLayoutRoot);

                                    ConstraintLayout newConstraintLayout = (ConstraintLayout) LayoutInflater
                                            .from(LoginActivity.this)
                                            .inflate(R.layout.otp_layout, null);

                                    ConstraintLayout existingConstraintLayout = findViewById(R.id.loginContainer);

                                    parentLayout.removeView(existingConstraintLayout);
                                    parentLayout.addView(newConstraintLayout);

                                    //var layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    //newConstraintLayout.setLayoutParams(layoutParams);

                                    var continueButton = (Button) parentLayout.findViewById(R.id.verificareOTPButton);
                                    num1 = (EditText) parentLayout.findViewById(R.id.editTextNumber1);
                                    num2 = (EditText) parentLayout.findViewById(R.id.editTextNumber2);
                                    num3 = (EditText) parentLayout.findViewById(R.id.editTextNumber3);
                                    num4 = (EditText) parentLayout.findViewById(R.id.editTextNumber4);
                                    num5 = (EditText) parentLayout.findViewById(R.id.editTextNumber5);
                                    num6 = (EditText) parentLayout.findViewById(R.id.editTextNumber6);

                                    num1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if(!charSequence.toString().trim().isEmpty()){
                                                num2.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });
                                    num2.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if(!charSequence.toString().trim().isEmpty()){
                                                num3.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });
                                    num3.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if(!charSequence.toString().trim().isEmpty()){
                                                num4.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });
                                    num4.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if(!charSequence.toString().trim().isEmpty()){
                                                num5.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });
                                    num5.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if(!charSequence.toString().trim().isEmpty()){
                                                num6.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });



                                    continueButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            var api = GoogleApiAvailability.getInstance();
                                            int result = api.isGooglePlayServicesAvailable(LoginActivity.this);
                                            if (result == ConnectionResult.SUCCESS) {

                                                // SafetyNet API is available
                                                FirebaseApp.initializeApp(LoginActivity.this);
                                                FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
                                                firebaseAppCheck.installAppCheckProviderFactory(
                                                        SafetyNetAppCheckProviderFactory.getInstance());
                                            } else {
                                                // SafetyNet API is not available, handle the error
                                                // ...
                                            }

                                            var stringBuilder = new StringBuilder();
                                            stringBuilder.append(num1.getText().toString());
                                            stringBuilder.append(num2.getText().toString());
                                            stringBuilder.append(num3.getText().toString());
                                            stringBuilder.append(num4.getText().toString());
                                            stringBuilder.append(num5.getText().toString());
                                            stringBuilder.append(num6.getText().toString());

                                            // Ask user for the SMS verification code.
                                            var credential =
                                                    PhoneAuthProvider.getCredential(mVerificationId, stringBuilder.toString());

                                            // Initialize a MultiFactorAssertion object with the
                                            // PhoneAuthCredential.
                                            MultiFactorAssertion multiFactorAssertion =
                                                    PhoneMultiFactorGenerator.getAssertion(credential);

                                            // Complete sign-in.
                                            multiFactorResolver
                                                    .resolveSignIn(multiFactorAssertion)
                                                    .addOnCompleteListener(
                                                            new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        // User successfully signed in with the
                                                                        // second factor phone number.
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                    // ...
                                                                }
                                                            });
                                        }
                                    });

                                } else {
                                    // Handle other errors such as wrong password.

                                }
                            }
                        });


    }
}