package com.example.mobanko.extras;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobanko.R;
import com.example.mobanko.entities.Account;
import com.example.mobanko.entities.AccountType;
import com.example.mobanko.entities.CurrencyType;
import com.example.mobanko.entities.Transaction;
import com.example.mobanko.entities.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PDFExtras {

    public static int A4WIDTH = 595;
    public static int A4HEIGHT = 842;

    private ArrayList<Transaction> transactions;
    private Context context;

    private User user;
    private Account account;
    private Integer numar_extras;
    private LocalDateTime extras_date;


    private final Integer TRANSACTIONS_PER_PAGE = 4;


    private View transactions_view(LayoutInflater inflater, DisplayMetrics resolution)
    {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout_transactions, null);

        LinearLayout transactions_list = view.findViewById(R.id.more_transactions_lst);
        int number = TRANSACTIONS_PER_PAGE;
        while (transactions.size() > 0 && number > 0) {
            TransactionViewPDF tr = new TransactionViewPDF(context);
            tr.setTransaction(transactions.get(0));
            transactions_list.addView(tr);
            transactions.remove(0);
            number--;
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(
                        resolution.widthPixels, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                        resolution.heightPixels, View.MeasureSpec.EXACTLY
                )
        );
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        return view;
    }

    public void createPDF(String path)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout_pdf, null);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Log.d("Sizes", Integer.valueOf(displayMetrics.widthPixels).toString() + " " + Integer.valueOf(displayMetrics.heightPixels).toString());

        /* FILL INFO */
        TextView data_gen_extras_txt = view.findViewById(R.id.data_gen_extras_txt);
        TextView data_extras_txt = view.findViewById(R.id.data_extras_txt);
        TextView numar_extras_txt = view.findViewById(R.id.numar_extras_txt);

        TextView nume_user_txt = view.findViewById(R.id.nume_user_txt);
        TextView email_user_txt = view.findViewById(R.id.email_user_txt);
        TextView tel_user_txt = view.findViewById(R.id.nrtel_user_txt);
        TextView iban_txt = view.findViewById(R.id.IBAN_txt);
        TextView tipcont_txt = view.findViewById(R.id.tipcont_txt);
        TextView currency_txt = view.findViewById(R.id.currency_txt);


        data_extras_txt.append(extras_date.getDayOfMonth() + "/" + extras_date.getMonth() + "/" + extras_date.getYear());
        numar_extras_txt.append(numar_extras.toString());
        nume_user_txt.append(user.getName());

        email_user_txt.append(user.getEmail());
        tel_user_txt.append(user.getPhoneNumber());
        iban_txt.append(account.getIBAN());
        tipcont_txt.append(AccountType.getAccountTypeString(account.getAccountType()));
        currency_txt.append(CurrencyType.getCurrencyString(account.getCurrencyType()));

        data_gen_extras_txt.append(Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).toString() +
                "/" + Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)).toString() +
                "/" + Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR)).toString());


        RecyclerView transaction_list = view.findViewById(R.id.transactions_lst);
        transaction_list.setLayoutManager(new LinearLayoutManager(context));
        transaction_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        ArrayList<Transaction> front_transactions = new ArrayList<>();
        for (int i = 0; i < 2 && i < transactions.size(); i++) {
            front_transactions.add(transactions.get(0));
            transactions.remove(0);
        }

        TransactionsAdapter adapter = new TransactionsAdapter(context, front_transactions);
        transaction_list.setAdapter(adapter);


        /* SEND TO PDF */

        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        );

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        bitmap = Bitmap.createScaledBitmap(bitmap, A4WIDTH, A4HEIGHT, true);

        PdfDocument pdf = new PdfDocument();
        PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder(A4WIDTH, A4HEIGHT, 1).create();

        PdfDocument.Page startPage = pdf.startPage(info);

        startPage.getCanvas().drawBitmap(bitmap, 0F, 0F, null);
        pdf.finishPage(startPage);

        int page_number = 2;
        while (transactions.size() > 0) {
            View more_transactions_view = transactions_view(inflater, displayMetrics);
            PdfDocument.PageInfo infoPage = new PdfDocument.PageInfo.Builder(A4WIDTH, A4HEIGHT, page_number).create();
            PdfDocument.Page page = pdf.startPage(infoPage);
            Bitmap bmp = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            more_transactions_view.draw(c);
            bmp = Bitmap.createScaledBitmap(bmp, A4WIDTH, A4HEIGHT, true);
            page.getCanvas().drawBitmap(bmp, 0F, 0F, null);
            pdf.finishPage(page);
        }

        try {
            pdf.writeTo(Files.newOutputStream(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pdf.close();
    }
}
