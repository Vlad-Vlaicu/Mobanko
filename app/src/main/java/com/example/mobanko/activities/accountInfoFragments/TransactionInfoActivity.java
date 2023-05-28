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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.mobanko.R;
import com.example.mobanko.activities.CategoryActivity;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.PaymentType;
import com.example.mobanko.entities.Subcategories;
import com.example.mobanko.entities.User;

import java.util.Objects;

public class TransactionInfoActivity extends AppCompatActivity {

    User userInfo;
    Account accountInfo;

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

        var transaction = transactionOptional.get();

        var otherUserNameTextView = (TextView) findViewById(R.id.textView53);
        var iconImage = (TextView) findViewById(R.id.imageView20);
        var balanceWholePart = (TextView) findViewById(R.id.textView54);
        var balanceDecimalPart = (TextView) findViewById(R.id.textView55);
        var balanceCurrency = (TextView) findViewById(R.id.textView56);
        var returnZone = (ConstraintLayout) findViewById(R.id.transactionInfoHeader);
        var replyButton = (TextView) findViewById(R.id.textView57);
        var shareButton = (TextView) findViewById(R.id.textView58);
        var tagTextView = (TextView) findViewById(R.id.textView59);
        var transactionInfoTag = (ConstraintLayout) findViewById(R.id.transactionInfoTag);
        var otherUserStatus = (TextView) findViewById(R.id.textView62);
        var otherUserName = (TextView) findViewById(R.id.textView63);
        var otherUserIBAN = (TextView) findViewById(R.id.textView64);
        var processingDate = (TextView) findViewById(R.id.textView66);
        var paymentType = (TextView) findViewById(R.id.textView68);
        var referenceId = (TextView) findViewById(R.id.textView70);

        var changeTagIntent = new Intent(this, CategoryActivity.class);

        StringBuilder balanceBuilder = new StringBuilder();

        if (Objects.equals(accountInfo.getIBAN(), transaction.getSenderID())) {
            //I am sender

            otherUserNameTextView.setText(transaction.getRecipientName());
            iconImage.setBackground(ResourcesCompat.getDrawable(this.getResources(), R.mipmap.send_transaction_icon, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceDecimalPart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            balanceCurrency.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_dark_blue, null));
            replyButton.setVisibility(GONE);
            otherUserStatus.setText("Recipient");
            otherUserName.setText(transaction.getRecipientName());
            otherUserIBAN.setText(transaction.getRecipientID());
            changeTagIntent.putExtra("myStatus", "SENDER");

            balanceBuilder.append("-");

        } else {
            //I am receiver

            otherUserNameTextView.setText(transaction.getSenderName());
            iconImage.setBackground(ResourcesCompat.getDrawable(this.getResources(), R.mipmap.receive_transaction_icon, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceDecimalPart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceWholePart.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            balanceCurrency.setTextColor(ResourcesCompat.getColor(this.getResources(), R.color.text_green, null));
            replyButton.setVisibility(VISIBLE);
            otherUserStatus.setText("Sender");
            otherUserName.setText(transaction.getSenderName());
            otherUserIBAN.setText(transaction.getSenderID());

            changeTagIntent.putExtra("myStatus", "RECEIVER");
        }

        balanceBuilder.append(getWholeValueFromDoubleAsString(transaction.getBalance()));

        balanceWholePart.setText(balanceBuilder.toString());
        balanceDecimalPart.setText(getFirstTwoDecimalsFromDouble(transaction.getBalance()));
        balanceCurrency.setText(transaction.getCurrencyType().toString());

        returnZone.setOnClickListener(view -> finish());

        tagTextView.setText(Subcategories.getSubcategoryString(transaction.getSubcategories()));

        processingDate.setText(getDynamicDate(transaction.getDateOfTransaction()));

        paymentType.setText(PaymentType.getPaymentString(transaction.getPaymentType()));

        referenceId.setText(transaction.getID());

        transactionInfoTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(changeTagIntent);
            }
        });

    }
}



















