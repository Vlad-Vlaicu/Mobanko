package com.example.mobanko.extras;

import static com.example.mobanko.entities.CurrencyType.getCurrencyString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.entities.CurrencyType;
import com.example.mobanko.entities.Transaction;
import com.example.mobanko.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>{

    private final Context context;
    private final ArrayList<Transaction> transactions;

    public TransactionsAdapter(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionsAdapter.TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_layout, parent, false);

        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.setTransaction(transaction);

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    static class TransactionHolder extends RecyclerView.ViewHolder {

        private final View itemView;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
        }

        public void setTransaction(Transaction transaction) {
            TextView suma_txt = itemView.findViewById(R.id.suma_txt);
            TextView currency_transaction_txt = itemView.findViewById(R.id.currency_transaction_txt);
            TextView data_transaction_txt = itemView.findViewById(R.id.data_transaction_txt);
            TextView beneficiar_txt = itemView.findViewById(R.id.beneficiar_txt);
            EditText payment_note_txt = itemView.findViewById(R.id.pay_note_txt);

            suma_txt.append(transaction.getBalance().toString());
            currency_transaction_txt.append(getCurrencyString(transaction.getCurrencyType()));

            var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            var transactionDate = LocalDateTime.parse(transaction.getDateOfTransaction(), formatter);


            data_transaction_txt.append(transactionDate.getDayOfMonth() + "/" +
                    transactionDate.getMonth() + "/" +
                    transactionDate.getYear());
            beneficiar_txt.append(User.getUserById(transaction.getSenderID().toString()).getName());
            payment_note_txt.append(transaction.getPaymentNote());
        }
    }
}
