package com.example.mobanko.activities.accountInfoFragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.mobanko.utils.NumberUtils.getFirstTwoDecimalsFromDouble;
import static com.example.mobanko.utils.NumberUtils.getWholeValueFromDoubleAsString;
import static com.example.mobanko.utils.TimeUtils.getDynamicDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.mobanko.R;
import com.example.mobanko.activities.CategoryActivity;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.Categories;
import com.example.mobanko.entities.PaymentType;
import com.example.mobanko.entities.Subcategories;
import com.example.mobanko.entities.Transaction;
import com.example.mobanko.entities.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class TransactionInfoActivity extends AppCompatActivity {

    static User userInfo;
    static Account accountInfo;
    static TextView tagTextView;
    static Transaction transactionInfo;

    public static void changeTag(Subcategories subcategories, Categories categories) {

        tagTextView.setText(Subcategories.getSubcategoryString(subcategories));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference accountRef = db.collection("Accounts").document(accountInfo.getIBAN());
        DocumentReference userRef = db.collection("Users").document(userInfo.getId());


        db.runTransaction(new com.google.firebase.firestore.Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull com.google.firebase.firestore.Transaction transaction) throws FirebaseFirestoreException {

                accountInfo.getTransactions().forEach(s -> {
                    if (s.getID().equals(transactionInfo.getID())) {
                        s.setCategories(categories);
                        s.setSubcategories(subcategories);
                    }
                });

                userInfo.getAccounts().replaceAll(item -> {
                    if (item.getIBAN().equals(accountInfo.getIBAN())) {
                        return accountInfo;
                    }
                    return item;
                });

                transaction.set(accountRef, accountInfo);
                transaction.set(userRef, userInfo);

                return null;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_info);
        var intent = getIntent();

        userInfo = (User) intent.getSerializableExtra("userInfo");
        accountInfo = (Account) intent.getSerializableExtra("accountInfo");
        String transactionId = intent.getStringExtra("transactionId");
        var transactionOptional = accountInfo.getTransactions().stream().filter(t -> Objects.equals(t.getID(), transactionId)).findFirst();
        if (!transactionOptional.isPresent()) {
            finish();
        }

        transactionInfo = transactionOptional.get();

        var otherUserNameTextView = (TextView) findViewById(R.id.textView53);
        var iconImage = (TextView) findViewById(R.id.imageView20);
        var balanceWholePart = (TextView) findViewById(R.id.textView54);
        var balanceDecimalPart = (TextView) findViewById(R.id.textView55);
        var balanceCurrency = (TextView) findViewById(R.id.textView56);
        var returnZone = (ConstraintLayout) findViewById(R.id.transactionInfoHeader);
        var replyButton = (TextView) findViewById(R.id.textView57);
        var shareButton = (TextView) findViewById(R.id.textView58);
        tagTextView = (TextView) findViewById(R.id.textView59);
        var transactionInfoTag = (ConstraintLayout) findViewById(R.id.transactionInfoTag);
        var otherUserStatus = (TextView) findViewById(R.id.textView62);
        var otherUserName = (TextView) findViewById(R.id.textView63);
        var otherUserIBAN = (TextView) findViewById(R.id.textView64);
        var processingDate = (TextView) findViewById(R.id.textView66);
        var paymentType = (TextView) findViewById(R.id.textView68);
        var referenceId = (TextView) findViewById(R.id.textView70);

        var changeTagIntent = new Intent(this, CategoryActivity.class);

        StringBuilder balanceBuilder = new StringBuilder();

        if (Objects.equals(accountInfo.getIBAN(), transactionInfo.getSenderID())) {
            //I am sender

            otherUserNameTextView.setText(transactionInfo.getRecipientName());
            iconImage.setBackground(ResourcesCompat.getDrawable(this.getResources(), R.mipmap.send_transaction_icon, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceDecimalPart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceCurrency.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            replyButton.setVisibility(GONE);
            otherUserStatus.setText("Recipient");
            otherUserName.setText(transactionInfo.getRecipientName());
            otherUserIBAN.setText(transactionInfo.getRecipientID());
            changeTagIntent.putExtra("myStatus", "SENDER");

            balanceBuilder.append("-");

        } else {
            //I am receiver

            otherUserNameTextView.setText(transactionInfo.getSenderName());
            iconImage.setBackground(ResourcesCompat.getDrawable(this.getResources(), R.mipmap.receive_transaction_icon, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceDecimalPart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceCurrency.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            replyButton.setVisibility(VISIBLE);
            otherUserStatus.setText("Sender");
            otherUserName.setText(transactionInfo.getSenderName());
            otherUserIBAN.setText(transactionInfo.getSenderID());

            changeTagIntent.putExtra("myStatus", "RECEIVER");
        }

        balanceBuilder.append(getWholeValueFromDoubleAsString(transactionInfo.getBalance()));

        balanceWholePart.setText(balanceBuilder.toString());
        balanceDecimalPart.setText(getFirstTwoDecimalsFromDouble(transactionInfo.getBalance()));
        balanceCurrency.setText(transactionInfo.getCurrencyType().toString());

        returnZone.setOnClickListener(view -> finish());

        tagTextView.setText(Subcategories.getSubcategoryString(transactionInfo.getSubcategories()));

        processingDate.setText(getDynamicDate(transactionInfo.getDateOfTransaction()));

        paymentType.setText(PaymentType.getPaymentString(transactionInfo.getPaymentType()));

        referenceId.setText(transactionInfo.getID());

        transactionInfoTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(changeTagIntent);
            }
        });

    }
}



















