package com.example.mobanko.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityTransferBinding;
import com.example.mobanko.entities.User;

public class TransferActivity extends AppCompatActivity {

    private ActivityTransferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        User userInfo = (User) intent.getSerializableExtra("userInfo");
        int accountId = intent.getIntExtra("accountId", -1);

        TextView textView = binding.getRoot().findViewById(R.id.textView21);
        textView.setText("Account no " + accountId + "starts a new transfer");
    }
}