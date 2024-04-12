package com.example.accidentdetectionalert.fragments;

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

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.utils.LocationUtils;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHome extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    public UserHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHome.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHome newInstance(String param1, String param2) {
        UserHome fragment = new UserHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        accelerometerText = view.findViewById(R.id.accelerometerText);
        soundMeterText = view.findViewById(R.id.soundMeterText);
        detectionSwitch = view.findViewById(R.id.switchDetection);

        getCurrentLocation();

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
    private void getCurrentLocation() {
        String currentLocation = LocationUtils.getCurrentLocation(getContext().getApplicationContext());
        Toast.makeText(requireContext(), "Current Location: " + currentLocation, Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        Toast.makeText(requireContext(), "Ambulance assigned! Notifying authorities...", Toast.LENGTH_SHORT).show();

        // You can implement the logic to notify authorities here
        // 1. Get the current location
        // 2. Send notification to hospital, ambulance, and police with the location and user details
    }
}