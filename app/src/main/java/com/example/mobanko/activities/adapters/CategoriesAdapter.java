package com.example.mobanko.activities.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.entities.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    Context context;
    List<Categories> categoriesList;
    List<Drawable> categoriesIconsList;

    public CategoriesAdapter(Context context, String transactionType) {
        this.context = context;
        switch (transactionType) {
            case "SENDER" -> {
                categoriesList = Categories.getExpensesCategories();
                categoriesIconsList = Categories.getExpensesCategoriesIcons(context);
            }
            case "RECEIVER" -> {
                categoriesList = Categories.getIncomeCategories();
                categoriesIconsList = Categories.getIncomeCategoriesIcons(context);
            }
        }
    }


    @NonNull
    @Override
    public CategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoriesViewHolder holder, int position) {
        holder.categoryIcon.setBackground(categoriesIconsList.get(position));
        holder.textCategory.setText(Categories.getCategoryString(categoriesList.get(position)));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView categoryIcon;
        TextView textCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIcon = (TextView) itemView.findViewById(R.id.imageView23);
            textCategory = (TextView) itemView.findViewById(R.id.textView52);
        }
    }

}
