package com.example.clockreminder

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
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



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)


        createNotificationChannel()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification(message:String,time:Long){
        val intent = Intent(applicationContext, Notification::class.java)
        intent.putExtra(titleExtra, "Reminder")
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time,"Reminder", message)
    }
    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        saved_hour=p1
        saved_minute=p2
        val calendar = Calendar.getInstance()
        calendar.set(saved_year,saved_month,saved_day,saved_hour,saved_minute)

        showDialog("$saved_day-$saved_month-$saved_year $saved_hour:$saved_minute", calendar.timeInMillis)

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDialog(time:String, calendar:Long) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog)
        dialog.findViewById<Button>(R.id.submitButton).setOnClickListener{
            var remText= dialog.findViewById<EditText>(R.id.reminder_text).text.toString()
            val rm = reminder(remText,"$saved_day-$saved_month-$saved_year $saved_hour:$saved_minute")
            viewModel.insertReminder(rm)
            scheduleNotification(remText,calendar)
            dialog.dismiss()
        }
        dialog.show()


    }

    override fun ItemDelete(reminder: reminder) {
        viewModel.deleteReminder(reminder)
    }

}