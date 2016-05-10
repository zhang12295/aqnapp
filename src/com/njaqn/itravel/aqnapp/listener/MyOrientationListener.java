package com.njaqn.itravel.aqnapp.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener {

	private Context context;
	private SensorManager sensorManager;
	private Sensor magneticSensor;
	private Sensor accelerometerSensor;

	private float lastX;

	private OnOrientationListener onOrientationListener;

	public void setOnOrientationListener(
			OnOrientationListener onOrientationListener) {
		this.onOrientationListener = onOrientationListener;
	}

	public MyOrientationListener(Context context) {
		this.context = context;
	}

	// 开始
	public void start() {
		// 获得传感器管理器
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// 获得方向传感器
			magneticSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			accelerometerSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

			if (magneticSensor != null && accelerometerSensor != null) {
				sensorManager.registerListener(this, magneticSensor,
						SensorManager.SENSOR_DELAY_GAME);
				sensorManager.registerListener(this, accelerometerSensor,
						SensorManager.SENSOR_DELAY_GAME);

			}

		}

	}

	// 停止检测
	public void stop() {
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}

	}

	float[] acceleromaterValues = new float[3];
	float[] magneticValues = new float[3];

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 接受方向感应器的类型
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			acceleromaterValues = event.values.clone();

		} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			magneticValues = event.values.clone();
		}
		float[] R = new float[9];
		float[] values = new float[3];
		sensorManager.getRotationMatrix(R, null, acceleromaterValues,
				magneticValues);
		sensorManager.getOrientation(R, values);
		lastX = (float) Math.toDegrees(values[0]);
		onOrientationListener.onOrientationChanged(lastX);
	}

	public interface OnOrientationListener {
		void onOrientationChanged(float x);
	}
}
