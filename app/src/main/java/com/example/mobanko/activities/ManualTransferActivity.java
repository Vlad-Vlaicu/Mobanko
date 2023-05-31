package com.example.mobanko.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityManualTransferBinding;
import com.example.mobanko.databinding.ActivityTransferBinding;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManualTransferActivity extends AppCompatActivity {

    private ActivityManualTransferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManualTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        User userInfo = (User) intent.getSerializableExtra("userInfo");
        int accountId = intent.getIntExtra("accountId", -1);

        Intent newIntent = new Intent(this, AmountTransferActivity.class);
        newIntent.putExtra("userInfo", userInfo);
        newIntent.putExtra("accountId", accountId);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                this, R.anim.slide_in_from_right, R.anim.slide_out_to_right);


        TextView actionBarTextview = binding.getRoot().findViewById(R.id.manual_action_bar_text);
        ImageView returnImage = binding.getRoot().findViewById(R.id.imageView15);

        actionBarTextview.setOnClickListener(view -> finish());
        returnImage.setOnClickListener(view -> finish());

        EditText ibanEditText = binding.getRoot().findViewById(R.id.editTextTextPersonName3);

        Drawable ibanIconUnfocused = getResources().getDrawable(R.mipmap.iban_unfocused);
        Drawable ibanIconFocused = getResources().getDrawable(R.mipmap.iban_focused);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        int size = getResources().getDimensionPixelSize(R.dimen.drawable_size);
        ibanIconUnfocused.setBounds(0, 0, size, size);
        ibanIconFocused.setBounds(0, 0, size, size);

        ibanEditText.setCompoundDrawablesRelative(ibanIconUnfocused, null, null, null);

        ibanEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                // EditText is focused
                ibanEditText.setCompoundDrawablesRelative(ibanIconFocused, null, null, null);

            } else {
                // EditText has lost focus
                ibanEditText.setCompoundDrawablesRelative(ibanIconUnfocused, null, null, null);
            }
        });

        ibanEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 14) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ibanEditText.getWindowToken(), 0);

                    String iban = editable.toString().toUpperCase();

                    var firebaseFirestore = FirebaseFirestore.getInstance();

                    var docRef = firebaseFirestore.collection("Accounts").document(iban);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Account accountInfo = document.toObject(Account.class);

                                    var docRefUser = firebaseFirestore.collection("Users").document(accountInfo.getAccountHolderID());

                                    docRefUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                            if (task.isSuccessful()) {
                                                DocumentSnapshot receiverDocument = task.getResult();
                                                if (receiverDocument.exists()) {
                                                    User receiverInfo = receiverDocument.toObject(User.class);
                                                    newIntent.putExtra("receiverInfo", receiverInfo);
                                                    newIntent.putExtra("receiverAccount", accountInfo);

                                                    ActivityCompat.startActivity(binding.getRoot().getContext(), newIntent, options.toBundle());
                                                }
                                            } else {
                                                Toast.makeText(binding.getRoot().getContext(), "There was a problem getting data. Please check your internet connection!", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(binding.getRoot().getContext(), "There was a problem getting data. Please check your internet connection!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }

            }
        });


    }
}