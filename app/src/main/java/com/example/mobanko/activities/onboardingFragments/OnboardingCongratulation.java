package com.example.mobanko.activities.onboardingFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mobanko.R;
import com.example.mobanko.activities.MainActivity;
import com.example.mobanko.activities.OnboardingActivity;

public class OnboardingCongratulation extends Fragment {

    private OnboardingActivity mActivity;

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
        var view = inflater.inflate(R.layout.fragment_congratulation, container, false);

        var exitImage = (ImageView) view.findViewById(R.id.imageView5);

        // Create a new Intent for the target activity
        var intent = new Intent(mActivity, MainActivity.class);

        // Set the appropriate flags to disable the back button
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Handler mHandler = new Handler();
        var runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                mActivity.finish();
            }
        };

        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start the target activity
                mHandler.removeCallbacks(runnable);
                startActivity(intent);
                mActivity.finish();
            }
        });

        mHandler.postDelayed(runnable, 30000);

        return view;
    }
}