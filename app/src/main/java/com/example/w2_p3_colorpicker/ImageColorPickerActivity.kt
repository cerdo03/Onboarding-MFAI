package com.example.w2_p3_colorpicker

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri

class ImageColorPickerActivity : AppCompatActivity() {
    companion object{
        const val imageID="imageID"
    }
    lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_color_picker)
        val id = intent.getStringExtra(imageID)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.textView)
        imageView.setImageURI(id?.toUri())
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache(true)

        imageView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                bitmap = imageView.drawingCache

                // get touched pixel
                val pixel = bitmap.getPixel(event.x.toInt(), event.y.toInt())

                // get RGB values from the touched pixel
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // color name in Hexadecimal(#RRGGBB)
                textView.setBackgroundColor(Color.rgb(r, g, b))
            }
            false

        }
    }
}