package com.example.camscanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    val repository: DocumentRepository
    val allDocument: LiveData<List<Document>>

    init{
        val dao  = DocumentDatabase.getDatabase(application).getDocumentDao()
        repository = DocumentRepository(dao)
        allDocument = repository.allDocument
    }
    fun deleteDocument(document: Document) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(document)
    }
    fun insertDocument(document: Document) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(document)
    }
    fun updateDocument(document: Document) = viewModelScope.launch(Dispatchers.IO){
        repository.update(document)
    }

}