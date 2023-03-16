package com.example.mobanko.activities.onboardingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.entities.User;

public class OnboardingEmail extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_onboarding_email, container, false);

        var bundle = this.getArguments();

        if(bundle == null){
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

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}