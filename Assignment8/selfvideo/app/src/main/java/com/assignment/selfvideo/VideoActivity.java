package com.assignment.selfvideo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private Bundle bundle;
    private Intent intent;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolayout);
        initview();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(VideoActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        videoView.start();
    }
    public void initview() {
        intent = this.getIntent();
        bundle = intent.getExtras();
        videoView = findViewById(R.id.videoview);
        videoView.setVideoPath(getVideoPath());
        button = findViewById(R.id.next_to_upload);
    }

    private String getVideoPath() {
        return bundle.getString("url");
    }
}
