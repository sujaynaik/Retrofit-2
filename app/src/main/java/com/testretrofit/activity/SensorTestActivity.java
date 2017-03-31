package com.testretrofit.activity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testretrofit.R;
import com.testretrofit.util.MyPreferencesManager;
import com.testretrofit.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SensorTestActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = SensorTestActivity.class.getSimpleName();
    private Context context = SensorTestActivity.this;

    private SensorManager sensorManager;
    @BindView(R.id.activity_sensor_test)
    RelativeLayout layout;
    @BindView(R.id.tvPoints)
    TextView tvPoints;
    @BindView(R.id.tvHighScore)
    TextView tvHighScore;
    private long lastUpdate;
    private float score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);
        ButterKnife.bind(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        tvHighScore.setText(score + MyPreferencesManager.getRecord(context) + "");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        Utils.loge(TAG, "x : " + x);
        Utils.loge(TAG, "y : " + y);
        Utils.loge(TAG, "z : " + z);

        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        Utils.loge(TAG, "accelationSquareRoot : " + accelationSquareRoot);
        long actualTime = event.timestamp;
        Utils.loge(TAG, "actualTime : " + actualTime);

        if (accelationSquareRoot >= 2) {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            score = accelationSquareRoot;
            tvPoints.setText(score + "");

            if (score > MyPreferencesManager.getRecord(context)) {
                tvHighScore.setText(score + "");
                Toast.makeText(this, "New record", Toast.LENGTH_SHORT).show();
                layout.setBackgroundColor(Color.GREEN);

            } else {
                layout.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
