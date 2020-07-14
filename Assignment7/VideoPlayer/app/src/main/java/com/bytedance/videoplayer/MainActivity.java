package com.bytedance.videoplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.videoplayer.player.VideoPlayerIJK;
import com.bytedance.videoplayer.player.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg";

    private VideoPlayerIJK ijkPlayer;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private ImageView imageView;
    private SeekBar seekbar;
    private boolean seekusing;
    private int seekPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = findViewById(R.id.SeekBar);
        seekbar.setProgress(0);
        ijkPlayer = findViewById(R.id.ijkPlayer);
        imageView = findViewById(R.id.imageView);
        ijkPlayer.setVisibility(View.VISIBLE);
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener());
        //ijkPlayer.setVideoResource(R.raw.bytedance);
        ijkPlayer.setVideoPath(getVideoPath());
        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.start();
            }
        });
        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.pause();
            }
        });
        findViewById(R.id.buttonSeek).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.seekTo(20 * 1000);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                int min = seekbar.getMin();
                int max = seekbar.getMax();
                final float float_progress = (float)(progress-min)/(float)(max-min);
                seekPosition = (int)(ijkPlayer.getDuration()*float_progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekusing = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    ijkPlayer.seekTo((long)(seekPosition));
                    seekusing = false;
            }
        });
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                while(true){
                    if(ijkPlayer!=null&&ijkPlayer.isPlaying()&&!seekusing){
                        float per = (float)ijkPlayer.getCurrentPosition() / (float)ijkPlayer.getDuration();
                        int max = seekbar.getMax();
                        int min = seekbar.getMin();
                        seekbar.setProgress((int) (per * (max - min)));
                        Log.d("seekbar","percent = " + per);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        initImage();
    }

    private void initImage() {
        RoundedCorners c = new RoundedCorners(30);
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(this)
                .load(url)
                .apply(options.optionalTransform(c))
                .placeholder(R.drawable.icon_progress_bar)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.ic_launcher_background)
                .transition(withCrossFade(1000))
                .into(imageView);
    }

    private String getVideoPath() {
        return "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";
        // return "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkPlayer.isPlaying()) {
            ijkPlayer.stop();
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
