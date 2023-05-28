package com.example.mobanko.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.SubcategoryActivity;
import com.example.mobanko.entities.Categories;
import com.example.mobanko.entities.Subcategories;

import java.util.List;

public class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.SubcategoriesViewHolder> {

    private static SubcategoryActivity subcategoryActivity;

    Context context;

    List<Subcategories> subcategories;

    Categories categories;

    public SubcategoriesAdapter(Context context, SubcategoryActivity subcategory, Categories category) {
        this.context = context;
        subcategoryActivity = subcategory;
        subcategories = Subcategories.getSubcategories(category);
        categories = category;
    }

    @NonNull
    @Override
    public SubcategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.subcategory_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new SubcategoriesAdapter.SubcategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoriesViewHolder holder, int position) {
        holder.text.setText(Subcategories.getSubcategoryString(subcategories.get(position)));

        holder.itemView.setOnClickListener(view -> {
            holder.button.setChecked(true);
            subcategoryActivity.selectSubcategory(subcategories.get(position), categories);

        });


    }

    @Override
    public int getItemCount() {
        return subcategories.size();
    }

    public static class SubcategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        RadioButton button;

        public SubcategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.textView71);
            button = (RadioButton) itemView.findViewById(R.id.radioButton);

        }
    }
}
