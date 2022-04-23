package com.example.camscanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.labters.documentscanner.DocumentScannerView
import java.io.ByteArrayOutputStream
import java.io.File


private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File
lateinit var vModel:ViewModel

class MainActivity : AppCompatActivity() {


    val REQUEST_IMAGE_CAPTURE = 1
    val requestCode =121
    private var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                this,
//                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
//                121
//            )
//        }


        vModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ViewModel::class.java]

    }

    fun clickPhoto(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)
        val fileProvider = FileProvider.getUriForFile(this,
            "com.example.camscanner.fileprovider", photoFile)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        } else {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            val uri = Uri.parse( File(photoFile.absolutePath).toString())
            val imageView = findViewById<DocumentScannerView>(R.id.imageView)
            bool = true
            imageView.setImage(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun onSave(view: View) {
            val imageView = findViewById<DocumentScannerView>(R.id.imageView)

            if(!bool){
                Toast.makeText(this,"Scan a document first",Toast.LENGTH_SHORT).show()
            }
            else{
                val image = imageView.getCroppedImage()
                val editText = findViewById<EditText>(R.id.textView)
                val title = editText.text.toString()
                if(title==null || title=="") {
                    Toast.makeText(this,"PLease enter title of the Document",Toast.LENGTH_SHORT).show()
                }else {
                    val count = vModel.allDocument.value?.size
                    var name=1
                    if(count==null || count==0){
                        name=1
                    }else{
                        name=count+2
                    }
                    val uri=getImageUri(this,image,name)
                    vModel.insertDocument(Document(uri.toString(),title))
                    Toast.makeText(this,"Document Saved",Toast.LENGTH_SHORT).show()
                }


            }

    }
    fun savedDocs(view: View) {
        val intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
    }
    fun getImageUri(inContext: Context, inImage: Bitmap,name:Int): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, name.toString(), null)
        return Uri.parse(path)
    }
//    fun saveImageToInternalStorage(bitmap:Bitmap):Uri{
//        // Get the image from drawable resource as drawable object
//
//        // Get the bitmap from drawable object
//
//
//        // Get the context wrapper instance
//        val wrapper = ContextWrapper(applicationContext)
//
//        // Initializing a new file
//        // The bellow line return a directory in internal storage
//        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
//
//
//        // Create a file to save the image
//        file = File(file, "${UUID.randomUUID()}.jpg")
//
//        try {
//            // Get the file output stream
//            val stream: OutputStream = FileOutputStream(file)
//
//            // Compress bitmap
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//
//            // Flush the stream
//            stream.flush()
//
//            // Close stream
//            stream.close()
//        } catch (e: IOException){ // Catch the exception
//            e.printStackTrace()
//        }
//
//        // Return the saved image uri
//        return Uri.parse(file.absolutePath)
//    }
//}
//
//    @TypeConverter
//    fun BitMapToString(bitmap: Bitmap): String {
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b: ByteArray = baos.toByteArray()
//        val temp: String = Base64.encodeToString(b, Base64.DEFAULT)
//        return if (temp == null) {
//            ""
//        } else temp
//    }
//
//    @TypeConverter
//    fun StringToBitMap(encodedString: String?): Bitmap? {
//        return try {
//            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
//            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
//            bitmap
//        } catch (e: Exception) {
//            e.message
//            null
//        }
//    }


}