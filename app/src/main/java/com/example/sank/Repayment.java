package com.example.sank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Repayment extends AppCompatActivity {

    int subcash = 0;
    int money =0;
    int loanpay = 0;

    public  void onBackButtonClicked(View v){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment);

        final EditText RepayMoney = findViewById(R.id.re1);
        Button button = findViewById(R.id.re2);

        SharedPreferences pref;
        final SharedPreferences.Editor editor;

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        money = pref.getInt("acckey",0);
        loanpay = pref.getInt("loankey",0);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //메인엑티비티에 보내기
                subcash = Integer.parseInt(""+RepayMoney.getText());
                if(subcash > money){
                    Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.",Toast.LENGTH_LONG).show();
                }else{
                    money -= subcash;
                    loanpay -= subcash;

                    if (loanpay < 0) loanpay = 0;
                    editor.putInt("loankey",loanpay);
                    editor.putInt("acckey",money);
                    editor.commit();
                }
                Intent intent = new Intent(Repayment.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
