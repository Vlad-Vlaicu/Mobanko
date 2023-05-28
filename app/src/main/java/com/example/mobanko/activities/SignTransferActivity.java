package com.example.mobanko.activities;

import static com.example.mobanko.entities.Categories.UNCATEGORIZED_EXPENSES;
import static com.example.mobanko.entities.Categories.UNCATEGORIZED_INCOME;
import static com.example.mobanko.entities.CurrencyType.RON;
import static com.example.mobanko.entities.PaymentType.INTRABANK;
import static com.example.mobanko.entities.Subcategories.OTHER_INCOME;
import static com.example.mobanko.entities.Subcategories.UNCATEGORIZED;
import static java.time.LocalDateTime.now;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobanko.R;
import com.example.mobanko.databinding.ActivitySignTransferBinding;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.Objects;
import java.util.UUID;


public class SignTransferActivity extends AppCompatActivity {

    ActivitySignTransferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        User userInfo = (User) intent.getSerializableExtra("userInfo");
        int accountId = intent.getIntExtra("accountId", -1);
        User receiverUser = (User) intent.getSerializableExtra("receiverInfo");
        Account receiverAccount = (Account) intent.getSerializableExtra("receiverAccount");
        double amountValue = intent.getDoubleExtra("amount", 0);

        Intent nextIntent = new Intent(this, WaitingScreen.class);


        TextView recipientName = (TextView) binding.getRoot().findViewById(R.id.textView29);
        TextView iban = (TextView) binding.getRoot().findViewById(R.id.textView31);
        TextView amount = (TextView) binding.getRoot().findViewById(R.id.textView33);
        TextView totalAmount = (TextView) binding.getRoot().findViewById(R.id.textView35);
        TextView returnText = (TextView) binding.getRoot().findViewById(R.id.textView27);
        ImageView returnImage = (ImageView) binding.getRoot().findViewById(R.id.sign_transfer_return_image);
        TextView signButton = (TextView) binding.getRoot().findViewById(R.id.signButton);

        returnImage.setOnClickListener(view -> finish());
   //     returnText.setOnClickListener(view -> finish());

        recipientName.setText(receiverUser.getName().toUpperCase());
        iban.setText(receiverAccount.getIBAN().toUpperCase());
        amount.setText(amountValue + " RON");
        totalAmount.setText(amountValue + " RON");

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference receiverAccountRef = db.collection("Accounts").document(receiverAccount.getIBAN());
                DocumentReference receiverUserRef = db.collection("Users").document(receiverUser.getId());
                DocumentReference senderAccountRef = db.collection("Accounts").document(userInfo.getAccounts().get(accountId).getIBAN());
                DocumentReference senderUserRef = db.collection("Users").document(userInfo.getId());

                db.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                DocumentSnapshot receiverAccountSnap = transaction.get(receiverAccountRef);
                                DocumentSnapshot receiverUserSnap = transaction.get(receiverUserRef);
                                DocumentSnapshot senderAccountSnap = transaction.get(senderAccountRef);
                                DocumentSnapshot senderUserSnap = transaction.get(senderUserRef);

                                double receiverBalance = receiverAccountSnap.getDouble("balance") + amountValue;
                                double senderBalance = senderAccountSnap.getDouble("balance") - amountValue;

                                // Update the balance in the transaction
                                transaction.update(receiverAccountRef, "balance", receiverBalance);
                                transaction.update(senderAccountRef, "balance", senderBalance);

                                User receiverUser = receiverUserSnap.toObject(User.class);
                                User senderUser = senderUserSnap.toObject(User.class);
                                Account receiverAccount = receiverAccountSnap.toObject(Account.class);
                                Account senderAccount = senderAccountSnap.toObject(Account.class);

                                com.example.mobanko.entities.Transaction receiverTransaction = new com.example.mobanko.entities.Transaction();
                                receiverTransaction.setBalance(amountValue);
                                receiverTransaction.setRecipientID(receiverAccount.getIBAN());
                                receiverTransaction.setSenderID(senderAccount.getIBAN());
                                receiverTransaction.setDateOfTransaction(now().toString());
                                receiverTransaction.setCurrencyType(RON);
                                receiverTransaction.setID(UUID.randomUUID().toString());
                                receiverTransaction.setCategories(UNCATEGORIZED_INCOME);
                                receiverTransaction.setSubcategories(OTHER_INCOME);
                                receiverTransaction.setRecipientName(receiverUser.getName());
                                receiverTransaction.setSenderName(senderUser.getName());
                                receiverTransaction.setPaymentType(INTRABANK);

                                com.example.mobanko.entities.Transaction senderTransaction = new com.example.mobanko.entities.Transaction();
                                senderTransaction.setBalance(amountValue);
                                senderTransaction.setRecipientID(receiverAccount.getIBAN());
                                senderTransaction.setSenderID(senderAccount.getIBAN());
                                senderTransaction.setDateOfTransaction(now().toString());
                                senderTransaction.setCurrencyType(RON);
                                senderTransaction.setID(UUID.randomUUID().toString());
                                senderTransaction.setCategories(UNCATEGORIZED_EXPENSES);
                                senderTransaction.setSubcategories(UNCATEGORIZED);
                                senderTransaction.setRecipientName(receiverUser.getName());
                                senderTransaction.setSenderName(senderUser.getName());
                                senderTransaction.setPaymentType(INTRABANK);


                                receiverUser.getAccounts().forEach(account -> {
                                    if (Objects.equals(account.getIBAN(), receiverAccount.getIBAN())) {
                                        double currentBalance = account.getBalance();
                                        account.setBalance(currentBalance + amountValue);
                                        account.getTransactions().add(receiverTransaction);
                                    }
                                });

                                receiverAccount.setBalance(receiverAccount.getBalance() + amountValue);
                                receiverAccount.getTransactions().add(receiverTransaction);

                                senderUser.getAccounts().forEach(account -> {
                                    if (Objects.equals(account.getIBAN(), senderAccount.getIBAN())) {
                                        double currentBalance = account.getBalance();
                                        account.setBalance(currentBalance - amountValue);
                                        account.getTransactions().add(senderTransaction);
                                    }
                                });

                                senderAccount.setBalance(senderAccount.getBalance() - amountValue);
                                senderAccount.getTransactions().add(senderTransaction);

                                transaction.set(receiverUserRef, receiverUser);
                                transaction.set(senderUserRef, senderUser);
                                transaction.set(receiverAccountRef, receiverAccount);
                                transaction.set(senderAccountRef, senderAccount);

                                return null;
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // The transaction was successfully completed.
                                // Handle the success case, if required.
                                System.out.println("SUCCESS\n");
                                startActivity(nextIntent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // The transaction failed.
                                // Handle the failure case, if required.
                                System.out.println("FAIL " + e.getMessage());
                                startActivity(nextIntent);

                            }
                        });



            }
        });


    }
}