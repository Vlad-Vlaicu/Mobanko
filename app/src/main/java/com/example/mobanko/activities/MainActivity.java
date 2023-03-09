package com.example.mobanko.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobanko.R;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

import static com.example.mobanko.entities.AccountType.CURRENT_ACCOUNT;
import static com.example.mobanko.entities.CurrencyType.RON;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String testEmail = "email@email.com";
        String testPass = "123456";

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        User user = new User();
        user.setId(new Random().nextLong());
        user.setAccounts(new ArrayList<>());
        user.setEmail(testEmail);
        user.setPhoneNumber("0720210312");

        Account account = new Account();
        account.setAccountHolderID(user.getId());
        account.setBalance(34345.34);
        account.setCurrencyType(RON);
        account.setIBAN("NEWIBAN");
        account.setAccountType(CURRENT_ACCOUNT);
        account.setTransactions(new ArrayList<>());

        user.getAccounts().add(account);


        firebaseAuth.createUserWithEmailAndPassword(testEmail, testPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                System.out.println("SUCCESS");
                Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                        .set(user);

                firebaseFirestore.collection("Accounts").document(FirebaseAuth.getInstance().getUid())
                        .set(account);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("FAIL");
            }
        });
    }


}