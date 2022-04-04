package com.example.clockreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextClock

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val clockText = findViewById<TextClock>(R.id.display_time)

    }

    fun switch_to_reminders(view: View) {
        val intent = Intent(this,reminderActivity::class.java)
        startActivity(intent)
    }
}