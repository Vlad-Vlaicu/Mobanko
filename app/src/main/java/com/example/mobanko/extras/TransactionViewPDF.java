package com.example.mobanko.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.entities.CurrencyType;
import com.example.mobanko.entities.Transaction;
import com.example.mobanko.entities.User;

import androidx.appcompat.widget.LinearLayoutCompat;

public class TransactionViewPDF extends LinearLayoutCompat {

    private final Context context;

    public TransactionViewPDF(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.transaction_layout, this);
    }

    public void setTransaction(Transaction transaction) {
        TextView suma_txt = this.findViewById(R.id.suma_txt);
        TextView currency_transaction_txt = this.findViewById(R.id.currency_transaction_txt);
        TextView data_transaction_txt = this.findViewById(R.id.data_transaction_txt);
        TextView beneficiar_txt = this.findViewById(R.id.beneficiar_txt);
        EditText payment_note_txt = this.findViewById(R.id.pay_note_txt);

        suma_txt.append(transaction.getBalance().toString());
        currency_transaction_txt.append(CurrencyType.getCurrencyString(transaction.getCurrencyType()));
        data_transaction_txt.append(transaction.getDateOfTransaction().toString());
        beneficiar_txt.append(User.getUserById(transaction.getSenderID().toString()).getName());
        payment_note_txt.append(transaction.getPaymentNote());
    }
}
