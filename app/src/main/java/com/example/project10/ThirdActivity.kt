package com.example.project10

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
/**
 * Activity to demonstrate the use of accelerometer sensor for moving an ImageView.
 */
class ThirdActivity : AppCompatActivity(),SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private  var accelerometer: Sensor?=null
    private lateinit var imageView: ImageView
    var offsetX = 0f
    var offsetY = 0f
    /**
     * Called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        imageView = findViewById(R.id.redBallImageView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }
    /**
     * Called when the sensor values change.
     */
    override fun onSensorChanged(event: SensorEvent?) {
      if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
          val x = event.values[0]
          val y = event.values[1]

          offsetX +=x
          offsetY +=y

          val screenWidth = window.decorView.width
          val screenHeight = window.decorView.height

          val imageWidth = imageView.width
          val imageHeight = imageView.height


          val maxX = (screenWidth-imageWidth).toFloat()-200f
          val maxY = (screenHeight-imageHeight).toFloat()-200f

          Log.i("TAG", "Initial offsetX: $offsetX, offsetY: $offsetY")

          offsetX = when{
              offsetX <0f ->0f
              offsetX >maxX ->0f
              else -> offsetX
          }

          offsetY = when{
              offsetY <0f ->0f
              offsetY >maxY ->0f
              else -> offsetY
          }
          Log.i("TAG", "Final offsetX: $offsetX, offsetY: $offsetY")

          val layoutParams = imageView.layoutParams as FrameLayout.LayoutParams
          layoutParams.leftMargin = offsetX.toInt()
          layoutParams.topMargin = offsetY.toInt()
          imageView.layoutParams = layoutParams
      }
    }
    /**
     * Called when the sensor accuracy changes.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
    /**
     * Called when the activity is resumed.
     */
    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    /**
     * Called when the activity is paused.
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}