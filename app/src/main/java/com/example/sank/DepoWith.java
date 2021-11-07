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

import org.w3c.dom.Text;

public class DepoWith extends AppCompatActivity {
    int mon,accMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_with);
        final EditText deWi = findViewById((R.id.inputVal));
        Button deButton = findViewById(R.id.deposit);
        Button wiButton = findViewById(R.id.withdrawl);
        final SharedPreferences pref;
        final SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        accMon = pref.getInt("acckey",0); //계좌잔액
        mon = pref.getInt("monKey",0);

        deButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //입금
                int cash;
                cash = Integer.parseInt(""+deWi.getText()); //입력한 숫자를 String에서 Int로
                if(mon - cash >= 0) {
                    mon -= cash;
                    accMon += cash;
                    editor.putInt("accKey", accMon); //계좌 금액에 추가
                    editor.putInt("monKey", mon); //수중 금액에 감소
                    editor.commit();

                    finish();
                    Intent intent = new Intent(DepoWith.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "현재 금액보다 많은 양을 입금할 순 없습니다!", Toast.LENGTH_LONG).show();
                }
            }
        });

        wiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //출금
                int cash;
                cash = Integer.parseInt(""+deWi.getText());
                if(accMon - cash >= 0) {
                    accMon -= cash;
                    mon += cash;
                    editor.putInt("monKey", mon); //수중의 금액에 추가
                    editor.putInt("accKey", accMon); //계좌 금액에 감소
                    editor.commit();

                    finish();
                    Intent intent = new Intent(DepoWith.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "계좌 금액보다 많은 양을 출금할 순 없습니다!"+accMon, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void onBackButton(View v){finish();}
}
