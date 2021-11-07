package com.example.sank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class withEz extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_with_ez);
    }
    public void toHome(View v){ finish();}

    public void wrong(View v){
        Toast.makeText(getApplicationContext(), "오답!", Toast.LENGTH_LONG).show();
        finish();
    }
    public void correct(View v){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        int mon = pref.getInt("monKey",0);
        Intent intent = new Intent(this, MainActivity.class);

        mon += 10000;
        editor.putInt("monKey",mon);
        editor.putInt("withEz",1);
        editor.commit();
        Toast.makeText(getApplicationContext(), "정답!", Toast.LENGTH_LONG).show();
        finish();
        startActivity(intent);

    }
}
