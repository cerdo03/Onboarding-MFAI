package com.example.clockreminder

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_reminder.*
import java.util.*


class reminderActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,INotesRVAdapter {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var saved_day = 0
    var saved_month = 0
    var saved_year = 0
    var saved_hour = 0
    var saved_minute = 0
    lateinit var viewModel: ViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = recyclerViewAdapter(this,this)
        recyclerView.adapter=adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ViewModel::class.java]

        viewModel.allReminders.observe(this, {list->
            list?.let{
                adapter.updateList(it)
            }

        })


    }

    fun addReminder(view: View) {
        pickDate()
    }
    fun pickDate() {
        getDateAndTime()
        DatePickerDialog(this,this,year,month,day).show()
    }

    fun getDateAndTime(){
        val cal=Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }


    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        saved_day=p3
        saved_month=p2
        saved_year=p1
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        saved_hour=p1
        saved_minute=p2
        showDialog("$saved_day-$saved_month-$saved_year $saved_hour:$saved_minute")

    }
    private fun showDialog(time:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog)
        dialog.findViewById<Button>(R.id.submitButton).setOnClickListener{
            var remText= dialog.findViewById<EditText>(R.id.reminder_text).text.toString()
            val rm = reminder(remText,"$saved_day-$saved_month-$saved_year $saved_hour:$saved_minute")
            viewModel.insertReminder(rm)
            dialog.dismiss()
        }
        dialog.show()


    }

    override fun ItemDelete(reminder: reminder) {
        viewModel.deleteReminder(reminder)
    }

}