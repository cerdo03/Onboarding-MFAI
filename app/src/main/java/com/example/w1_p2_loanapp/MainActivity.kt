package com.example.w1_p2_loanapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),inf {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var curView : EditText? = null
    var pos:Int = 0

    var ttle:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataset = listOfFields().getList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = adapter(this,dataset,this)
        recyclerView.adapter=adapter


        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult? ->
            if(result!!.resultCode== RESULT_OK && result!!.data!=null){
                val speechText = result!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                if(curView!=null){
                    if(curView?.inputType == InputType.TYPE_CLASS_TEXT){
                        val editor = sharedPreferences.edit()
                        editor.apply { putString(ttle,speechText[0]) }.apply()

                        curView!!.text= Editable.Factory.getInstance().newEditable(speechText[0])
                        recyclerView.smoothScrollToPosition(pos+2)
                    }
                    else if(curView?.inputType == InputType.TYPE_CLASS_NUMBER || curView?.inputType == InputType.TYPE_CLASS_PHONE){
                        var result: String = ""
                        for(i in 0 until speechText[0].length){
                            if(speechText[0][i]>='0' && speechText[0][i]<='9')
                                result += speechText[0][i]
                        }
                        val editor = sharedPreferences.edit()
                        editor.apply { putString(ttle,result) }.apply()
                        curView?.text = Editable.Factory.getInstance().newEditable(result)
                        recyclerView.smoothScrollToPosition(pos+2)

                    }

                }

            }
        }


    }



    override fun onClick(view: EditText,position:Int,title:String) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something")
        try{
            ttle=title
            curView=view
            pos=position
            activityResultLauncher.launch(intent)



        }catch (exp:ActivityNotFoundException){
            Toast.makeText(applicationContext,"Not Supported",Toast.LENGTH_SHORT).show()
        }

    }
}