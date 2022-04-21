package com.example.animation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.abs


class MainActivity : AppCompatActivity(),GestureDetector.OnGestureListener {
    lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private var zoomed = false
    private val swipeVelocityThreshold = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }
    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onShowPress(p0: MotionEvent?) {
        return
    }
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
    override fun onLongPress(p0: MotionEvent?) {
        val image: ImageView = findViewById<ImageView>(R.id.imageView) as ImageView
        if(!zoomed){
            image.scaleX = 2f
            image.scaleY = 2f
            zoomed=true
        }
        else{
            image.scaleX = 1f
            image.scaleY = 1f
            zoomed=false
        }

        return
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            var angle = findViewById<ImageView>(R.id.imageView).rotation
//            if (abs(diffX) >  abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold
                    && abs(diffY)>swipeThreshold && abs(velocityY)>swipeVelocityThreshold) {
                    if ((diffX < 0 && diffY>0) || (diffX<0 && diffY<0)){
                        val image: ImageView = findViewById<ImageView>(R.id.imageView) as ImageView
                                image.rotation=angle+10
                    }
                    else if((diffX>0 && diffY>0) || (diffX>0 && diffY<0)){
//                        Toast.makeText(applicationContext, "Right to Left swipe gesture", Toast.LENGTH_SHORT).show()
//
                        val image: ImageView = findViewById<ImageView>(R.id.imageView) as ImageView
//

                        image.rotation=angle-10




//
                    }
//                }
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }
}