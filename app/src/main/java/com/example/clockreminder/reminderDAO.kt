package com.example.clockreminder

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface reminderDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rem: reminder)

    @Delete
    fun delete(rem: reminder)

    @Update
    fun update(rem: reminder)

    @Query("Select * from reminder_table order by id ASC")
    fun getAllReminders(): LiveData<List<reminder>>
}