package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.services.AccidentDetectionService;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    private TextView accelerometerText;
    private TextView soundMeterText;
    private Switch detectionSwitch;
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    private static final float THRESHOLD_ACCELEROMETER = 15.0f;
    private static final int THRESHOLD_SOUND = 40;
    boolean isAccelerometerSensorAvailable;
    private MediaRecorder mediaRecorder;
    private boolean mIsRecording = false;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        accelerometerText = findViewById(R.id.accelerometerText);
        soundMeterText = findViewById(R.id.soundMeterText);
        detectionSwitch = findViewById(R.id.switchDetection);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else{
            isAccelerometerSensorAvailable = false;
        }
        detectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    startDetectionService();
                }
                else
                {
                    stopDetectionService();
                }
            }
        });
    }
    private void startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
                return;
            }
        }

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile("/dev/null");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("AudioRecorder", "prepare() failed");
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
            return;
        }

        mediaRecorder.start();
        mIsRecording = true;

        // Start a thread to update the sound level
        new Thread(new Runnable() {
            public void run() {
                while (mIsRecording) {
                    try {
                        Thread.sleep(1000); // Update every second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Calculate the sound level
                    int amplitude = mediaRecorder.getMaxAmplitude();
                    double amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));

                    // Update UI or do something with the sound level
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            soundMeterText.setText("Amplitude: " + amplitude + " dB: " + amplitudeDb);
                        }
                    });
                }
            }
        }).start();
    }
    private void stopRecording() {
        if (mediaRecorder != null) {
            mIsRecording = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start recording or do other tasks that require this permission
            } else {
                // Permission denied, handle the denied state
            }
        }
    }
    // stop/start the detection system.
    private void startDetectionService() {

    }
    private void stopDetectionService() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDetectionService();
        stopRecording();
    }
    // The system in the background will be
    // continuously monitoring the Sound decibel value and accelerometer for any Accident type impacts.
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(x * x + y * y + z * z);
        String formattedAcceleration = String.format("%.2f", acceleration);
        accelerometerText.setText(formattedAcceleration);

        if (acceleration > THRESHOLD_ACCELEROMETER) {
            // Accident detected based on accelerometer
            Toast.makeText(this, "Accident detected!", Toast.LENGTH_SHORT).show();

            // If it finds the App Notifies the User to verify if it’s a false alarm,
            // if no action is done in 5 secs
            // the Ambulance is assigned & notifies Hospital,
            // Ambulance and Police about the accident with the location & User details.

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isAccelerometerSensorAvailable){
            sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        startRecording();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(isAccelerometerSensorAvailable){
            sensorManager.unregisterListener(this);
        }
        stopRecording();
    }
}