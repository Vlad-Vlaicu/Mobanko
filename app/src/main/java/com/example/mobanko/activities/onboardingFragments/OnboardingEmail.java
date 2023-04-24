package com.example.mobanko.activities.onboardingFragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.example.mobanko.generators.PasswordGenerator.generatePassword;
import static java.lang.Math.abs;
import static java.lang.Math.toRadians;
import static java.lang.Thread.sleep;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
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
import com.example.mobanko.activities.OnboardingActivity;
import com.example.mobanko.entities.User;
import com.example.mobanko.ui.WaitingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;
import java.util.regex.Pattern;

public class OnboardingEmail extends Fragment {

    private Bundle bundle;
    private OnboardingActivity mActivity;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnboardingActivity) {
            mActivity = (OnboardingActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_onboarding_email, container, false);

        bundle = this.getArguments();

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
                        .addOnCompleteListener(recreateTask -> {
                            if (recreateTask.isSuccessful()) {
                                bundle.putString("userEmail", email[0]);

                                FirebaseUser newUser = FirebaseAuth.getInstance().getCurrentUser();

                                sendEmailVerification(newUser, email[0], password);
                            }
                        });

                /*
                var mAuth = FirebaseAuth.getInstance();
                mAuth.fetchSignInMethodsForEmail(email[0])
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                SignInMethodQueryResult result = task.getResult();
                                List<String> signInMethods = result.getSignInMethods();
                                if (signInMethods == null || signInMethods.isEmpty()) {
                                    // Email is not associated with any existing user, create new user
                                    mAuth.createUserWithEmailAndPassword(email[0], password)
                                            .addOnCompleteListener(createionTask -> {
                                                if (createionTask.isSuccessful()) {
                                                    bundle.putString("userEmail", email[0]);

                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                    sendEmailVerification(user, email[0], password);
                                                }
                                            });
                                } else {
                                    // Email is associated with an existing user
                                    boolean isEmailVerified = signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) ||
                                            signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD);
                                    if (isEmailVerified) {
                                        // User has already verified their email, do nothing
                                        // ...
                                    } else {
                                        // User has not verified their email, delete the user
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.delete()
                                                .addOnCompleteListener(deleteTask -> {
                                                    if (deleteTask.isSuccessful()) {
                                                        // User deleted successfully
                                                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email[0], password)
                                                                .addOnCompleteListener(recreateTask -> {
                                                                    if (recreateTask.isSuccessful()) {
                                                                        bundle.putString("userEmail", email[0]);

                                                                        FirebaseUser newUser = FirebaseAuth.getInstance().getCurrentUser();

                                                                        sendEmailVerification(newUser, email[0], password);
                                                                    }
                                                                });
                                                    } else {
                                                        // User deletion failed
                                                        // ...
                                                    }
                                                });
                                    }
                                }
                            } else {
                                // Error fetching sign-in methods
                                // ...
                            }
                        });

                */

            }
        });
        return view;
    }

    void awaitResponse(FirebaseUser user) {

        System.out.println("AWAITING RESPONSE");

        var dialog = new Dialog(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        dialog.setContentView(inflater.inflate(R.layout.waiting_dialog, null));
        dialog.setCancelable(false);


        final Handler handler = new Handler();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + (5 * 60 * 1000); // 5 minutes
        final long interval = 300; // 0.3 seconds

        dialog.show();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            boolean isVerified = user.isEmailVerified();
                            if (isVerified) {
                                // User is verified, dismiss the waiting dialog and do something
                                dialog.dismiss();
                            }
                        }
                    }
                });

                if (user.isEmailVerified()) {
                    dialog.dismiss();
                    return;
                }

                long currentTime = System.currentTimeMillis();
                if (currentTime < endTime && !user.isEmailVerified()) {

                    handler.postDelayed(this, interval);
                } else {
                    // Time has expired, dismiss the waiting dialog and do something
                    dialog.dismiss();
                }

            }
        };

        handler.postDelayed(runnable, interval);

    }

    void userLogin(String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            bundle.putString("userEmail", email);

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

    void sendEmailVerification(FirebaseUser user, String email, String password) {

        System.out.println("SENDING EMAIL VERIFICATION");

        if (user == null) {
            return;
        }

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

                                    user.reload();

                                    boolean isEmailVerified = user.isEmailVerified();
                                    if (!isEmailVerified) {

                                        awaitResponse(user);

                                        userLogin(email, password);

                                        var nextFragment = new OnboardingPhone();
                                        nextFragment.setArguments(bundle);
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.fragmentContainerView2, nextFragment)
                                                .commit();
                                    }
                                }
                            });
                        }
                    }
                });

    }
}