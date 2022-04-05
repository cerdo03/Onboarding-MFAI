package com.example.clockreminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "reminder_table")
data class reminder(@ColumnInfo(name = "text")var text:String,@ColumnInfo(name = "time")var time:String):Serializable {
    @PrimaryKey(autoGenerate = true) var id =0
}