package com.example.camscanner

import android.content.Intent
import android.location.GnssAntennaInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity2 : AppCompatActivity() ,Lstner{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(this, this)
        recyclerView.adapter=adapter


        vModel.allDocument.observe(this, Observer {list->
            list?.let{
                adapter.updateList(it)
            }

        })
    }

    override fun dltDocument(document: Document) {
        vModel.deleteDocument(document)
    }

    override fun itemClicked(document: Document) {
        callActivity(Uri.parse(document.uri))
    }
    fun callActivity(img: Uri){

        val intent = Intent(this,DocumentView::class.java)
        intent.putExtra(
            DocumentView.Image_Extra, img.toString()
        )
        startActivity(intent)
    }
}