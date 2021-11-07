package com.example.sank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Loan extends AppCompatActivity {

    int cash = 0;
    int money =0;
    int loanpay = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan);

        final EditText LoanMoney = findViewById(R.id.LoanMoney);
        Button button = findViewById(R.id.LoanButton);
        SharedPreferences pref;
        final SharedPreferences.Editor editor;

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        money = pref.getInt("acckey",0);
        loanpay = pref.getInt("loankey",0);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //메인엑티비티에 보내
                cash = Integer.parseInt(""+LoanMoney.getText());

                if((loanpay + cash) > 300000000){ // 3억 한도초과
                    Toast.makeText(getApplicationContext(), "한도초과입니다.(최대 30억)",Toast.LENGTH_LONG).show();
                }else{
                    money += cash;
                    loanpay += cash;

                    editor.putInt("loankey",loanpay);
                    editor.putInt("acckey",money);
                    editor.commit();

                }
                Intent intent = new Intent(Loan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public  void onBackButtonClicked(View v){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
