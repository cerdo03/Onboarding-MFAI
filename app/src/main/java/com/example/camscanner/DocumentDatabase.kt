package com.example.camscanner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Document::class], version = 1, exportSchema = false)
abstract class DocumentDatabase : RoomDatabase() {

    abstract fun getDocumentDao(): DocumentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DocumentDatabase? = null

        fun getDatabase(context: Context): DocumentDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DocumentDatabase::class.java,
                    "DocumentDatabase"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}