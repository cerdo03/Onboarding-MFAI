package com.example.camscanner

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(document: Document)

    @Delete
    fun delete(document: Document)

    @Update
    fun update(document: Document)

    @Query("Select * from document_table order by id ASC")
    fun getAllDocuments(): LiveData<List<Document>>
}