package com.example.tanviwagle.textfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class text extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.d("MainActivity", "Detector Dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(text.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    //String s = "";
                    if (items.size() != 0 ){
                        textView.post(new Runnable() {

                            public boolean isNumber(char c){
                                if ((c>=48)&&(c<=57)){
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public void run() {
                                String s="";
                                boolean checked=false;
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++){
                                    TextBlock item = items.valueAt(i);
                                    /*//stringBuilder.append(item.getValue());
                                    //stringBuilder.append("/n");
                                    //Log.d("hello","hey");
                                    Log.d("hello",item.getValue());
                                    if (item.getValue().contains("cal")){
                                        Log.d("hello","worked");
                                        textView.setText(item.getValue());
                                        cameraSource.stop();
                                        cameraView.setVisibility(View.INVISIBLE);
                                        item.getValue();
                                        //stringBuilder.append(item.getValue());
                                    }*/
                                    //if (!checked) {
                                        if ((item.getValue().contains("Calories")) && (item.getValue().length() > 9)) { //makes sure detects the calories and some characters(the number) after that
                                            Log.d("Processor", "Text detected! " + item.getValue());
                                            s = item.getValue();
                                            s = s.substring(s.indexOf("Calories")); //cut off everything before "calories"
                                            String[] lines = s.split("\r\n|\r|\n");
                                            for (String line : lines) {
                                                System.out.println(line);
                                            }
                                            s=lines[0];
                                            int startIndex = 0;
                                            for (int index = 0; index < s.length(); index++) { //setting start index to first #
                                                if (startIndex == 0) {
                                                    if (isNumber(s.charAt(index))) {
                                                        startIndex = index;
                                                    }
                                                }
                                            }
                                            s = s.substring(startIndex);
                                            int endIndex = s.length();
                                            for (int index = 0; index < s.length(); index++) { //set end index to first character that is NOT a number
                                                if (endIndex == s.length()) {
                                                    if (isNumber(s.charAt(index))) {
                                                        //
                                                    } else endIndex = index;
                                                }
                                            }
                                            s = s.substring(0, endIndex);
                                            int calories = -1;
                                            try {
                                                calories = Integer.valueOf(s);
                                                Log.d("calorieKEY", endIndex + "this food has " + calories + " calories");
                                                //Toast.makeText(AppCompatActivity.CON)
                                                Intent sendInfoBack = new Intent();
                                                sendInfoBack.putExtra(MainActivity.INTENT_CODE, calories);
                                                setResult(RESULT_OK, sendInfoBack);
                                                finish();

                                                checked = true;
                                            } catch (NumberFormatException e) {
                                                Log.d("calorieKey", "please try again, not detected: " + s);
                                            }
                                            ;
                                            textView.setText("this food has " + Integer.toString(calories) + " calories");
                                        }
                                    /*} else {
                                        cameraSource.stop();
                                        cameraView.setVisibility(View.INVISIBLE);
                                    }*/
//we are ending out own code here
                                }
                            }
                        });
                    }
                }
            });

        }

    }
}
