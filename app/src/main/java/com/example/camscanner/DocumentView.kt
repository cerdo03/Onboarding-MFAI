package com.example.camscanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DocumentView : AppCompatActivity() {
    lateinit var uri:Uri
    companion object{
        const val Image_Extra="Image_Extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_view)
        val cur = intent.getStringExtra(Image_Extra)
        val full_image_view = findViewById<ImageView>(R.id.imageView)
        if (cur != null) {
            full_image_view.setImageURI(Uri.parse(cur))
            uri = Uri.parse(cur)
        }
    }

    fun shareDoc(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this document.")

        val chooser = Intent.createChooser(intent,"Share this Document using....")
        startActivity(chooser)
    }
}