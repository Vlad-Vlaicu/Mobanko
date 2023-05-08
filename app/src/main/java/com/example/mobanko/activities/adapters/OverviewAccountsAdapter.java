package com.example.mobanko.activities.adapters;

import static com.example.mobanko.entities.AccountType.getAccountTypeString;
import static com.example.mobanko.entities.CurrencyType.getCurrencyString;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.MainActivity;
import com.example.mobanko.activities.TransferActivity;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;

import java.util.List;

public class OverviewAccountsAdapter extends RecyclerView.Adapter<OverviewAccountsAdapter.MyViewHolder> {

    Context context;
    private List<Account> accountList;

    private static MainActivity mainActivity;

    private User userInfo;

    public OverviewAccountsAdapter(Context context, MainActivity mainActivity) {
        this.userInfo = mainActivity.getUserInfo();
        this.context = context;
        this.accountList = userInfo.getAccounts();
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OverviewAccountsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.account_layout, parent, false);
        return new OverviewAccountsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewAccountsAdapter.MyViewHolder holder, int position) {
        var currentAccount = accountList.get(position);
        holder.accountType.setText(getAccountTypeString(currentAccount.getAccountType()));
        String integerBalance = (int) floor(currentAccount.getBalance()) + ",";
        holder.integerPartBalance.setText(integerBalance);

        double decimalPart = currentAccount.getBalance() - floor(currentAccount.getBalance());
        int roundedDecimalPart = (int) ((int) round(decimalPart * 100.0) / 100.0);
        String decimalBalance;

        if (roundedDecimalPart < 10) {
            decimalBalance = "0" + roundedDecimalPart;
        } else {
            decimalBalance = String.valueOf(roundedDecimalPart);
        }

        holder.decimalPartBalance.setText(decimalBalance);

        holder.currencyType.setText(getCurrencyString(currentAccount.getCurrencyType()));

    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView accountType;
        TextView integerPartBalance;
        TextView decimalPartBalance;
        TextView currencyType;
        TextView newTransfer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            accountType = (TextView) itemView.findViewById(R.id.textView22);
            integerPartBalance = (TextView) itemView.findViewById(R.id.textView23);
            decimalPartBalance = (TextView) itemView.findViewById(R.id.textView24);
            currencyType = (TextView) itemView.findViewById(R.id.textView25);
            newTransfer = (TextView) itemView.findViewById(R.id.textView26);

            newTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.startNewTransfer(getAdapterPosition());
                }
            });

        }
    }
}
