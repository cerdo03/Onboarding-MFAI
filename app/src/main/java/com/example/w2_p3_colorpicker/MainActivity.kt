package com.example.w2_p3_colorpicker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.github.antonpopoff.colorwheel.ColorWheel
import com.github.antonpopoff.colorwheel.gradientseekbar.GradientSeekBar

var curColor:Int = 0
class MainActivity : AppCompatActivity() {
    var selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)
        val textView = findViewById<TextView>(R.id.textView)
        val gradientSeekBar = findViewById<GradientSeekBar>(R.id.gradient)
        val endColor = Color.argb(0xff, 0xff, 0xff, 0xff)

        colorWheel.colorChangeListener = {

            rgb: Int ->
            val prev = colorWheel.rgb
            curColor=prev
            gradientSeekBar.startColor = prev
            gradientSeekBar.endColor =endColor


        }
        gradientSeekBar.colorChangeListener={
            offset:Float,
            argb:Int -> textView.setBackgroundColor(argb)
        }
        selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            val intent = Intent(this,ImageColorPickerActivity::class.java)
            if(uri!=null){
                intent.putExtra(ImageColorPickerActivity.imageID,uri.toString())
//            Log.d("URI of image",uri.toString())
                startActivity(intent)
            }

        }

    }

    fun pickImage(view: View) {

        selectImageIntent.launch("image/*")
    }
}