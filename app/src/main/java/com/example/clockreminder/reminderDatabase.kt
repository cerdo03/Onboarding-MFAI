package com.example.clockreminder


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [reminder::class], version = 1, exportSchema = false)
abstract class reminderDatabase:RoomDatabase() {
    abstract fun getReminderDao(): reminderDAO

    companion object{
        @Volatile
        private var INSTANCE: reminderDatabase? = null

        fun getDatabase(context: Context): reminderDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    reminderDatabase::class.java,
                    "reminderDatabase"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}