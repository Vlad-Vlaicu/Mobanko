package com.example.mobanko.activities.accountInfoFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.AccountInfoActivity;
import com.example.mobanko.activities.adapters.AccountInfoTransactionsAdapter;

public class TransactionsFragment extends Fragment {

    private AccountInfoActivity mActivity;

    private AccountInfoTransactionsAdapter adapter;
    private RecyclerView recycleView;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountInfoActivity) {
            mActivity = (AccountInfoActivity) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transactions, container, false);

        recycleView = (RecyclerView) view.findViewById(R.id.transactions_recycle_view);

        adapter = new AccountInfoTransactionsAdapter(view.getContext(), mActivity);

        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new AccountInfoTransactionsAdapter(view.getContext(), mActivity);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    public void forcedResume() {
        adapter = new AccountInfoTransactionsAdapter(view.getContext(), mActivity);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}