package com.bytedance.todolist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bytedance.todolist.R;

public class newpageActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        initview();
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("text", String.valueOf(editText.getText()));
                setResult(1, data);
                finish();
            }
        });
    }

    private void initview() {
        editText = findViewById(R.id.write);
        button = findViewById(R.id.comfirm);
    }
}