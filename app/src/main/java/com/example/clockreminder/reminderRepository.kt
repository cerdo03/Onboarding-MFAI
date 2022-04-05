package com.example.clockreminder

import android.provider.ContactsContract
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao

class reminderRepository(private val reminderDAO: reminderDAO) {
    val allReminder:LiveData<List<reminder>> = reminderDAO.getAllReminders()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(rm: reminder) {
        reminderDAO.insert(rm)
    }
    @WorkerThread
    fun delete(rm: reminder) {
        reminderDAO.delete(rm)
    }
    fun update(rm: reminder){
        reminderDAO.update(rm)
    }
}