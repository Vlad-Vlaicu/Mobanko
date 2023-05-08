package com.example.mobanko.activities.homeFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobanko.R;
import com.example.mobanko.activities.MainActivity;
import com.example.mobanko.activities.OnboardingActivity;
import com.example.mobanko.activities.adapters.OverviewAccountsAdapter;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OverviewFragment extends Fragment {
    private MainActivity mActivity;

    private User userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MainActivity");
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        userInfo = mActivity.getUserInfo();

        var recycleView = (RecyclerView) view.findViewById(R.id.overviewAccountsRecycleView);

        var adapter = new OverviewAccountsAdapter(view.getContext(), userInfo.getAccounts());
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}