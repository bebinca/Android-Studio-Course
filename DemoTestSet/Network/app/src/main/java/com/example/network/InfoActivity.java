package com.example.network;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";

    public static final String NAME_EXTRA = "NAME_EXTRA";
    public static final String ICON_URL_EXTRA = "ICON_URL_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if( intent == null ){
            return;
        }
        String name = intent.getStringExtra(NAME_EXTRA);
        String iconUrl = intent.getStringExtra(ICON_URL_EXTRA);
        Log.i(TAG,"name:"+name +" iconUrl:"+iconUrl);

        TextView nameTV = findViewById(R.id.name_show);
        nameTV.setText(name);

        NetImageView icon = findViewById(R.id.icon_show);
        icon.setImageURL(iconUrl);


    }
}
