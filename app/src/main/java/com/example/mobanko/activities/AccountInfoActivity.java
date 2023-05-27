package com.example.mobanko.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.activities.accountInfoFragments.FunctionsFragment;
import com.example.mobanko.activities.accountInfoFragments.InfoFragment;
import com.example.mobanko.activities.accountInfoFragments.TransactionsFragment;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoActivity extends AppCompatActivity {

    User userInfo;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        ViewPager2 viewPager;
        FragmentManager fragmentManager;
        List<Fragment> fragments;

        var intent = getIntent();
        userInfo = (User) intent.getSerializableExtra("userInfo");
        accountId = intent.getIntExtra("accountId", -1);
        var account = getAccount();

        TextView accountName = (TextView) findViewById(R.id.textView38);
        TextView balanceWhole = (TextView) findViewById(R.id.textView39);
        TextView balanceDecimal = (TextView) findViewById(R.id.textView40);
        TextView balanceCurrency = (TextView) findViewById(R.id.textView41);
        var fragmentHolder = (ConstraintLayout) findViewById(R.id.fragmentHolder);

        TextView transactionNavbar = (TextView) findViewById(R.id.textView42);
        TextView functionsNavbar = (TextView) findViewById(R.id.textView43);
        TextView infoNavbar = (TextView) findViewById(R.id.textView44);

        ImageView searchImage = (ImageView) findViewById(R.id.imageView16);
        ImageView statisticsImage = (ImageView) findViewById(R.id.imageView17);
        TextView newTransferButton = (TextView) findViewById(R.id.textView21);

        ImageView backArrow = (ImageView) findViewById(R.id.imageView10);
        TextView backText = (TextView) findViewById(R.id.textView27);


        accountName.setText(account.getName());
        backText.setText(account.getName());

        int wholeBalanceNumber = (int) account.getBalance();
        double decimalPart = account.getBalance() - wholeBalanceNumber;
        decimalPart = Math.round(decimalPart * 100.0) / 100.0;
        balanceWhole.setText(String.valueOf(wholeBalanceNumber) + ",");

        if (decimalPart == 0) {
            balanceDecimal.setText("00");
        } else {
            balanceDecimal.setText(String.valueOf(decimalPart));
        }

        balanceCurrency.setText(account.getCurrencyType().toString());

        backText.setOnClickListener(view -> finish());
        backArrow.setOnClickListener(view -> finish());

        viewPager = findViewById(R.id.viewPager);
        fragmentManager = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(new TransactionsFragment());
        fragments.add(new FunctionsFragment());
        fragments.add(new InfoFragment());

        // Set up the ViewPager2 adapter
        FragmentStateAdapter adapter = new FragmentStateAdapter(fragmentManager, getLifecycle()) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Update the TextView based on the selected fragment
                if (position == 0) {

                    transactionNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_navbar, null));
                    functionsNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));
                    infoNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));


                } else if (position == 1) {

                    functionsNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_navbar, null));
                    transactionNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));
                    infoNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));

                } else if (position == 2) {

                    infoNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_navbar, null));
                    functionsNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));
                    transactionNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.dirty_white2, null));
                }
            }
        });

        transactionNavbar.setOnClickListener(view -> viewPager.setCurrentItem(0, true));
        functionsNavbar.setOnClickListener(view -> viewPager.setCurrentItem(1, true));
        infoNavbar.setOnClickListener(view -> viewPager.setCurrentItem(2, true));

    }

    public Account getAccount() {
        return userInfo.getAccounts().get(accountId);
    }
}