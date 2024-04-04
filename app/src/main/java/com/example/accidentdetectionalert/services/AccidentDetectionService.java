package com.example.accidentdetectionalert.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.security.Provider;
import java.util.List;
import java.util.Map;

public class AccidentDetectionService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private MediaRecorder mediaRecorder;
    private static final float THRESHOLD_ACCELEROMETER = 15.0f; // Adjust threshold as needed
    private static final int THRESHOLD_SOUND = 40;
    private boolean isListening;
    private final IBinder binder = new LocalBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mediaRecorder = new MediaRecorder();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isListening) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isListening) {
            sensorManager.unregisterListener(this);
            isListening = false;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public class LocalBinder extends Binder {
        public AccidentDetectionService getService() {
            return AccidentDetectionService.this;
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(x * x + y * y + z * z);

        if (acceleration > THRESHOLD_ACCELEROMETER) {
            // Accident detected based on accelerometer
            Toast.makeText(this, "Accident detected!", Toast.LENGTH_SHORT).show();
        }

        int soundLevel = getSoundLevel();
        if (soundLevel > THRESHOLD_SOUND) {
            // Accident detected based on sound level
            Toast.makeText(this, "Sound level too high!", Toast.LENGTH_SHORT).show();
        }

        // Send sensor readings to HomeActivity
        Intent intent = new Intent("SensorReadings");
        intent.putExtra("acceleration", acceleration);
        intent.putExtra("soundLevel", soundLevel);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private void startRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null");

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    private int getSoundLevel() {
        if (mediaRecorder != null) {
            return mediaRecorder.getMaxAmplitude();
        }
        return 0;
    }
}
