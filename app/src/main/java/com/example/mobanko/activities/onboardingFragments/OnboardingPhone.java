package com.example.mobanko.activities.onboardingFragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import static com.example.mobanko.activities.factory.UserFactory.getUser;
import static com.example.mobanko.entities.AccountType.CURRENT_ACCOUNT;
import static com.example.mobanko.entities.CurrencyType.RON;
import static com.example.mobanko.generators.IBANGenerator.generateIban;
import static com.example.mobanko.generators.IdGenerator.generateID;
import static com.example.mobanko.generators.PasswordGenerator.generatePassword;

import static java.util.Collections.emptyList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.activities.MainActivity;
import com.example.mobanko.activities.OnboardingActivity;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.MultiFactorAssertion;
import com.google.firebase.auth.MultiFactorSession;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorGenerator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OnboardingPhone extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private EditText phoneEditText;
    private EditText insertCode;
    private View view;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private TextView sendButton;
    private TextView sendButtonDisabled;
    private PhoneAuthCredential credential;
    private Bundle bundle;
    private OnboardingActivity mActivity;
    boolean messageSent = false;
    private String phoneNumber;

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

        firebaseFirestore = FirebaseFirestore.getInstance();

        view = inflater.inflate(R.layout.fragment_onboarding_phone, container, false);

        phoneEditText = (EditText) view.findViewById(R.id.editTextPhone);
        insertCode = (EditText) view.findViewById(R.id.editTextNumberDecimal);
        sendButton = (TextView) view.findViewById(R.id.textView11);
        sendButtonDisabled = (TextView) view.findViewById(R.id.textView7);
        var continueButton = (TextView) view.findViewById(R.id.textView10);
        var continueButtonDisabled = (TextView) view.findViewById(R.id.textView9);
        var errorMessage = (TextView) view.findViewById(R.id.textView12);

        bundle = this.getArguments();

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!messageSent && phoneEditText.getText().toString().trim().length() == 10) {
                    sendButton.setVisibility(VISIBLE);
                    sendButtonDisabled.setVisibility(GONE);
                } else {
                    sendButton.setVisibility(GONE);
                    sendButtonDisabled.setVisibility(VISIBLE);
                }
            }
        });

        insertCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (insertCode.getText().toString().trim().length() == 6 && messageSent) {
                    continueButton.setVisibility(VISIBLE);
                    continueButtonDisabled.setVisibility(GONE);
                } else {
                    continueButton.setVisibility(GONE);
                    continueButtonDisabled.setVisibility(VISIBLE);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Phones")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    var list = queryDocumentSnapshots.getDocuments();
                                    String phoneText = phoneEditText.getText().toString();
                                    for (var d : list) {

                                        String phone = d.toString();

                                        if (phone.equals(phoneText)) {

                                            errorMessage.setVisibility(VISIBLE);
                                            return;
                                        }
                                    }
                                    errorMessage.setVisibility(GONE);
                                    sendSmsOtp();
                                    phoneNumber = phoneText;
                                    return;
                                }
                                errorMessage.setVisibility(VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                errorMessage.setVisibility(VISIBLE);
                            }
                        });
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var code = insertCode.getText().toString();
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);

                System.out.println("CODE " + code + " INSERTED");

                MultiFactorAssertion multiFactorAssertion
                        = PhoneMultiFactorGenerator.getAssertion(credential);

                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getMultiFactor()
                        .enroll(multiFactorAssertion, "My personal phone number")
                        .addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        addUserData();
                                    }
                                });
            }
        });

        return view;
    }


    private void sendSmsOtp() {

        String phoneNumber = "+4" + phoneEditText.getText().toString();
        System.out.println("PHONE NUMBER" + phoneNumber);

        var builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Notificare")
                .setMessage("Pentru a confirma că sunteți o persoană reală, vă vom redirecționa către o pagină web. " +
                        "Vă rugăm să urmați instrucțiunile de pe acea pagină pentru a finaliza procesul")
                .setCancelable(true)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.cancel()).show();

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
                messageSent = true;
                sendButton.setVisibility(GONE);
                sendButtonDisabled.setVisibility(VISIBLE);

            }
        };

        var user = FirebaseAuth.getInstance().getCurrentUser();

        user.getMultiFactor().getSession()
                .addOnCompleteListener(
                        new OnCompleteListener<MultiFactorSession>() {
                            @Override
                            public void onComplete(@NonNull Task<MultiFactorSession> task) {
                                if (task.isSuccessful()) {
                                    MultiFactorSession multiFactorSession = task.getResult();
                                    var phoneAuthOptions = buildPhoneOptions(multiFactorSession, callback);

                                    var api = GoogleApiAvailability.getInstance();
                                    int result = api.isGooglePlayServicesAvailable(getActivity());
                                    if (result == ConnectionResult.SUCCESS) {

                                        // SafetyNet API is available
                                        FirebaseApp.initializeApp(getActivity());
                                        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
                                        firebaseAppCheck.installAppCheckProviderFactory(
                                                SafetyNetAppCheckProviderFactory.getInstance());
                                    } else {
                                        // SafetyNet API is not available, handle the error
                                        // ...
                                    }

                                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
                                }
                            }
                        });
    }

    void addUserData() {
        String userName = bundle.getString("userName");
        String email = bundle.getString("userEmail");

        var userId = FirebaseAuth.getInstance().getUid();
        var userData = getUser(userId, userName, email, phoneNumber);
        var defaultAccount = userData.getAccounts().get(0);
        mActivity.setUserInfo(userData);

        firebaseFirestore.collection("Users").document(userId).set(userData);
        firebaseFirestore.collection("Accounts").document(defaultAccount.getIBAN()).set(defaultAccount);

        var phoneDocRef = firebaseFirestore.collection("Phones").document(phoneNumber);
        var emailDocRef = firebaseFirestore.collection("Emails").document(email);

        phoneDocRef.set(new HashMap<String, String>() {{
            put("phone", phoneNumber);
        }});

        emailDocRef.set(new HashMap<String, String>() {{
            put("email", email);
        }});

        sendChangePasswordEmail(email);

        var nextFragment = new OnboardingCongratulation();

        getFragmentManager().beginTransaction()
                .replace(R.id.onboardingContainer, nextFragment)
                .commit();

    }

    PhoneAuthOptions buildPhoneOptions(MultiFactorSession multiFactorSession,
                                       PhoneAuthProvider.OnVerificationStateChangedCallbacks callback) {
        System.out.println("SENDING SMS TO +4" + phoneEditText.getText().toString());

        return PhoneAuthOptions.newBuilder()
                .setPhoneNumber("+4" + phoneEditText.getText().toString())
                .setTimeout(120L, TimeUnit.SECONDS)
                .setMultiFactorSession(multiFactorSession)
                .setCallbacks(callback)
                .requireSmsValidation(true)
                .setActivity(mActivity)
                .build();
    }

    void sendChangePasswordEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

}