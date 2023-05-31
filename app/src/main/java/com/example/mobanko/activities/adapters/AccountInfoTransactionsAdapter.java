package com.example.mobanko.activities.adapters;

import static com.example.mobanko.utils.NumberUtils.getFirstTwoDecimalsFromDouble;
import static com.example.mobanko.utils.NumberUtils.getWholeValueFromDoubleAsString;
import static com.example.mobanko.utils.TimeUtils.getDynamicDate;
import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobanko.R;
import com.example.mobanko.activities.AccountInfoActivity;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.PaymentType;
import com.example.mobanko.entities.Subcategories;
import com.example.mobanko.entities.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class AccountInfoTransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private static AccountInfoActivity accountInfoActivity;

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_TRANSACTION = 2;

    List<Object> allItems;

    Account account;

    public AccountInfoTransactionsAdapter(Context context, AccountInfoActivity infoActivity) {
        this.context = context;
        accountInfoActivity = infoActivity;
        allItems = new ArrayList<>();
        clear();
        this.account = accountInfoActivity.getAccount();


        var sortedTransactions = account.getTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getDateOfTransaction).reversed())
                .collect(toList());

        var headersList = sortedTransactions.stream()
                .map(s -> LocalDateTime.parse(s.getDateOfTransaction()))
                .map(s -> LocalDateTime.of(s.getYear(), s.getMonthValue(), s.getDayOfMonth(), 0, 0))
                .sorted(Comparator.comparing(LocalDateTime::getYear).thenComparing(LocalDateTime::getMonth).reversed())
                .distinct()
                .collect(toList());

        headersList.forEach(headerObject -> {
            allItems.add(headerObject);
            sortedTransactions.forEach(transaction -> {
                var time = LocalDateTime.parse(transaction.getDateOfTransaction());
                if (time.getYear() == headerObject.getYear() && time.getMonth() == headerObject.getMonth()) {
                    allItems.add(transaction);
                }
            });
        });

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.account_info_transaction_layout_header, null);

            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(layoutParams);
            return new HeaderItemViewHolder(headerView);
        }

        View itemView = LayoutInflater.from(context).inflate(R.layout.account_info_transaction_layout, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);

        if (itemType == TYPE_HEADER) {
            ((HeaderItemViewHolder) holder).bindData((LocalDateTime) allItems.get(position));
        } else if (itemType == TYPE_TRANSACTION) {
            ((ItemViewHolder) holder).bindData((Transaction) allItems.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return allItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (allItems.get(position) instanceof LocalDateTime) {
            return TYPE_HEADER;
        } else if (allItems.get(position) instanceof Transaction) {
            return TYPE_TRANSACTION;
        }
        return 0;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView transactionIcon;
        TextView otherTransactionUser;
        TextView dateOfTransaction;
        TextView transactionType;
        TextView transactionSubcategory;
        TextView balance;

        CardView transactionCardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionIcon = (TextView) itemView.findViewById(R.id.imageView22);
            otherTransactionUser = (TextView) itemView.findViewById(R.id.textView47);
            dateOfTransaction = (TextView) itemView.findViewById(R.id.textView49);
            transactionType = (TextView) itemView.findViewById(R.id.textView50);
            transactionSubcategory = (TextView) itemView.findViewById(R.id.textView51);
            balance = (TextView) itemView.findViewById(R.id.textView48);
            transactionCardView = (CardView) itemView.findViewById(R.id.transactionCardView);


            /*
            itemView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN ->
                           // transactionCardView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.light_gray, null));
                    case MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->{
                        //transactionCardView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
                        v.performClick();
                    }
                }
                return false;
            });

             */


        }


        public void bindData(Transaction transaction) {

            StringBuilder balanceBuilder = new StringBuilder();
            if (Objects.equals(transaction.getSenderID(), account.getIBAN())) {
                transactionIcon.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.send_transaction_icon, null));
                otherTransactionUser.setText(transaction.getRecipientName());
                balanceBuilder.append("-");
            } else {
                transactionIcon.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.receive_transaction_icon, null));
                otherTransactionUser.setText(transaction.getSenderName());
                balance.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.text_green, null));
            }


            var transactionDate = LocalDateTime.parse(transaction.getDateOfTransaction());

            dateOfTransaction.setText(getDynamicDate(transactionDate));

            transactionType.setText(PaymentType.getPaymentString(transaction.getPaymentType()));
            transactionSubcategory.setText(Subcategories.getSubcategoryString(transaction.getSubcategories()));

            String wholePart = getWholeValueFromDoubleAsString(transaction.getBalance());
            String decimalPart = getFirstTwoDecimalsFromDouble(transaction.getBalance());

            balanceBuilder.append(wholePart).append(",").append(decimalPart).append(" ").append(transaction.getCurrencyType().toString());
            balance.setText(balanceBuilder.toString());

            itemView.setOnClickListener(view -> accountInfoActivity.getTransactionInfo(transaction.getID()));
        }
    }

    class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView headerTextView;

        public HeaderItemViewHolder(@NonNull View itemView) {
            super(itemView);

            headerTextView = (TextView) itemView.findViewById(R.id.textView46);
        }

        public void bindData(LocalDateTime time) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(time.getMonth().toString());
            stringBuilder.append(" ");
            stringBuilder.append(time.getYear());

            headerTextView.setText(stringBuilder.toString());
        }
    }

    public void clear() {
        int size = allItems.size();
        allItems.clear();
        notifyItemRangeRemoved(0, size);
    }
}
