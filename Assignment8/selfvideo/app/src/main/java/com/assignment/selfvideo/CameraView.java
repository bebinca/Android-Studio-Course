package com.assignment.selfvideo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraView extends AppCompatActivity {

    private FloatingActionButton button;
    private Camera camera;
    private CameraPreview preview;
    private FrameLayout frameLayout;
    private MediaRecorder mediaRecorder;
    private boolean isRecording;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        isRecording = false;
        button = findViewById(R.id.fab_camera);
        frameLayout = findViewById(R.id.camera_preview);
        initCameraView();
        initButton();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (camera == null) {
            initCamera();
        }
        camera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.stopPreview();
    }

    private void initCameraView() {
        initCamera();
        preview = new CameraPreview(this, camera);
        frameLayout.addView(preview);
    }

    private void initCamera() {
        camera = getCameraInstance();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
    }

    private void initButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "click");
                if (!isRecording) {
                    if (prepareVideoRecorder()) {
                        Log.d("MainActivity","is recording");
                        mediaRecorder.start();
                        isRecording = true;
                    }
                    else {
                        releaseMediaRecorder();
                    }
                }
                else {
                    Log.d("MainActivity","end recording");
                    mediaRecorder.stop();
                    releaseMediaRecorder();
                    camera.lock();
                    isRecording = false;
                    Intent intent = new Intent(CameraView.this, VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
        }
    }

    private String getOutputMediaPath() {
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        //File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
         //           Environment.DIRECTORY_MOVIES), "CameraView");
        if(mediaStorageDir == null) Log.d("MainActivity", "fail to create file");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir, "VID_" + timeStamp + ".mp4");
        if (!mediaFile.exists()) {
            mediaFile.getParentFile().mkdirs();
        }
        Log.d("MainActivity",mediaFile.getAbsolutePath());
        return mediaFile.getAbsolutePath();
    }

    private static String getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SelfVideo");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        if (!mediaFile.exists()){
            mediaFile.getParentFile().mkdirs();
        }
        Log.d("MainActivity",mediaFile.getAbsolutePath());
        return mediaFile.getAbsolutePath();
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.d("MainActivity", "open camera failed");
        }
        return c; // returns null if camera is unavailable
    }

    private boolean prepareVideoRecorder() {
        mediaRecorder = new MediaRecorder();
        camera.unlock();
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        url = getOutputMediaPath();
        mediaRecorder.setOutputFile(url);
        mediaRecorder.setPreviewDisplay(preview.getHolder().getSurface());
        mediaRecorder.setOrientationHint(90);
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("MainActivity", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d("MainActivity", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }
}
