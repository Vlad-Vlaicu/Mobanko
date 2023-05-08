package com.example.mobanko.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobanko.R;
import com.example.mobanko.activities.homeFragments.ContactFragment;
import com.example.mobanko.activities.homeFragments.OverviewFragment;
import com.example.mobanko.activities.homeFragments.StoreFragment;
import com.example.mobanko.databinding.ActivityMainBinding;
import com.example.mobanko.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    User userInfo;
    private ActivityMainBinding binding;

    int fragmentState;

    private ImageView profileButton;
    private ImageView overviewButton;
    private ImageView storeButton;
    private ImageView contactButton;

    private TextView actionBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        userInfo = (User) intent.getSerializableExtra("userInfo");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var overviewFragment = new OverviewFragment();

        fragmentState = 0;

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.fragmentContainerView.getId(),
                        overviewFragment, null)
                .commit();


        profileButton = (android.widget.ImageView) binding.getRoot().findViewById(R.id.textView20);
        overviewButton = (android.widget.ImageView) binding.getRoot().findViewById(R.id.imageView7);
        storeButton = (android.widget.ImageView) binding.getRoot().findViewById(R.id.imageView8);
        contactButton = (android.widget.ImageView) binding.getRoot().findViewById(R.id.imageView9);
        actionBarText = (TextView) binding.getRoot().findViewById(R.id.textView19);

        overviewButton.setOnClickListener(view -> setNewFragment(0));
        storeButton.setOnClickListener(view -> setNewFragment(1));
        contactButton.setOnClickListener(view -> setNewFragment(2));
    }

    public User getUserInfo() {
        return userInfo;
    }

    private void setNewFragment(int fragmentId) {

        if (fragmentId == fragmentState) {
            return;
        }

        profileButton.setVisibility(GONE);

        switch (fragmentId) {
            case 0:
                fragmentState = 0;
                actionBarText.setText("Overview");
                profileButton.setVisibility(VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(binding.fragmentContainerView.getId(),
                                OverviewFragment.class, null)
                        .commit();
                break;

            case 1:
                fragmentState = 1;
                actionBarText.setText("Store");
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(binding.fragmentContainerView.getId(),
                                StoreFragment.class, null)
                        .commit();

                break;

            case 2:
                fragmentState = 2;
                actionBarText.setText("Contact");
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(binding.fragmentContainerView.getId(),
                                ContactFragment.class, null)
                        .commit();
                break;

            default:
                return;
        }

        overviewButton.setImageResource(R.mipmap.overview_unfocus);
        storeButton.setImageResource(R.mipmap.store_unfocus);
        contactButton.setImageResource(R.mipmap.contact_unfocus);


        switch (fragmentState) {
            case 0 -> overviewButton.setImageResource(R.mipmap.overview_focus);
            case 1 -> storeButton.setImageResource(R.mipmap.store_focus);
            case 2 -> contactButton.setImageResource(R.mipmap.contact_focus);
            default -> {
            }
        }
    }

}