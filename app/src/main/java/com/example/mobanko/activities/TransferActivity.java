package com.example.mobanko.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivityTransferBinding;
import com.example.mobanko.entities.User;

public class TransferActivity extends AppCompatActivity {

    private ActivityTransferBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        User userInfo = (User) intent.getSerializableExtra("userInfo");
        int accountId = intent.getIntExtra("accountId", -1);

        if (accountId == -1) {
            finish();
        }

        ConstraintLayout manualTransferZone = binding.getRoot().findViewById(R.id.manualTransferZone);
        ConstraintLayout scanPayZone = binding.getRoot().findViewById(R.id.scanPayZone);
        ConstraintLayout templatesZone = binding.getRoot().findViewById(R.id.templatesZone);
        ConstraintLayout billPaymentsZone = binding.getRoot().findViewById(R.id.billPaymentsZone);
        ImageView returnImage = binding.getRoot().findViewById(R.id.imageView10);
        TextView returnText = binding.getRoot().findViewById(R.id.textView27);

        returnImage.setOnClickListener(view -> finish());
        returnText.setOnClickListener(view -> finish());


        manualTransferZone.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Intent newIntent = new Intent(this, ManualTransferActivity.class);
                    newIntent.putExtra("userInfo", userInfo);
                    newIntent.putExtra("accountId", accountId);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                            this, R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                    ActivityCompat.startActivity(this, newIntent, options.toBundle());
                    break;
            }
            return false;
        });

        scanPayZone.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    //TODO: scanPayZoneActivity
                    Toast.makeText(this, "scan", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });

        templatesZone.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    //TODO: templatesZoneActivity
                    Toast.makeText(this, "template", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });

        billPaymentsZone.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    //TODO: billPaymentsZone
                    Toast.makeText(this, "bill", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });


    }

    private void animateBackgroundColorChange(View view, int targetColor) {
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), ((ColorDrawable) view.getBackground()).getColor(), targetColor);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (int) animator.getAnimatedValue();
                view.setBackgroundColor(color);
            }
        });
        colorAnimator.setDuration(300);
        colorAnimator.start();
    }
}