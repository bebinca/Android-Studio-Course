package com.assignment.message1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class newpage extends AppCompatActivity {
    private static final String TAG = "TAG";
    private TextView textView;
    private Bundle bundle;
    private Intent intent;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpage);
        Log.i(TAG, "LinearLayoutActivity onCreate");
        initView();
        print();
    }

    private void initView() {
        textView = findViewById(R.id.textpos);
        intent = this.getIntent();
        bundle = intent.getExtras();
        position = bundle.getInt("position");
    }

    private void print() {
        textView.setText("这是第" + position + "个item");
    }
}
