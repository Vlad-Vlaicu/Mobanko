package com.example.mobanko.activities;

import static com.example.mobanko.utils.TimeUtils.getCurrentMonthYearMinusMonths;
import static com.example.mobanko.utils.TimeUtils.getCurrentMonthYearMinusMonthsString;
import static com.example.mobanko.utils.TimeUtils.getMonthAbbreviation;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.mobanko.entities.Categories;
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

    static Context context;

    ConstraintLayout month1Layout;
    ConstraintLayout month2Layout;
    ConstraintLayout month3Layout;
    ConstraintLayout month4Layout;
    ConstraintLayout month5Layout;
    ConstraintLayout month6Layout;

    static List<Transaction> selectedMonthTransactions = new ArrayList<>();
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

    TextView monthOverall;
    TextView monthIncome;
    TextView monthExpenses;

    TextView dateTextView;

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
        if (percentage == 0) {
            percentage = 1;
        }
        var params = bar.getLayoutParams();
        params.height = dpToPixels(bar.getContext(), percentage);
        bar.setLayoutParams(params);
    }

    public static List<Transaction> getSelectedMonthTransactions() {
        return selectedMonthTransactions;
    }

    public static List<Transaction> getTransactions() {
        return selectedMonthTransactions;
    }

    public static void selectCategory(Categories category) {
        Toast.makeText(context, Categories.getCategoryString(category), Toast.LENGTH_SHORT).show();
    }

    List<Transaction> getTransactionsPerDate(LocalDateTime time) {

        return accountInfo.getTransactions().stream()
                .filter(s -> LocalDateTime.parse(s.getDateOfTransaction()).getYear() == time.getYear())
                .filter(s -> LocalDateTime.parse(s.getDateOfTransaction()).getMonthValue() == time.getMonthValue())
                .sorted(Comparator.comparing(Transaction::getDateOfTransaction).reversed())
                .collect(Collectors.toList());
    }

    void setBarValues() {

        //bar values are calculate are percent of the biggest value among both income and expenses

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

        List<Double> valueList = new ArrayList<>();
        valueList.add(getTotalIncome(transactionsMonth1));
        valueList.add(getTotalExpenses(transactionsMonth1));
        valueList.add(getTotalIncome(transactionsMonth2));
        valueList.add(getTotalExpenses(transactionsMonth2));
        valueList.add(getTotalIncome(transactionsMonth3));
        valueList.add(getTotalExpenses(transactionsMonth3));
        valueList.add(getTotalIncome(transactionsMonth4));
        valueList.add(getTotalExpenses(transactionsMonth4));
        valueList.add(getTotalIncome(transactionsMonth5));
        valueList.add(getTotalExpenses(transactionsMonth5));
        valueList.add(getTotalIncome(transactionsMonth6));
        valueList.add(getTotalExpenses(transactionsMonth6));

        double biggestValue = valueList.stream().mapToDouble(Double::valueOf).reduce(Math::max).getAsDouble();

        var incomeMonth1Percentage = Double.valueOf((valueList.get(0) / biggestValue) * 100).intValue();
        var expensesMonth1Percentage = Double.valueOf((valueList.get(1) / biggestValue) * 100).intValue();
        var incomeMonth2Percentage = Double.valueOf((valueList.get(2) / biggestValue) * 100).intValue();
        var expensesMonth2Percentage = Double.valueOf((valueList.get(3) / biggestValue) * 100).intValue();
        var incomeMonth3Percentage = Double.valueOf((valueList.get(4) / biggestValue) * 100).intValue();
        var expensesMonth3Percentage = Double.valueOf((valueList.get(5) / biggestValue) * 100).intValue();
        var incomeMonth4Percentage = Double.valueOf((valueList.get(6) / biggestValue) * 100).intValue();
        var expensesMonth4Percentage = Double.valueOf((valueList.get(7) / biggestValue) * 100).intValue();
        var incomeMonth5Percentage = Double.valueOf((valueList.get(8) / biggestValue) * 100).intValue();
        var expensesMonth5Percentage = Double.valueOf((valueList.get(9) / biggestValue) * 100).intValue();
        var incomeMonth6Percentage = Double.valueOf((valueList.get(10) / biggestValue) * 100).intValue();
        var expensesMonth6Percentage = Double.valueOf((valueList.get(11) / biggestValue) * 100).intValue();

        changeBarPercentage(incomeBarMonth1, incomeMonth1Percentage);
        changeBarPercentage(expensesBarMonth1, expensesMonth1Percentage);
        changeBarPercentage(incomeBarMonth2, incomeMonth2Percentage);
        changeBarPercentage(expensesBarMonth2, expensesMonth2Percentage);
        changeBarPercentage(incomeBarMonth3, incomeMonth3Percentage);
        changeBarPercentage(expensesBarMonth3, expensesMonth3Percentage);
        changeBarPercentage(incomeBarMonth4, incomeMonth4Percentage);
        changeBarPercentage(expensesBarMonth4, expensesMonth4Percentage);
        changeBarPercentage(incomeBarMonth5, incomeMonth5Percentage);
        changeBarPercentage(expensesBarMonth5, expensesMonth5Percentage);
        changeBarPercentage(incomeBarMonth6, incomeMonth6Percentage);
        changeBarPercentage(expensesBarMonth6, expensesMonth6Percentage);

    }

    double getTotalIncome(List<Transaction> transactions) {

        if (transactions.isEmpty()) {
            return 0;
        }

        return transactions.stream().filter(t -> Categories.getIncomeCategories().contains(t.getCategories()))
                .mapToDouble(Transaction::getBalance)
                .sum();
    }

    double getTotalExpenses(List<Transaction> transactions) {

        if (transactions.isEmpty()) {
            return 0;
        }

        return transactions.stream().filter(t -> Categories.getExpensesCategories().contains(t.getCategories()))
                .mapToDouble(Transaction::getBalance)
                .sum();
    }

    private void setMonthData(List<Transaction> transactions) {

        var income = getTotalIncome(transactions);
        var expenses = getTotalExpenses(transactions);

        var incomeString = String.valueOf(income);
        if (income == 0) {
            incomeString = "0";
        }

        var expensesString = String.valueOf(expenses);
        if (expenses == 0) {
            expensesString = "0";
        } else {
            expensesString = "-" + expensesString;
        }


        var totalString = String.valueOf(income - expenses);
        if (income - expenses == 0) {
            totalString = "0";
        }

        String overallString = totalString + " " + accountInfo.getCurrencyType();
        String incomeTotalString = incomeString + " " + accountInfo.getCurrencyType();
        String expensesTotalString = expensesString + " " + accountInfo.getCurrencyType();

        monthOverall.setText(overallString);
        monthIncome.setText(incomeTotalString);
        monthExpenses.setText(expensesTotalString);
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

        dateTextView = findViewById(R.id.textView72);
        monthOverall = findViewById(R.id.textView79);
        monthIncome = findViewById(R.id.textView80);
        monthExpenses = findViewById(R.id.textView81);
        TextView categoriesNavbar = findViewById(R.id.textView82);
        TextView merchantsNavbar = findViewById(R.id.textView83);
        ViewPager2 viewPager = findViewById(R.id.cashflowViewpager);

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
                    ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
                }
            }
        });

        categoriesNavbar.setOnClickListener(view -> viewPager.setCurrentItem(0, true));
        merchantsNavbar.setOnClickListener(view -> viewPager.setCurrentItem(1, true));


        ImageView returnButton = findViewById(R.id.imageView15);

        returnButton.setOnClickListener(view -> finish());


        month1Layout = findViewById(R.id.month1);
        TextView descMonth1 = findViewById(R.id.textView84);
        incomeBarMonth1 = findViewById(R.id.textView85);
        expensesBarMonth1 = findViewById(R.id.textView86);

        month2Layout = findViewById(R.id.month2);
        TextView descMonth2 = findViewById(R.id.textView87);
        incomeBarMonth2 = findViewById(R.id.textView88);
        expensesBarMonth2 = findViewById(R.id.textView89);

        month3Layout = findViewById(R.id.month3);
        TextView descMonth3 = findViewById(R.id.textView90);
        incomeBarMonth3 = findViewById(R.id.textView91);
        expensesBarMonth3 = findViewById(R.id.textView92);

        month4Layout = findViewById(R.id.month4);
        TextView descMonth4 = findViewById(R.id.textView93);
        incomeBarMonth4 = findViewById(R.id.textView94);
        expensesBarMonth4 = findViewById(R.id.textView95);

        month5Layout = findViewById(R.id.month5);
        TextView descMonth5 = findViewById(R.id.textView96);
        incomeBarMonth5 = findViewById(R.id.textView97);
        expensesBarMonth5 = findViewById(R.id.textView98);

        month6Layout = findViewById(R.id.month6);
        TextView descMonth6 = findViewById(R.id.textView99);
        incomeBarMonth6 = findViewById(R.id.textView100);
        expensesBarMonth6 = findViewById(R.id.textView101);

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

            setMonthData(transactions);

            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();

        });
        month2Layout.setOnClickListener(view -> {

            setMonthLayout(2);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(4));
            var date = getCurrentMonthYearMinusMonths(4);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

            setMonthData(transactions);

            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
        });

        month3Layout.setOnClickListener(view -> {

            setMonthLayout(3);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(3));
            var date = getCurrentMonthYearMinusMonths(3);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

            setMonthData(transactions);

            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
        });

        month4Layout.setOnClickListener(view -> {

            setMonthLayout(4);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(2));
            var date = getCurrentMonthYearMinusMonths(2);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

            setMonthData(transactions);

            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
        });

        month5Layout.setOnClickListener(view -> {

            setMonthLayout(5);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(1));
            var date = getCurrentMonthYearMinusMonths(1);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;

            setMonthData(transactions);

            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
        });

        month6Layout.setOnClickListener(view -> {

            setMonthLayout(6);
            dateTextView.setText(getCurrentMonthYearMinusMonthsString(0));
            var date = getCurrentMonthYearMinusMonths(0);
            var transactions = getTransactionsPerDate(date);
            selectedMonthTransactions = transactions;
            setMonthData(transactions);
            ((CategoriesCashFlowFragment) fragments.get(0)).forcedResume();
        });

        setMonthLayout(6);
        dateTextView.setText(getCurrentMonthYearMinusMonthsString(0));
        var date = getCurrentMonthYearMinusMonths(0);
        var transactions = getTransactionsPerDate(date);
        selectedMonthTransactions = transactions;
        setMonthData(transactions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMonthLayout(6);
        dateTextView.setText(getCurrentMonthYearMinusMonthsString(0));
        var date = getCurrentMonthYearMinusMonths(0);
        var transactions = getTransactionsPerDate(date);
        selectedMonthTransactions = transactions;
        setMonthData(transactions);
    }
}