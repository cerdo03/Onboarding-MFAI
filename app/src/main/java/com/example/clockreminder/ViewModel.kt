package com.example.clockreminder

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    val repository: reminderRepository
    val allReminders: LiveData<List<reminder>>

    init{
        val dao  = reminderDatabase.getDatabase(application).getReminderDao()
        repository = reminderRepository(dao)
        allReminders = repository.allReminder
    }
    fun deleteReminder(reminder: reminder) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(reminder)
    }
    fun insertReminder(reminder: reminder) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(reminder)
    }
    fun updateReminder(reminder: reminder) = viewModelScope.launch(Dispatchers.IO){
        repository.update(reminder)
    }
}