package com.example.mobanko.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.CashFlowActivity;
import com.example.mobanko.entities.Categories;
import com.example.mobanko.entities.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CashFlowCategoriesAdapter extends RecyclerView.Adapter<CashFlowCategoriesAdapter.CategoriesViewHolder> {

    Context context;
    List<Transaction> transactions;

    HashMap<Categories, List<Transaction>> categoriesMap;

    public CashFlowCategoriesAdapter(Context context) {
        this.context = context;
        categoriesMap = new HashMap<>();

        this.transactions = CashFlowActivity.getTransactions();

        transactions.forEach(transaction -> {
            if (categoriesMap.containsKey(transaction.getCategories())) {
                categoriesMap.get(transaction.getCategories()).add(transaction);

            } else {
                List<Transaction> transactionsList = new ArrayList<>();
                transactionsList.add(transaction);
                categoriesMap.put(transaction.getCategories(), transactionsList);
            }

        });
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cashflow_categories_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new CashFlowCategoriesAdapter.CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        List<Categories> categoryList = categoriesMap.keySet().stream().collect(Collectors.toList());
        var category = categoryList.get(position);
        var transactions = categoriesMap.get(category);
        Double total = transactions.stream().mapToDouble(Transaction::getBalance).sum();
        int noTransactions = transactions.size();

        holder.categoryIcon.setBackground(Categories.getIcon(category, context));
        holder.textCategory.setText(Categories.getCategoryString(category));
        holder.numberTransactions.setText(String.valueOf(noTransactions) + " Transactions");

        var totalString = new StringBuilder();
        if (Categories.getExpensesCategories().contains(category)) {
            totalString.append("-");
        }

        totalString.append(total).append(" ").append(transactions.get(0).getCurrencyType());

        holder.balance.setText(totalString);
    }

    @Override
    public int getItemCount() {
        return categoriesMap.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView categoryIcon;
        TextView textCategory;

        TextView numberTransactions;

        TextView balance;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIcon = (TextView) itemView.findViewById(R.id.imageView21);
            textCategory = (TextView) itemView.findViewById(R.id.textView105);
            numberTransactions = (TextView) itemView.findViewById(R.id.textView106);
            balance = (TextView) itemView.findViewById(R.id.textView107);

        }
    }
}
