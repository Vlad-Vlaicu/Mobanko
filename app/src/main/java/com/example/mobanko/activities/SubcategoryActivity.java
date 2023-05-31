package com.example.mobanko.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.accountInfoFragments.TransactionInfoActivity;
import com.example.mobanko.activities.adapters.SubcategoriesAdapter;
import com.example.mobanko.entities.Categories;
import com.example.mobanko.entities.Subcategories;

public class SubcategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        var returnZone = (ConstraintLayout) findViewById(R.id.returnZone_subcategory);
        var subcategoryRecycleView = (RecyclerView) findViewById(R.id.subcategoryRecycleView);

        var intent = getIntent();
        Categories category = (Categories) intent.getSerializableExtra("category");

        var adapter = new SubcategoriesAdapter(this, this, category);
        subcategoryRecycleView.setAdapter(adapter);
        subcategoryRecycleView.setLayoutManager(new LinearLayoutManager(this));

        returnZone.setOnClickListener(view -> finish());
    }

    public void selectSubcategory(Subcategories subcategory, Categories category) {
        TransactionInfoActivity.changeTag(subcategory, category);
        finish();
    }
}