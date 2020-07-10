package com.example.network;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";

    EditText nameEditText;
    EditText pwdEditText;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViewsAndClickListener();
    }

    @SuppressLint("StaticFieldLeak")
    private void initViewsAndClickListener(){
        nameEditText = findViewById(R.id.name);
        pwdEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String pwd = pwdEditText.getText().toString();
            Log.i(TAG,"name:"+name +" pwd:"+pwd);

            new AsyncTask<Void, Void, String>(){
                @Override
                protected String doInBackground(Void... voids) {
                    String resultJson = login(name , pwd);
                    Log.i(TAG, "resultJson:"+resultJson);

                    return resultJson;
                }

                @Override
                protected void onPostExecute(String resultJson) {
                    Log.i(TAG,"resultJson:"+resultJson);
                    try {
                        JSONObject jsonObject = new JSONObject(resultJson);
                        String status = jsonObject.getString("status");
                        Log.i(TAG,"status:"+status);
                        if( "OK".equals(status) ){
                            String name = jsonObject.getString("name");
                            String iconUrl = jsonObject.getString("icon");
                            Log.i(TAG,"name:"+name+" iconUrl:"+iconUrl);

                            Intent intent = new Intent();
                            intent.putExtra(InfoActivity.NAME_EXTRA, name);
                            intent.putExtra(InfoActivity.ICON_URL_EXTRA, iconUrl);
                            intent.setClass(MainActivity.this, InfoActivity.class);
                            startActivity(intent);

                        }else{
                            Log.i(TAG, "wrong status");
                            Toast.makeText(MainActivity.this,"错误的用户名或者密码",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();


        });

    }

    public static String login(String username,String password) {
        String msg = "";
        try {
            username = URLEncoder.encode(username, "UTF-8");
            password = URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String urlStr = "https://api.daliapp.net/android-bootcamp/invoke/hello?name=" + username + "&password=" + password;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (msg == null) {
                    msg = line;
                } else {
                    msg += line;
                }
            }
            reader.close();
            in.close();//关闭数据流
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally { //做清理操作 }
            return msg;
        }
    }



}
