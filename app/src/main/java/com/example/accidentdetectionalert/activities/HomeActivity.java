package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.Context;
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
import com.example.accidentdetectionalert.utils.LocationUtils;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    private TextView accelerometerText;
    private TextView soundMeterText;
    private Switch detectionSwitch;
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    private static final float THRESHOLD_ACCELEROMETER = 15.0f;
    private static final int THRESHOLD_SOUND = 80;
    boolean isAccelerometerSensorAvailable;
    private MediaRecorder mediaRecorder;
    private boolean mIsRecording = false;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Handler mHandler = new Handler();
    private AlertDialog alertDialog;
    private static final int NOTIFICATION_DELAY = 5000; // 5 seconds delay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        accelerometerText = findViewById(R.id.accelerometerText);
        soundMeterText = findViewById(R.id.soundMeterText);
        detectionSwitch = findViewById(R.id.switchDetection);

        getCurrentLocation();

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDetectionService();
        stopRecording();
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
        File outputFile = new File(getExternalCacheDir(), "test.3gp");
        mediaRecorder.setOutputFile(outputFile.getAbsolutePath());
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
    }
    private void stopRecording() {
        if (mediaRecorder != null) {
            mIsRecording = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    private void getCurrentLocation() {
        String currentLocation = LocationUtils.getCurrentLocation(this);
        Toast.makeText(this, "Current Location: " + currentLocation, Toast.LENGTH_SHORT).show();
    }
    // Handle the permissions request
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

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get current location
                getCurrentLocation();
            } else {
                // Permission denied, show a message or handle it gracefully
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // stop/start the detection system.
    private void startDetectionService() {
        startRecording();
        if(isAccelerometerSensorAvailable){
            sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    private void stopDetectionService() {
        stopRecording();
        if(isAccelerometerSensorAvailable){
            sensorManager.unregisterListener(this);
        }
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
            showAlertAndNotifyUser();
        }

        // Calculate the sound level
        if(mIsRecording)
        {
            int amplitude = mediaRecorder.getMaxAmplitude();
            double amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
            String formattedAmplitudeDb = String.format("%.2f", amplitudeDb);
            soundMeterText.setText("Amplitude: " + amplitude + " dB: " + formattedAmplitudeDb);

            if(amplitudeDb > THRESHOLD_SOUND)
            {
                showAlertAndNotifyUser();
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    // If it finds the App Notifies the User to verify if it’s a false alarm,
    // if no action is done in 5 secs
    // the Ambulance is assigned & notifies Hospital, Ambulance and Police about the accident
    // with the location & User details.
    private void showAlertAndNotifyUser() {
        if (alertDialog != null && alertDialog.isShowing()) {
            // Dialog is already showing, do nothing
            return;
        }

        // Show alert dialog to verify if it's a false alarm
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Accident detected! Verify if it's a false alarm.")
                .setCancelable(false)
                .setPositiveButton("Notify Authorities", (dialog, id) -> {
                    // User verifies and notifies authorities
                    assignAmbulanceAndNotifyAuthorities();
                    alertDialog.dismiss();
                    alertDialog = null;
                })
                .setNegativeButton("False Alarm!", (dialog, id) -> {
                    // User cancels, stop the notification and ambulance assignment
                    mHandler.removeCallbacksAndMessages(null);
                    alertDialog.dismiss();
                    alertDialog = null;
                });
        alertDialog = builder.create();
        alertDialog.show();

        // Schedule ambulance assignment after 5 seconds if no action is taken
        mHandler.postDelayed(() -> {
            if (alertDialog != null && alertDialog.isShowing()) {
                // No action taken, assign ambulance and notify authorities
                assignAmbulanceAndNotifyAuthorities();
                alertDialog.dismiss();
                alertDialog = null;
            }
        }, NOTIFICATION_DELAY);
    }

    private void assignAmbulanceAndNotifyAuthorities() {
        // Notify Hospital, Ambulance, and Police about the accident with the location & User details
        Toast.makeText(this, "Ambulance assigned! Notifying authorities...", Toast.LENGTH_SHORT).show();

        // You can implement the logic to notify authorities here
        // 1. Get the current location
        // 2. Send notification to hospital, ambulance, and police with the location and user details
    }
}