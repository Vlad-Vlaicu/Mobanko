package com.example.mobanko.activities.onboardingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



        return view;
    }
}