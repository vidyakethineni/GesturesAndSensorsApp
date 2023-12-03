package com.example.project10

import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
/**
 * Fragment to handle touch gestures on an ImageView.
 */
class TopFragment : Fragment() {
    private val TAG = TopFragment::class.java.simpleName

    private lateinit var imageView: ImageView
    private lateinit var matrix: Matrix
    private lateinit var startPoint: PointF
    private lateinit var viewModel:GestureViewModel

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }
    /**
     * Initializes the fragment and sets up touch gesture handling.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GestureViewModel::class.java]
        imageView = view.findViewById(R.id.redBallImageView)

        matrix = Matrix()
        startPoint = PointF(0F,0F)
        view.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    Log.i(TAG, "User is moving. Coordinates - x: " + event.getX() + ", y: " + event.getY());
                    val endPoint = PointF(event.x, event.y)
                    val dx = endPoint.x - startPoint.x
                    val dy = endPoint.y - startPoint.y
                    Log.i(TAG, "Movement displacement - dx: " + dx + ", dy: " + dy);
                    if(dx == 0.0f ||dy==0.0f){
                        if (dx != 0.0f || dy != 0.0f) {
                            if(dx == 0.0f){
                                if(dy<0){
                                    viewModel.movementChanged("You swiped up")
                                }else{
                                    viewModel.movementChanged("You swiped down")
                                }
                            }
                            if(dy == 0.0f){
                                if(dx<0){
                                    viewModel.movementChanged("You swiped left")
                                }else{
                                    viewModel.movementChanged("You swiped right")
                                }
                            }
                        }

                    }
                    matrix.postTranslate(dx,dy)

                    imageView.imageMatrix = matrix

                    startPoint = PointF(event.x, event.y)
                }

                MotionEvent.ACTION_DOWN -> {
                    if(startPoint.equals(event.x,event.y)){
                        viewModel.movementChanged("You double tapped")
                        Log.i(TAG, "User double tapped. Coordinates - x: " + event.getX() + ", y: " + event.getY());
                    }
                    Log.i(TAG, "User is moving. Coordinates - x: " + event.getX() + ", y: " + event.getY());
                    startPoint = PointF(event.x, event.y)

                }
            }
            true
        }


    }


}