package com.example.project10

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

/**
 * ViewModel class for managing data related to sensor information.
 */
class SensorViewModel : ViewModel() {
    /**
     * Observable fields for the sensor data.
     */
    val title = ObservableField("Sensors Playground")
    val name = ObservableField("Name: Vidya Kethineni")
    val location = ObservableField("Location: ")
    val city = ObservableField("City: ")
    val state = ObservableField("State: ")
    val temperature = ObservableField("Temperature: ")
    val humidity = ObservableField("Humidity: ")
    val airPressure = ObservableField("Air Pressure: ")
}
