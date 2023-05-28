package com.example.mobanko.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

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
        categoryRecycleView.setLayoutManager(new LinearLayoutManager(this));
        setResult(Activity.RESULT_CANCELED);
    }

    public void selectCategory(Categories category) {
        Toast.makeText(this, Categories.getCategoryString(category), Toast.LENGTH_SHORT).show();


    }


}