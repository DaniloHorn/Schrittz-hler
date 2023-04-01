package ch.wiss.schrittzaehler20;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView stepCountTextView;
    private boolean isSensorRegistered = false;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCountTextView = findViewById(R.id.schritte);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
            isSensorRegistered = true;
        } else {
            Toast.makeText(this, "Step sensor not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("WORKS", "Pause");
        if (isSensorRegistered) {
            sensorManager.unregisterListener(this);
            isSensorRegistered = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount = (int) event.values[0];
        stepCountTextView.setText(String.valueOf(stepCount));
        Log.d("INFO", String.valueOf(stepCount));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }
}
