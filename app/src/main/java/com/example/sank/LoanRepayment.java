package com.example.sank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class LoanRepayment extends AppCompatActivity {

    static int loanpay = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_repayment);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        loanpay = pref.getInt("loankey",0);
        TextView m_TextView;
        m_TextView = findViewById(R.id.textViewLL);
        m_TextView.setText( loanpay +"원");

    }
    public void onChangeLoan(View v){
        Intent intent = new Intent(this, Loan.class);
        startActivity(intent);
        finish();
    }
    public void onChangeRepayment(View v){
        Intent intent = new Intent(this, Repayment.class);
        startActivity(intent);
        finish();
    }
    public  void onBackButtonClicked(View v){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
