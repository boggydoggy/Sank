package com.example.sank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Remittance extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 제거
        setContentView(R.layout.activity_remittance);
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        int checkEz = pref.getInt("remitEz",0); //쉬운 문제 정답 확인
        int checkHz = pref.getInt("remitHz", 0); //어려운 문제 정답 확인

        Button Ez = (Button) findViewById(R.id.remitEQ);
        Button Hz = (Button) findViewById(R.id.remitHQ);

        if(checkEz == 1){ //맞췄다면
            Ez.setText("통과"); //버튼 이름을 통과로 바꾸고
            Ez.setEnabled(false); //버튼 비활성화
        }
        if(checkHz == 1){
            Hz.setText("통과"); //버튼 이름을 통과로 바꾸고
            Hz.setEnabled(false);
        }

    }
    public void popEz(View v){
        Intent intent = new Intent(this, remitEz.class);
        startActivity(intent);
        finish();
    }
    public void popHz(View v){
        Intent intent = new Intent(this, remitHz.class);
        startActivity(intent);
        finish();
    }
    public void onClose(View v){ finish(); }
}