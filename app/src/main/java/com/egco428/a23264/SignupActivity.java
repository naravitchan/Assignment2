package com.egco428.a23264;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Random;

public class SignupActivity extends AppCompatActivity implements SensorEventListener {
    private long lastUpdate;
    private int countShake = 0;
    private boolean isPress=false;
    private CommentDataSource dataSource;
    EditText username;
    EditText password;
    EditText longitude;
    EditText latitude;
    private SensorManager sensorManager;
    Button addBtn;
    Button randomBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + "Sign-up Page" + "</font>"));
        mActionBar.setDisplayHomeAsUpEnabled(true);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        username = (EditText)findViewById(R.id.nametxt);
        password = (EditText)findViewById(R.id.passtxt);
        latitude = (EditText)findViewById(R.id.Latitude);
        longitude = (EditText)findViewById(R.id.Longitude);

        dataSource = new CommentDataSource(this);
        dataSource.open();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2) {
            if (actualTime - lastUpdate < 600) {
                return;
            }
            countShake += 1;
            lastUpdate = actualTime;
            if (countShake > 1) {
                doRandom();
                countShake = 0;
                return;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public void pressAdd(View view){

        String Username = username.getText().toString();
        String Password = password.getText().toString();
        String Latitude = latitude.getText().toString();
        String Longitude = longitude.getText().toString();

        Comment comment = dataSource.createComment(Username,Password,Longitude,Latitude);

        finish();

    }

    public void random(View view){
        doRandom();
    }

    public void doRandom(){

        Random r = new Random();
        double randomLatitude = 85.000000 - (170.000000) * r.nextDouble();
        randomLatitude =Double.parseDouble(new DecimalFormat("##.####").format(randomLatitude));
        latitude.setText(String.valueOf(randomLatitude));

        Random g = new Random();
        double randomLongitude = -179.999989 + (179.999989 - (-179.999989)) * g.nextDouble();
        randomLongitude =Double.parseDouble(new DecimalFormat("##.####").format(randomLongitude));
        longitude.setText(String.valueOf(randomLongitude));

    }
}
