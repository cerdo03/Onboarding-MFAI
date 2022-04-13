package com.example.w1_p2_loanapp

import android.Manifest
import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),inf {
    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var curView : EditText? = null
    var pos:Int = 0

    var ttle:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permission()
        val dataset = listOfFields().getList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = adapter(this,dataset,this)
        recyclerView.adapter=adapter


//        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
//        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//                result: ActivityResult? ->
//            if(result!!.resultCode== RESULT_OK && result!!.data!=null){
//                val speechText = result!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
//                if(curView!=null){
//                    if(curView?.inputType == InputType.TYPE_CLASS_TEXT){
//                        val editor = sharedPreferences.edit()
//                        editor.apply { putString(ttle,speechText[0]) }.apply()
//
//                        curView!!.text= Editable.Factory.getInstance().newEditable(speechText[0])
//                        recyclerView.smoothScrollToPosition(pos+2)
//                    }
//                    else if(curView?.inputType == InputType.TYPE_CLASS_NUMBER || curView?.inputType == InputType.TYPE_CLASS_PHONE){
//                        var result: String = ""
//                        for(i in 0 until speechText[0].length){
//                            if(speechText[0][i]>='0' && speechText[0][i]<='9')
//                                result += speechText[0][i]
//                        }
//                        val editor = sharedPreferences.edit()
//                        editor.apply { putString(ttle,result) }.apply()
//                        curView?.text = Editable.Factory.getInstance().newEditable(result)
//                        recyclerView.smoothScrollToPosition(pos+2)
//
//                    }
//
//                }
//
//            }
//        }


    }



//    override fun onClick(view: EditText,position:Int,title:String) {
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something")
//        try{
//            ttle=title
//            curView=view
//            pos=position
//            activityResultLauncher.launch(intent)
//
//
//
//        }catch (exp:ActivityNotFoundException){
//            Toast.makeText(applicationContext,"Not Supported",Toast.LENGTH_SHORT).show()
//        }
//
//    }
    override fun onClick(view: EditText,position:Int,title:String) {

        ttle=title
        curView=view
        pos=position

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)

         Snackbar.make(view, "Speak now, App is listening", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()

        TranslatorFactory
            .instance
            .with(TranslatorFactory.TRANSLATORS.SPEECH_TO_TEXT,
                object : ConversionCallback {
                    override fun onSuccess(result: String) {
                        if(curView!=null){
                            if(curView?.inputType == InputType.TYPE_CLASS_TEXT){
                                val editor = sharedPreferences.edit()
                                editor.apply { putString(ttle,result) }.apply()

                                curView!!.text= Editable.Factory.getInstance().newEditable(result)
                                recyclerView.smoothScrollToPosition(pos+2)
                            }
                            else if(curView?.inputType == InputType.TYPE_CLASS_NUMBER || curView?.inputType == InputType.TYPE_CLASS_PHONE){
                                var r: String = ""
                                for(i in 0 until result.length){
                                    if(result[i]>='0' && result[i]<='9')
                                        r += result[i]
                                }
                                val editor = sharedPreferences.edit()
                                editor.apply { putString(ttle,r) }.apply()
                                curView?.text = Editable.Factory.getInstance().newEditable(r)
                                recyclerView.smoothScrollToPosition(pos+2)

                            }

                        }
                    }

                    override fun onCompletion() {
                    }

                    override fun onErrorOccurred(errorMessage: String) {

                        Log.d("gg","Speech2Text Error: $errorMessage")
                    }

                }).initialize("Speak Now !!", this)



    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun permission() {

        val permissionsList = ArrayList<String>()

        if (!isPermissionGranted(permissionsList, Manifest.permission.RECORD_AUDIO))

            if (permissionsList.size > 0) {

                requestPermissions(permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
                return
            }
        //add listeners on view

    }


    @TargetApi(Build.VERSION_CODES.M)
    private fun isPermissionGranted(permissionsList: MutableList<String>, permission: String): Boolean {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)

            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED

                for (i in permissions.indices)
                    perms[permissions[i]] = grantResults[i]

                if (perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED) {

                    // All Permissions Granted


                } else {
                    // Permission Denied
                    Toast.makeText(applicationContext, "Some Permissions are Denied Exiting App", Toast.LENGTH_SHORT)
                        .show()

                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}