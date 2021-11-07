package com.example.sank;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    static int currentcash=0;
    static int currentmoney = 0;

    //코인갯수
    static int currentBTC = 0;
    static int currentETH = 0;
    static int currentBCH = 0;
    static int currentXRP = 0;
    static int currentEOS = 0;

    //평균매수가
    static int avrBTC = 0;
    static int avrETH = 0;
    static int avrBCH = 0;
    static int avrXRP = 0;
    static int avrEOS = 0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        TextView accmoneyView, accmoneyView2;
        TextView curmoneyVIew, curmoneyVIew2;
        TextView bctText,btcText2,btcText3,btcText4;
        TextView ethText,ethText2,ethText3,ethText4;
        TextView bchText,bchText2,bchText3,bchText4;
        TextView xrpText,xrpText2,xrpText3,xrpText4;
        TextView eosText,eosText2,eosText3,eosText4;

        accmoneyView = findViewById(R.id.curmoney);
        accmoneyView2 = findViewById(R.id.curmoney2);
        curmoneyVIew = findViewById(R.id.accmoney);
        curmoneyVIew2 = findViewById(R.id.accmoney2);

        bctText = findViewById(R.id.btctext);
        btcText2 = findViewById(R.id.btctext2);
        btcText3 = findViewById(R.id.btctext3);
        btcText4 = findViewById(R.id.btctext4);
        ethText = findViewById(R.id.ethtext);
        ethText2 = findViewById(R.id.ethtext2);
        ethText3 = findViewById(R.id.ethtext3);
        ethText4 = findViewById(R.id.ethtext4);
        bchText = findViewById(R.id.bchtext);
        bchText2 = findViewById(R.id.bchtext2);
        bchText3 = findViewById(R.id.bchtext3);
        bchText4 = findViewById(R.id.bchtext4);
        xrpText = findViewById(R.id.xrptext);
        xrpText2 = findViewById(R.id.xrptext2);
        xrpText3 = findViewById(R.id.xrptext3);
        xrpText4 = findViewById(R.id.xrptext4);
        eosText = findViewById(R.id.eostext);
        eosText2 = findViewById(R.id.eostext2);
        eosText3 = findViewById(R.id.eostext3);
        eosText4 = findViewById(R.id.eostext4);

        currentcash = pref.getInt("accKey",0);
        currentmoney = pref.getInt("monKey",0);

        //코인 갯수
        currentBTC = pref.getInt("btc",0);
        currentETH = pref.getInt("eth",0);
        currentBCH = pref.getInt("bhc",0);
        currentXRP = pref.getInt("xrp",0);
        currentEOS = pref.getInt("eos",0);

        //평균매수가
        avrBTC = pref.getInt("avrbtc",0);
        avrETH = pref.getInt("avreth",0);
        avrBCH = pref.getInt("avrbhc",0);
        avrXRP = pref.getInt("avrxrp",0);
        avrEOS = pref.getInt("avreos",0);

        curmoneyVIew.setText("현재잔액");
        curmoneyVIew2.setText(currentmoney+"원");
        accmoneyView.setText("계좌잔액" );
        accmoneyView2.setText(currentcash+"원");

        bctText.setText("비트코인" );
        btcText2.setText(currentBTC+"개");
        btcText3.setText("평균매수가");
        btcText4.setText(avrBTC+"원");

        ethText.setText("이더리움");
        ethText2.setText(currentETH+"개");
        ethText3.setText("평균매수가");
        ethText4.setText(avrETH+"원");

        bchText.setText("비트캐시");
        bchText2.setText(currentBCH+"개");
        bchText3.setText("평균매수가");
        bchText4.setText(avrBCH+"원");

        xrpText.setText("리플");
        xrpText2.setText(currentXRP+"개");
        xrpText3.setText("평균매수가");
        xrpText4.setText(avrXRP+"원");

        eosText.setText("이오스");
        eosText2.setText(currentEOS+"개");
        eosText3.setText("평균매수가");
        eosText4.setText(avrEOS+"원");


    }
    public void onChangeIns(View v){
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
        finish();
    }

    public void onChangeLoanRepayment(View v){
        Intent intent = new Intent(this, LoanRepayment.class);
        startActivity(intent);
        finish();
    }

    public void onChangeStock(View v){
        Intent intent = new Intent(this, Stock.class);
        startActivity(intent);
        finish();
    }
    public void onChangeDepo(View v){
        Intent intent = new Intent(this, DepoWith.class);
        startActivity(intent);
        finish();
    }

}
