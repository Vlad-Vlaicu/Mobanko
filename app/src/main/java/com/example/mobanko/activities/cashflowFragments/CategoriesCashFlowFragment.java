package com.example.mobanko.activities.cashflowFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.CashFlowActivity;
import com.example.mobanko.activities.adapters.CashFlowCategoriesAdapter;

public class CategoriesCashFlowFragment extends Fragment {

    RecyclerView recycleView;
    CashFlowCategoriesAdapter adapter;
    View view;
    Context context;
    private CashFlowActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CashFlowActivity) {
            mActivity = (CashFlowActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AccountInfoActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories_cash_flow, container, false);

        recycleView = (RecyclerView) view.findViewById(R.id.categories_cashflow_fragment);
        adapter = new CashFlowCategoriesAdapter(view.getContext());
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        context = view.getContext();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new CashFlowCategoriesAdapter(view.getContext());
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    public void forcedResume() {

        adapter = new CashFlowCategoriesAdapter(context);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}