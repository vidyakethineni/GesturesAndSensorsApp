package com.example.project10

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.project10.databinding.ActivitySensorBinding
import java.io.IOException
import java.util.*

/**
 * Activity to display sensor data and gesture navigation.
 */
class SensorActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var binding: ActivitySensorBinding
    private lateinit var viewModel: SensorViewModel
    private lateinit var gestureDetector: GestureDetector
    /**
     * Sensors for temperature, humidity, and pressure
     */
    private val temperatureSensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) }
    private val humiditySensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) }
    private val pressureSensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) }
    /**
     * Initializes the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sensor)

        viewModel = ViewModelProvider(this).get(SensorViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Initialize gesture detector
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                navigateToGestureActivity()
                Log.d(TAG, "Fling occurred")
                Toast.makeText(this@SensorActivity, "Fling occurred", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        binding.gestureButton.setOnClickListener{
            startActivity(Intent(this,ThirdActivity::class.java))
        }
    }
    /**
     * Handles touch events on the screen.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event?:return false)
        return super.onTouchEvent(event)
    }
    /**
     * Registers sensor listeners and obtains location information when the activity starts.
     */
    override fun onStart() {
        super.onStart()

        // Register temperature sensor listener
        if (temperatureSensor != null) {
            sensorManager.registerListener(object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    val temperatureValue = event?.values?.get(0)
                    viewModel.temperature.set("Temperature: $temperatureValue")
                    Log.d(TAG, "Temperature sensor value changed: $temperatureValue")
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    // Handle accuracy change if needed
                    Log.d(TAG, "Temperature sensor accuracy changed: $accuracy")
                }
            }, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Register humidity sensor listener
        if (humiditySensor != null) {
            sensorManager.registerListener(object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    val humidityValue = event?.values?.get(0)
                    viewModel.humidity.set("Humidity: $humidityValue")
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    // Handle accuracy change if needed
                }
            }, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Register pressure sensor listener
        if (pressureSensor != null) {
            sensorManager.registerListener(object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    val pressureValue = event?.values?.get(0)
                    viewModel.airPressure.set("Air Pressure: $pressureValue")
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    // Handle accuracy change if needed
                }
            }, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Get location information
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(39.168804, -86.536659, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                viewModel.location.set("Location: ${address.getAddressLine(0)}")
                viewModel.city.set("City: ${address.locality}")
                viewModel.state.set("State: ${address.adminArea}")
                Log.d(TAG, "Location information retrieved: ${address.getAddressLine(0)}")
            }
        } catch (e: IOException) {
//            e.printStackTrace()
            Log.e(TAG, "Error getting location information", e)
        }

    }
    /**
     * Navigates to the GestureActivity.
     */
    private fun navigateToGestureActivity() {
        startActivity(Intent(this,GestureActivity::class.java))
    }

}
