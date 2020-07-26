package com.example.womensafetyapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.telephony.SmsManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*

class ScreenOnOffBackgroundService : Service() {

    private var screenOnOffReceiver: ScreenOnOffReceiver? = null
    //lateinit var ref:DatabaseReference
     var intent:Intent?=null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

       // ref= FirebaseDatabase.getInstance().getReference("Users")

        // Create an IntentFilter instance.
        val intentFilter = IntentFilter()

        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.SCREEN_ON")
        intentFilter.addAction("android.intent.action.SCREEN_OFF")

        // Set broadcast receiver priority.
        intentFilter.priority = 100

        // Create a network change broadcast receiver.
        screenOnOffReceiver = ScreenOnOffReceiver()

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnOffReceiver, intentFilter)

        Log.d(
            ScreenOnOffReceiver.SCREEN_TOGGLE_TAG,
            "Service onCreate: screenOnOffReceiver is registered."
        )
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
       var restartServiceTask = Intent(getApplicationContext(),this::class.java)
       var restartPendingIntent:PendingIntent= PendingIntent.getService(
           this@ScreenOnOffBackgroundService, 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT
       )
//        restartServiceTask.setPackage(getPackageName())
//        var restartPendingIntent =PendingIntent.getService(this@ScreenOnOffBackgroundService, 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT)
        var myAlarmService:AlarmManager  = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        myAlarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000,
            restartPendingIntent)

        super.onTaskRemoved(rootIntent)
    }
    override fun onDestroy() {
        super.onDestroy()

//         Unregister screenOnOffReceiver when destroy.
        if (screenOnOffReceiver != null) {
            unregisterReceiver(screenOnOffReceiver)
            Log.d(
                ScreenOnOffReceiver.SCREEN_TOGGLE_TAG,
                "Service onDestroy: screenOnOffReceiver is unregistered."
            )
        }
    }
}
