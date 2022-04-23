package com.example.camscanner

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class DocumentRepository(private val nDao: DocumentDao) {
    val allDocument: LiveData<List<Document>> = nDao.getAllDocuments()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(document: Document) {
        nDao.insert(document)
    }
    @WorkerThread
    fun delete(document: Document) {
        nDao.delete(document)
    }
    fun update(document: Document){
        nDao.update(document)
    }
}