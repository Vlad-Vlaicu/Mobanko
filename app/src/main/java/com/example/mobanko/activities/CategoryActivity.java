package com.example.mobanko.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.adapters.CategoriesAdapter;
import com.example.mobanko.entities.Categories;

public class CategoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        var returnZone = (ConstraintLayout) findViewById(R.id.returnZone_category);
        var categoryRecycleView = (RecyclerView) findViewById(R.id.categoryRecycleView);

        var intent = getIntent();
        String status = intent.getStringExtra("myStatus");

        var adapter = new CategoriesAdapter(this, status, this);
        categoryRecycleView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(layoutManager);

        returnZone.setOnClickListener(view -> finish());
    }

    public void selectCategory(Categories category) {
        var subcategoriesIntent = new Intent(this, SubcategoryActivity.class);
        subcategoriesIntent.putExtra("category", category);
        startActivity(subcategoriesIntent);
        finish();
    }

}