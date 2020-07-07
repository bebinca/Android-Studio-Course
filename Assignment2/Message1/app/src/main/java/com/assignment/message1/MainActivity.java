package com.assignment.message1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.assignment.message1.recycler.MessegeSet;
import com.assignment.message1.recycler.MyAdapter;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "TAG";
    private Button contact;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MainActivity onCreate");
        initView();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicitIntent = new Intent();
                implicitIntent.setAction(Intent.ACTION_DIAL);
                startActivity(implicitIntent);
                Log.d(TAG, "Button pressed");
            }
        });
        Log.i(TAG, "MainActivity onCreate end; " + count(findViewById(R.id.main_)) + "views");
    }
    private void initView() {
        contact = findViewById(R.id.contact);
        mAdapter = new MyAdapter(MessegeSet.getData());
        mAdapter.setOnItemClickListener(new MyAdapter.IOnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                if (position == 0) return;
                Intent intent1 = new Intent(MainActivity.this, newpage.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    public int count(View RootPage) {
        int ans = 0;
        if (RootPage == null) return ans;
        if (RootPage instanceof ViewGroup) {
            ans ++;
            for (int i = 0; i < ((ViewGroup) RootPage).getChildCount(); i ++) {
                View temp = ((ViewGroup) RootPage).getChildAt(i);
                if (temp instanceof ViewGroup) {
                    ans += count(temp);
                } else {
                    ans ++;
                }
            }
        }
        return ans;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "MainActivity onResume " + count(findViewById(R.id.main_)) + "views");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "MainActivity onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity onDestroy");
    }


}