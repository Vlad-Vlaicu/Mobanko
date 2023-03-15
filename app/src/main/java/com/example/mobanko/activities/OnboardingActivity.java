package com.example.mobanko.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobanko.activities.onboardingFragments.OnboardingPersonalData;
import com.example.mobanko.databinding.ActivityOnboardingBinding;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;


public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.fragmentContainerView2.getId(),
                        OnboardingPersonalData.class, null)
                .commit();

        var acc = new User();
        
    }
}