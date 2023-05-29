package com.example.mobanko.activities;

import static com.example.mobanko.utils.TimeUtils.getCurrentMonthYearMinusMonths;
import static com.example.mobanko.utils.TimeUtils.getCurrentMonthYearMinusMonthsString;
import static com.example.mobanko.utils.TimeUtils.getMonthAbbreviation;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobanko.R;
import com.example.mobanko.activities.cashflowFragments.CategoriesCashFlowFragment;
import com.example.mobanko.activities.cashflowFragments.MerchantsCashFlowFragment;
import com.example.mobanko.databinding.ActivityCashFlowBinding;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CashFlowActivity extends AppCompatActivity {

    Account accountInfo;

    List<Fragment> fragments;

    ActivityCashFlowBinding binding;

    Context context;

    ConstraintLayout month1Layout;
    ConstraintLayout month2Layout;
    ConstraintLayout month3Layout;
    ConstraintLayout month4Layout;
    ConstraintLayout month5Layout;
    ConstraintLayout month6Layout;

    static List<Transaction> selectedMonthTransactions;
    TextView incomeBarMonth1;
    TextView expensesBarMonth1;
    TextView incomeBarMonth2;
    TextView expensesBarMonth2;
    TextView incomeBarMonth3;
    TextView expensesBarMonth3;
    TextView incomeBarMonth4;
    TextView expensesBarMonth4;
    TextView incomeBarMonth5;
    TextView expensesBarMonth5;
    TextView incomeBarMonth6;
    TextView expensesBarMonth6;

    public static int dpToPixels(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    void setMonthText(TextView monthTextView, int index) {

        var correctedIndex = getMonthIndex(index);

        String monthAbbrv = getMonthAbbreviation(correctedIndex);

        monthTextView.setText(monthAbbrv);
    }

    int getMonthIndex(int index) {
        return (LocalDateTime.now().getMonthValue() - index - 1 + 12) % 12;
    }

    void setMonthLayout(int layoutIndex) {

        month1Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));
        month2Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));
        month3Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));
        month4Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));
        month5Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));
        month6Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.transfer_background, null));

        switch (layoutIndex) {
            case 1 ->
                    month1Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
            case 2 ->
                    month2Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
            case 3 ->
                    month3Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
            case 4 ->
                    month4Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
            case 5 ->
                    month5Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
            case 6 ->
                    month6Layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.selected_bar, null));
        }
    }

    public static void changeBarPercentage(TextView bar, int percentage) {
        var params = bar.getLayoutParams();
        params.height = dpToPixels(bar.getContext(), percentage);
        bar.setLayoutParams(params);
    }

    public static List<Transaction> getSelectedMonthTransactions() {
        return selectedMonthTransactions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCashFlowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;

        var intent = getIntent();
        accountInfo = (Account) intent.getSerializableExtra("accountInfo");

        fragments = new ArrayList<>();
        fragments.add(new CategoriesCashFlowFragment());
        fragments.add(new MerchantsCashFlowFragment());
        var fragmentManager = getSupportFragmentManager();

        TextView dateTextView = (TextView) findViewById(R.id.textView72);
        TextView monthOverall = (TextView) findViewById(R.id.textView79);
        TextView monthIncome = (TextView) findViewById(R.id.textView80);
        TextView monthExpenses = (TextView) findViewById(R.id.textView81);
        TextView categoriesNavbar = (TextView) findViewById(R.id.textView82);
        TextView merchantsNavbar = (TextView) findViewById(R.id.textView83);
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.cashflowViewpager);

        FragmentStateAdapter fragmentStateAdapter = new FragmentStateAdapter(fragmentManager, getLifecycle()) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(fragmentStateAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Update the TextView based on the selected fragment
                if (position == 0) {

                    categoriesNavbar.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    categoriesNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.underline_white, null));
                    merchantsNavbar.setTextColor(ResourcesCompat.getColor(getResources(), R.color.expenses_bar_color, null));
                    merchantsNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.empty_drawable, null));

                } else if (position == 1) {

                    merchantsNavbar.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    merchantsNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.underline_white, null));
                    categoriesNavbar.setTextColor(ResourcesCompat.getColor(getResources(), R.color.expenses_bar_color, null));
                    categoriesNavbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.empty_drawable, null));
                }
            }
        });

        categoriesNavbar.setOnClickListener(view -> viewPager.setCurrentItem(0, true));
        merchantsNavbar.setOnClickListener(view -> viewPager.setCurrentItem(1, true));


        ImageView returnButton = (ImageView) findViewById(R.id.imageView15);

        returnButton.setOnClickListener(view -> finish());


        month1Layout = (ConstraintLayout) findViewById(R.id.month1);
        TextView descMonth1 = (TextView) findViewById(R.id.textView84);
        incomeBarMonth1 = (TextView) findViewById(R.id.textView85);
        expensesBarMonth1 = (TextView) findViewById(R.id.textView86);

        month2Layout = (ConstraintLayout) findViewById(R.id.month2);
        TextView descMonth2 = (TextView) findViewById(R.id.textView87);
        incomeBarMonth2 = (TextView) findViewById(R.id.textView88);
        expensesBarMonth2 = (TextView) findViewById(R.id.textView89);

        month3Layout = (ConstraintLayout) findViewById(R.id.month3);
        TextView descMonth3 = (TextView) findViewById(R.id.textView90);
        incomeBarMonth3 = (TextView) findViewById(R.id.textView91);
        expensesBarMonth3 = (TextView) findViewById(R.id.textView92);

        month4Layout = (ConstraintLayout) findViewById(R.id.month4);
        TextView descMonth4 = (TextView) findViewById(R.id.textView93);
        incomeBarMonth4 = (TextView) findViewById(R.id.textView94);
        expensesBarMonth4 = (TextView) findViewById(R.id.textView95);

        month5Layout = (ConstraintLayout) findViewById(R.id.month5);
        TextView descMonth5 = (TextView) findViewById(R.id.textView96);
        incomeBarMonth5 = (TextView) findViewById(R.id.textView97);
        expensesBarMonth5 = (TextView) findViewById(R.id.textView98);

        month6Layout = (ConstraintLayout) findViewById(R.id.month6);
        TextView descMonth6 = (TextView) findViewById(R.id.textView99);
        incomeBarMonth6 = (TextView) findViewById(R.id.textView100);
        expensesBarMonth6 = (TextView) findViewById(R.id.textView101);

        setBarValues();

        setMonthText(descMonth6, 0);
        setMonthText(descMonth5, 1);
        setMonthText(descMonth4, 2);
        setMonthText(descMonth3, 3);
        setMonthText(descMonth2, 4);
        setMonthText(descMonth1, 5);

        month1Layout.setOnClickListener(view -> {

            setMonthLayout(1);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(5));
            var date = getCurrentMonthYearMinusMonths(5);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });
        month2Layout.setOnClickListener(view -> {

            setMonthLayout(2);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(4));
            var date = getCurrentMonthYearMinusMonths(4);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });

        month3Layout.setOnClickListener(view -> {

            setMonthLayout(3);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(3));
            var date = getCurrentMonthYearMinusMonths(3);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });

        month4Layout.setOnClickListener(view -> {

            setMonthLayout(4);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(2));
            var date = getCurrentMonthYearMinusMonths(2);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });

        month5Layout.setOnClickListener(view -> {

            setMonthLayout(5);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(1));
            var date = getCurrentMonthYearMinusMonths(1);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });

        month6Layout.setOnClickListener(view -> {

            setMonthLayout(6);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(0));
            var date = getCurrentMonthYearMinusMonths(0);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

        });

        month6Layout.performClick();

    }

    List<Transaction> getTransactionsPerDate(LocalDateTime time) {

        return accountInfo.getTransactions().stream()
                .filter(s -> LocalDateTime.parse(s.getDateOfTransaction()).getYear() == time.getYear())
                .filter(s -> LocalDateTime.parse(s.getDateOfTransaction()).getMonthValue() == time.getMonthValue())
                .sorted(Comparator.comparing(Transaction::getDateOfTransaction).reversed())
                .collect(Collectors.toList());
    }

    void setBarValues() {
        var dateMonth1 = getCurrentMonthYearMinusMonths(5);
        var transactionsMonth1 = getTransactionsPerDate(dateMonth1);
        var dateMonth2 = getCurrentMonthYearMinusMonths(4);
        var transactionsMonth2 = getTransactionsPerDate(dateMonth2);
        var dateMonth3 = getCurrentMonthYearMinusMonths(3);
        var transactionsMonth3 = getTransactionsPerDate(dateMonth3);
        var dateMonth4 = getCurrentMonthYearMinusMonths(2);
        var transactionsMonth4 = getTransactionsPerDate(dateMonth4);
        var dateMonth5 = getCurrentMonthYearMinusMonths(1);
        var transactionsMonth5 = getTransactionsPerDate(dateMonth5);
        var dateMonth6 = getCurrentMonthYearMinusMonths(0);
        var transactionsMonth6 = getTransactionsPerDate(dateMonth6);


    }

    double getTotalIncome(List<Transaction> transactions) {
        return 0;
    }

    double getTotalExpenses(List<Transaction> transactions) {
        return 0;
    }


}