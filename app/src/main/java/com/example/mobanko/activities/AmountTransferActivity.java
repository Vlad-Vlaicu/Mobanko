package com.example.mobanko.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityAmountTransferBinding;
import com.example.mobanko.databinding.ActivityManualTransferBinding;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;

public class AmountTransferActivity extends AppCompatActivity {

    ActivityAmountTransferBinding binding;
    EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmountTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        User userInfo = (User) intent.getSerializableExtra("userInfo");
        int accountId = intent.getIntExtra("accountId", -1);
        User receiverUser = (User) intent.getSerializableExtra("receiverInfo");
        Account receiverAccount = (Account) intent.getSerializableExtra("receiverAccount");

        TextView returnTextView = (TextView) binding.getRoot().findViewById(R.id.amount_action_bar_text);
        ImageView returnImageView = (ImageView) binding.getRoot().findViewById(R.id.amount_return_image);
        TextView warningTextView = (TextView) binding.getRoot().findViewById(R.id.warning_amount_textview);
        TextView amountAvailableTextView = (TextView) binding.getRoot().findViewById(R.id.available_amount_textview);
        TextView amountNextUnfocused = (TextView) binding.getRoot().findViewById(R.id.amountNextUnfocused);
        TextView amountNextFocused = (TextView) binding.getRoot().findViewById(R.id.amountNextFocused);

        Account currentAccount = userInfo.getAccounts().get(accountId);

        String amount = "Your available amount is " + currentAccount.getBalance() + " " + currentAccount.getCurrencyType();
        amountAvailableTextView.setText(amount);

        returnImageView.setOnClickListener(view -> finish());
        returnTextView.setOnClickListener(view -> finish());

        amountEditText = (EditText) binding.getRoot().findViewById(R.id.amountEditText);

        amountEditText.requestFocus();

        // Show the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(amountEditText, InputMethodManager.SHOW_IMPLICIT);

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                double newValue = 0;
                if (editable.length() != 0) {
                    newValue = Double.parseDouble(editable.toString());
                    amountNextFocused.setVisibility(GONE);
                    amountNextUnfocused.setVisibility(VISIBLE);
                }

                if (newValue > currentAccount.getBalance()) {
                    warningTextView.setVisibility(VISIBLE);
                    amountNextFocused.setVisibility(GONE);
                    amountNextUnfocused.setVisibility(VISIBLE);

                } else {
                    warningTextView.setVisibility(GONE);
                    amountNextFocused.setVisibility(VISIBLE);
                    amountNextUnfocused.setVisibility(GONE);
                }

                String newAmount = "Your new available amount is " + (currentAccount.getBalance() - newValue) + " " + currentAccount.getCurrencyType();
                amountAvailableTextView.setText(newAmount);
            }
        });

        amountNextFocused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        amountEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(amountEditText, InputMethodManager.SHOW_IMPLICIT);
    }
}