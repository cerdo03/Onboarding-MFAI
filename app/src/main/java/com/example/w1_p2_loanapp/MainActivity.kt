package com.example.w1_p2_loanapp

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList



class MainActivity : AppCompatActivity(),inf {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var curView : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataset = listOfFields().getList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = adapter(this,dataset,this)
        recyclerView.adapter=adapter


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult? ->
            if(result!!.resultCode== RESULT_OK && result!!.data!=null){
                val speechText = result!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<Editable>
                if(curView!=null){
                    curView!!.text=speechText[0]
                }

            }
        }


    }



    override fun onClick(view: EditText) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something")
        try{
            curView=view
            activityResultLauncher.launch(intent)

        }catch (exp:ActivityNotFoundException){
            Toast.makeText(applicationContext,"Not Supported",Toast.LENGTH_SHORT).show()
        }

    }
}