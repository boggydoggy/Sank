package com.example.sank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class Stock extends AppCompatActivity {

    int money = 0;
    int cnt = 0;
    int cash = 0;
    int coinvalue = 0;
    String coinName = "";

    public  void onBackButtonClicked(View v){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock2);

        final Spinner coinSpinner = (Spinner)findViewById(R.id.coinselect);
        ArrayAdapter coinAdapter = ArrayAdapter.createFromResource(this,
                R.array.coin, android.R.layout.simple_spinner_item);
        coinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coinSpinner.setAdapter(coinAdapter);

        TextView textView1 = (TextView)findViewById(R.id.parsetext);
        TextView textView2 = (TextView)findViewById(R.id.parsetext2);
        TextView textView3 = (TextView)findViewById(R.id.parsetext3);
        TextView textView4 = (TextView)findViewById(R.id.parsetext4);
        TextView textView5 = (TextView)findViewById(R.id.parsetext5);
        String BTC = "";
        String ETH = "";
        String BCH = "";
        String XRP = "";
        String EOS = "";

        try {
            String bitco = new Task().execute().get();
            String ethco = new Task2().execute().get();
            String bchco = new Task3().execute().get();
            String xrpco = new Task4().execute().get();
            String eosco = new Task5().execute().get();

            BTC = "비트코인                          " + bitco +"원";
            ETH = "이더리움                              " + ethco +"원";
            BCH = "비트코인캐시                       " + bchco +"원";
            XRP = "리플                                          " + xrpco +"원";
            EOS = "이오스                                     " + eosco +"원";

            SharedPreferences pref;
            SharedPreferences.Editor editor;
            pref = getSharedPreferences("pref", MODE_PRIVATE);
            editor = pref.edit();
            editor.putString("btcprice",bitco);
            editor.putString("ethprice",ethco);
            editor.putString("bhcprice",bchco);
            editor.putString("xrpprice",xrpco);
            editor.putString("eosprice",eosco);
            editor.commit();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        textView1.setText(BTC);
        textView2.setText(ETH);
        textView3.setText(BCH);
        textView4.setText(XRP);
        textView5.setText(EOS);

        final EditText buycash = findViewById(R.id.buymoney); //살갯수
        final EditText sellcash = findViewById(R.id.sellmoney); //판매할갯수
        Button buybutton = findViewById(R.id.buy); //구매버튼
        Button sellbutton = findViewById(R.id.sell); //판매버튼

        buybutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //메인엑티비티에 보내기
                final SharedPreferences pref;
                final SharedPreferences.Editor editor;
                String seltem = coinSpinner.getSelectedItem().toString(); //선택한 코인이름
                pref = getSharedPreferences("pref", MODE_PRIVATE);
                editor = pref.edit();
                money = pref.getInt("acckey",0); //계좌잔액
                cnt = Integer.parseInt(""+buycash.getText()); //입력한 갯수

                if(seltem.equals("비트코인")){
                    String coin = pref.getString("btcprice", "0"); //btc 갯수
                    coinvalue = Integer.parseInt(coin); //btc가격
                    cash = cnt * coinvalue; //입력한 갯수 * btc가격
                    if (money - cash < 0){
                        //잔액이부족합니다.
                        Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.1",Toast.LENGTH_LONG).show();
                    }else{
                        int avrbtc = pref.getInt("avrbtc",0); //평균매수가
                        int btccnt = pref.getInt("btc",0); //가진코인갯수
                        money -= cash;
                        int allmoney = avrbtc*btccnt + cash;
                        btccnt += cnt;
                        int newavr = allmoney/btccnt;
                        editor.putInt("avrbtc",newavr);
                        editor.putInt("btc",btccnt);
                    }
                }else if(seltem.equals("이더리움")){
                    String coin = pref.getString("ethprice", "0");
                    coinvalue = Integer.parseInt(coin);
                    cash = cnt * coinvalue;
                    if (money - cash < 0){
                        //잔액이부족합니다.
                        Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        int avreth = pref.getInt("avreth",0); //평균매수가
                        int ethcnt = pref.getInt("eth",0);
                        money -= cash;
                        int allmoney = avreth*ethcnt + cash;
                        ethcnt += cnt;
                        int newavr = allmoney/ethcnt;
                        editor.putInt("avreth",newavr);
                        editor.putInt("eth",ethcnt);
                    }
                }else if(seltem.equals("비트코인캐시")){
                    String coin = pref.getString("bhcprice", "0");
                    coinvalue = Integer.parseInt(coin);
                    cash = cnt * coinvalue;
                    if (money - cash < 0){
                        //잔액이부족합니다.
                        Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        int avrbhc = pref.getInt("avrbhc",0); //평균매수가
                        int bhccnt = pref.getInt("bhc",0);
                        money -= cash;
                        int allmoney = avrbhc*bhccnt + cash;
                        bhccnt += cnt;
                        int newavr = allmoney/bhccnt;
                        editor.putInt("avrbhc",newavr);
                        editor.putInt("bhc",bhccnt);
                    }
                }else if(seltem.equals("리플")){
                    String coin = pref.getString("xrpprice", "0");
                    coinvalue = Integer.parseInt(coin);
                    cash = cnt * coinvalue;
                    if (money - cash < 0){
                        //잔액이부족합니다.
                        Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        int avrxrp = pref.getInt("avrxrp",0); //평균매수가
                        int xrpcnt = pref.getInt("xrp",0);
                        money -= cash;
                        int allmoney = avrxrp*xrpcnt + cash;
                        xrpcnt += cnt;
                        int newavr = allmoney/xrpcnt;
                        editor.putInt("avrxrp",newavr);
                        editor.putInt("xrp",xrpcnt);
                    }
                }else if(seltem.equals("이오스")){
                    String coin = pref.getString("eosprice", "0");
                    coinvalue = Integer.parseInt(coin);
                    cash = cnt * coinvalue;
                    if (money - cash < 0){
                        //잔액이부족합니다.
                        Toast.makeText(getApplicationContext(), "계좌 잔액이 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        int avreos = pref.getInt("avreos",0); //평균매수가
                        int eoscnt = pref.getInt("eos",0);
                        money -= cash;
                        int allmoney = avreos*eoscnt + cash;
                        eoscnt += cnt;
                        int newavr = allmoney/eoscnt;
                        editor.putInt("avreos",newavr);
                        editor.putInt("eos",eoscnt);
                    }
                }

                editor.putInt("acckey",money);
                editor.commit();
                Intent intent = new Intent(Stock.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //sel버튼
        sellbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //메인엑티비티에 보내기
                final SharedPreferences pref;
                final SharedPreferences.Editor editor;
                String seltem = coinSpinner.getSelectedItem().toString(); //선택한 코인이름
                pref = getSharedPreferences("pref", MODE_PRIVATE);
                editor = pref.edit();
                money = pref.getInt("acckey",0); //계좌잔액
                cnt = Integer.parseInt(""+sellcash.getText()); //입력한 갯수

                if(seltem.equals("비트코인")){
                    String coin = pref.getString("btcprice", "0"); //btc가격
                    coinvalue = Integer.parseInt(coin); //btc가격
                    cash = cnt * coinvalue; //입력한 갯수 * btc가격
                    int btccnt = pref.getInt("btc",0);

                    if (btccnt < cnt){
                        //코인이부족합니다.
                        Toast.makeText(getApplicationContext(), "비트코인 갯수 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        money += cash;
                        btccnt -= cnt;
                        if(btccnt == 0){ //코인이 0개면 평균매수가 0으로 초기화
                            editor.putInt("avrbtc",0);
                        }
                        editor.putInt("btc",btccnt);
                    }
                }else if(seltem.equals("이더리움")){
                    String coin = pref.getString("ethprice", "0"); //eth가격
                    coinvalue = Integer.parseInt(coin); //eth가격
                    cash = cnt * coinvalue; //입력한 갯수 * eth가격
                    int ethcnt = pref.getInt("eth",0);

                    if (ethcnt < cnt){
                        //코인이부족합니다.
                        Toast.makeText(getApplicationContext(), "이더리움 갯수 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        money += cash;
                        ethcnt -= cnt;
                        if(ethcnt == 0){ //코인이 0개면 평균매수가 0으로 초기화
                            editor.putInt("avreth",0);
                        }
                        editor.putInt("eth",ethcnt);
                    }
                }else if(seltem.equals("비트코인캐시")){
                    String coin = pref.getString("bhcprice", "0"); //bhc가격
                    coinvalue = Integer.parseInt(coin); //bhc가격
                    cash = cnt * coinvalue; //입력한 갯수 * bhc가격
                    int bhccnt = pref.getInt("bhc",0);

                    if (bhccnt < cnt){
                        //코인이부족합니다.
                        Toast.makeText(getApplicationContext(), "비트코인캐시 갯수 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        money += cash;
                        bhccnt -= cnt;
                        if(bhccnt == 0){ //코인이 0개면 평균매수가 0으로 초기화
                            editor.putInt("avrbhc",0);
                        }
                        editor.putInt("bhc",bhccnt);
                    }
                }else if(seltem.equals("리플")){
                    String coin = pref.getString("xrpprice", "0"); //xrp가격
                    coinvalue = Integer.parseInt(coin); //xrp가격
                    cash = cnt * coinvalue; //입력한 갯수 * xrp가격
                    int xrpcnt = pref.getInt("xrp",0);

                    if (xrpcnt < cnt){
                        //코인이부족합니다.
                        Toast.makeText(getApplicationContext(), "리플 갯수 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        money += cash;
                        xrpcnt -= cnt;
                        if(xrpcnt == 0){ //코인이 0개면 평균매수가 0으로 초기화
                            editor.putInt("avrxrp",0);
                        }
                        editor.putInt("xrp",xrpcnt);
                    }
                }else if(seltem.equals("이오스")){
                    String coin = pref.getString("eosprice", "0"); //eos가격
                    coinvalue = Integer.parseInt(coin); //eos가격
                    cash = cnt * coinvalue; //입력한 갯수 * eos가격
                    int eoscnt = pref.getInt("eos",0);

                    if (eoscnt < cnt){
                        //코인이부족합니다.
                        Toast.makeText(getApplicationContext(), "이오스 갯수 부족합니다.",Toast.LENGTH_LONG).show();
                    }else{
                        money += cash;
                        eoscnt -= cnt;
                        if(eoscnt == 0){ //코인이 0개면 평균매수가 0으로 초기화
                            editor.putInt("avreos",0);
                        }
                        editor.putInt("eos",eoscnt);
                    }
                }

                editor.putInt("acckey",money);
                editor.commit();
                Intent intent = new Intent(Stock.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

class Task extends AsyncTask<String, Void, String>{

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            //  url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-BTC&count=1");
            url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/lines?code=CRIX.UPBIT.KRW-BTC");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String []s = getPrice(receiveMsg);
        return s[0];
    }


    public String[] getPrice(String jsonString) {

        String tradePrice  = null;
        String[] arraysum = new String[1];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("candles");

            HashMap map = new HashMap<>();
            JSONObject jObject = jarray.getJSONObject(0);
            tradePrice = jObject.optString("tradePrice");

            double a = Double.parseDouble(tradePrice);
            arraysum[0] = Integer.toString((int)a);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arraysum;
    }
}

class Task2 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            //  url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-BTC&count=1");
            url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/lines?code=CRIX.UPBIT.KRW-ETH");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] s = getPrice(receiveMsg);
        return s[0];
    }

    public String[] getPrice(String jsonString) {

        String tradePrice  = null;
        String[] arraysum = new String[1];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("candles");

            //HashMap map = new HashMap<>();
            JSONObject jObject = jarray.getJSONObject(0);
            tradePrice = jObject.optString("tradePrice");

            double a = Double.parseDouble(tradePrice);
            arraysum[0] = Integer.toString((int)a);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }
}
class Task3 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            //  url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-BTC&count=1");
            url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/lines?code=CRIX.UPBIT.KRW-BCH");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] s = getPrice(receiveMsg);
        return s[0];
    }

    public String[] getPrice(String jsonString) {

        String tradePrice  = null;

        String[] arraysum = new String[1];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("candles");

            //HashMap map = new HashMap<>();
            JSONObject jObject = jarray.getJSONObject(0);
            tradePrice = jObject.optString("tradePrice");

            double a = Double.parseDouble(tradePrice);
            arraysum[0] = Integer.toString((int)a);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }
}
class Task4 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            //  url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-BTC&count=1");
            url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/lines?code=CRIX.UPBIT.KRW-XRP");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] s = getPrice(receiveMsg);
        return s[0];
    }

    public String[] getPrice(String jsonString) {

        String tradePrice  = null;

        String[] arraysum = new String[1];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("candles");

            //HashMap map = new HashMap<>();
            JSONObject jObject = jarray.getJSONObject(0);
            tradePrice = jObject.optString("tradePrice");

            double a = Double.parseDouble(tradePrice);
            arraysum[0] = Integer.toString((int)a);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }
}
class Task5 extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            //  url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-BTC&count=1");
            url = new URL("https://crix-api-endpoint.upbit.com/v1/crix/candles/lines?code=CRIX.UPBIT.KRW-EOS");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] s = getPrice(receiveMsg);
        return s[0];
    }

    public String[] getPrice(String jsonString) {

        String tradePrice  = null;

        String[] arraysum = new String[1];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("candles");

            //HashMap map = new HashMap<>();
            JSONObject jObject = jarray.getJSONObject(0);
            tradePrice = jObject.optString("tradePrice");

            double a = Double.parseDouble(tradePrice);
            arraysum[0] = Integer.toString((int)a);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraysum;
    }
}

class SearchActivity extends Activity {
    public static String select_item="";
    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock2);

        Spinner coinSpinner = (Spinner)findViewById(R.id.coinselect);
        ArrayAdapter coinAdapter = ArrayAdapter.createFromResource(this,
                R.array.coin, android.R.layout.simple_spinner_item);
        coinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coinSpinner.setAdapter(coinAdapter);

        String seltem = coinSpinner.getSelectedItem().toString();
        SharedPreferences pref;
        final SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("seltem",seltem);
        editor.commit();

    }
}

