package com.example.project10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * Activity to handle gestures and manage associated ViewModel.
 */
class GestureActivity : AppCompatActivity() {
    private val TAG = GestureActivity::class.java.simpleName

    private lateinit var viewModel:GestureViewModel
    /**
     * Initializes the activity and sets the content view.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
        viewModel = ViewModelProvider(this)[GestureViewModel::class.java]
    }


}