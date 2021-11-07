package com.example.sank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Instruction extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
    }
    public void popWith(View v){
        Intent intent = new Intent(this, withdrawl.class);
        startActivity(intent);
    }
    public void popSav(View v){
        Intent intent = new Intent(this, Saving.class);
        startActivity(intent);
    }
    public void popLoan(View v){
        Intent intent = new Intent(this, insLoan.class);
        startActivity(intent);
    }
    public void popStock(View v){
        Intent intent = new Intent(this, insStock.class);
        startActivity(intent);
    }
    public void popRemit(View c){
        Intent intent = new Intent(this, Remittance.class);
        startActivity(intent);
    }
    public void popBitcoin(View c){
        Intent intent = new Intent(this, bitcoin.class);
        startActivity(intent);
    }
    public  void onBackButtonClicked(View v){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
