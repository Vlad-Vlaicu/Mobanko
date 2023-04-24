package com.example.mobanko.activities.onboardingFragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.example.mobanko.generators.PasswordGenerator.generatePassword;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.activities.LoginActivity;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class OnboardingEmail extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_onboarding_email, container, false);

        var bundle = this.getArguments();

        if (bundle == null) {
            return view;
        }

        var name = bundle.getString("userName", "User");

        var titleGreeting = (TextView) view.findViewById(R.id.titleEmailOnboarding);

        titleGreeting.setText(name + ", ce adresa de e-mail ai?");

        var emailEditText = (EditText) view.findViewById(R.id.editTextEmailAddressOnboarding);
        var emailRetypeEditText = (EditText) view.findViewById(R.id.editTextEmailAddressOnboardingRetype);
        var firstWarning = (TextView) view.findViewById(R.id.warningTextViewEmailOnboarding);
        var secondWarning = (TextView) view.findViewById(R.id.warningTextViewEmailRetypeOnboarding);
        var continueButton = (TextView) view.findViewById(R.id.continueEmailOnboardingButton);
        var disabledButton = (TextView) view.findViewById(R.id.continueEmailOnboardingButtonDisabled);

        final String[] email = {""};
        final String[] retypedEmail = {""};
        //validate email
        String regex = "^(.+)@(.+)$";
        var emailPattern = Pattern.compile(regex);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email[0] = String.valueOf(emailEditText.getText());
                if (email[0].isEmpty()) {
                    firstWarning.setVisibility(VISIBLE);
                    firstWarning.setText("Camp oblitoriu");
                    return;
                }

                if (email[0].equals(retypedEmail[0])) {
                    var matcher = emailPattern.matcher(email[0]);

                    if (!matcher.matches()) {
                        firstWarning.setVisibility(VISIBLE);
                        firstWarning.setText("Adresa de e-mail este invalida");
                    } else {
                        disabledButton.setVisibility(GONE);
                        continueButton.setVisibility(VISIBLE);
                    }

                } else {
                    disabledButton.setVisibility(VISIBLE);
                    continueButton.setVisibility(GONE);
                }
            }
        });

        emailRetypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (email[0].isEmpty()) {
                    firstWarning.setVisibility(VISIBLE);
                    firstWarning.setText("Camp oblitoriu");
                    return;
                }

                var matcher = emailPattern.matcher(email[0]);
                if (!matcher.matches()) {
                    firstWarning.setVisibility(VISIBLE);
                    firstWarning.setText("Adresa de e-mail este invalida");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                retypedEmail[0] = String.valueOf(emailRetypeEditText.getText());
                if (retypedEmail[0].isEmpty()) {
                    secondWarning.setVisibility(VISIBLE);
                    secondWarning.setText("Camp oblitoriu");
                    return;
                }

                if (abs(email[0].length() - retypedEmail[0].length()) == 1) {
                    secondWarning.setVisibility(VISIBLE);
                    secondWarning.setText("Adresele de e-mail nu sunt identice");
                    return;
                }

                if (email[0].equals(retypedEmail[0])) {
                    disabledButton.setVisibility(GONE);
                    continueButton.setVisibility(VISIBLE);
                } else {
                    disabledButton.setVisibility(VISIBLE);
                    continueButton.setVisibility(GONE);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = generatePassword(12);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email[0], password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                bundle.putString("userEmail", email[0]);

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user != null) {
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Email sent
                                                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                                                        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                                            @Override
                                                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                user.reload();

                                                                if (user != null) {
                                                                    boolean isEmailVerified = user.isEmailVerified();
                                                                    if (!isEmailVerified) {

                                                                        while (!user.isEmailVerified()) {
                                                                            user.reload();
                                                                            try {
                                                                                sleep(300);
                                                                                System.out.println("Not validated yet");
                                                                            } catch (
                                                                                    InterruptedException e) {
                                                                                throw new RuntimeException(e);
                                                                            }
                                                                        }

                                                                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                                                        mAuth.signInWithEmailAndPassword(email[0], password)
                                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            // Sign in success, update UI with the signed-in user's information
                                                                                            bundle.putString("userEmail", email[0]);

                                                                                            var nextFragment = new OnboardingPhone();
                                                                                            nextFragment.setArguments(bundle);
                                                                                            getFragmentManager().beginTransaction()
                                                                                                    .replace(R.id.fragmentContainerView2, nextFragment)
                                                                                                    .commit();

                                                                                        } else {
                                                                                            // If sign in fails, display a message to the user.
                                                                                            System.out.println("SIGN IN FAILED");
                                                                                        }
                                                                                    }
                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        System.out.println("SOMETHING BAD HAPPEND: " + e.getMessage());
                                                                                    }
                                                                                });


                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }


                            }
                        });


            }
        });

        return view;
    }
}