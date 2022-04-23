package com.example.camscanner

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="document_table")
data class Document(
    @ColumnInfo(name="uri") val uri:String,
    @ColumnInfo(name = "title") val title:String) :Serializable{
    @PrimaryKey(autoGenerate = true) var id =0
}