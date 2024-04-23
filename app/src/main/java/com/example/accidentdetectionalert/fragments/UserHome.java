package com.example.accidentdetectionalert.fragments;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.accidentdetectionalert.R;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;
import com.example.accidentdetectionalert.models.HistoryAlert;
import com.example.accidentdetectionalert.models.User;
import com.example.accidentdetectionalert.utils.LocationUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class UserHome extends Fragment implements SensorEventListener {
    private TextView accelerometerText;
    private TextView soundMeterText;
    private Switch detectionSwitch;
    // Accelerometer
    boolean isAccelerometerSensorAvailable;
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    private static final float THRESHOLD_ACCELEROMETER = 15.0f;
    // Sound
    private static final int THRESHOLD_SOUND = 80;
    private MediaRecorder mediaRecorder;
    private boolean mIsRecording = false;
    // Permissions
    private static final int RECORD_AUDIO_PERMISSION_CODE = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Handler mHandler = new Handler();
    private AlertDialog alertDialog;
    private static final int NOTIFICATION_DELAY = 5000; // 5 seconds delay
    String currentLocation;
    DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        accelerometerText = view.findViewById(R.id.accelerometerText);
        soundMeterText = view.findViewById(R.id.soundMeterText);
        detectionSwitch = view.findViewById(R.id.switchDetection);

        databaseHelper = new DatabaseHelper(getActivity());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        requestLocationPermission();

        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);

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
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopDetectionService();
        stopRecording();
    }
    private void startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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
        File outputFile = new File(requireContext().getExternalCacheDir(), "test.3gp");
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
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            // You can now perform your location-related tasks
            getCurrentLocation();
        } else {
            // Request the permission from the user
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    private void getCurrentLocation() {
        String location = LocationUtils.getCurrentLocation(getContext().getApplicationContext());
        currentLocation = location;
        Toast.makeText(requireContext(), "Current Location: " + location, Toast.LENGTH_SHORT).show();
    }
    // Handle the permissions request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start recording or do other tasks that require this permission
                startRecording();
            } else {
                // Permission denied, handle the denied state
                stopRecording();
            }
        }

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get current location
                getCurrentLocation();
            } else {
                // Permission denied, show a message or handle it gracefully
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(requireContext(), "Accident detected!", Toast.LENGTH_SHORT).show();
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

    private void showAlertAndNotifyUser() {
        if (alertDialog != null && alertDialog.isShowing()) {
            // Dialog is already showing, do nothing
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        String dateTime = DateFormat.getDateTimeInstance().format(new Date(currentTimeMillis));

        // Show alert dialog to verify if it's a false alarm
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Accident detected! Verify if it's a false alarm.")
                .setCancelable(false)
                .setPositiveButton("Notify Authorities", (dialog, id) -> {
                    // User verifies and notifies authorities
                    assignAmbulanceAndNotifyAuthorities();

                    HistoryAlert historyAlert = new HistoryAlert(databaseHelper.getUser(userId), dateTime, "Assigned Authorities!");
                    databaseHelper.createHistoryAlert(historyAlert);

                    alertDialog.dismiss();
                    alertDialog = null;
                })
                .setNegativeButton("False Alarm!", (dialog, id) -> {
                    // User cancels, stop the notification and ambulance assignment

                    HistoryAlert historyAlert = new HistoryAlert(databaseHelper.getUser(userId), dateTime, "False Alarm!");
                    databaseHelper.createHistoryAlert(historyAlert);

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
        Toast.makeText(requireContext(), "Ambulance assigned! Notifying authorities...", Toast.LENGTH_SHORT).show();

        // Get current date and time
        long currentTimeMillis = System.currentTimeMillis();
        String dateTime = DateFormat.getDateTimeInstance().format(new Date(currentTimeMillis));

        // Creating the accident will notify every authority about the accident
        Accident accident = new Accident(databaseHelper.getUser(userId), dateTime, currentLocation, "None");
        databaseHelper.createAccident(accident);

        // The assigned ambulance is able to update the status of the accident
        databaseHelper.assignAmbulance(accident.getAccidentId());
    }
}