package com.example.womensafetyapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference

class ScreenOnOffReceiver : BroadcastReceiver() {
    companion object {
        val SCREEN_TOGGLE_TAG = "SCREEN_TOGGLE_TAG"
    }
    override fun onReceive(context: Context, intent: Intent) {
        var counter = 0
        val action = intent.action
        if (Intent.ACTION_SCREEN_OFF == action) {
//            counter++
//            if(counter==1){
//             Log.d(SCREEN_TOGGLE_TAG, ""+counter)
            val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")


        val sharedPreferenceSCI =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Contact1=sharedPreferenceSCI.getString("Contact1","")
        val Contact2=sharedPreferenceSCI.getString("Contact2","")
        val Contact3=sharedPreferenceSCI.getString("Contact3","")

        val sharedPreferenceLLL =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Lat=sharedPreferenceLLL.getString("Lat","")
        val Lng=sharedPreferenceLLL.getString("Long","")
        //    Toast.makeText(this@SendSMS2,"Testing:: "+Lat, Toast.LENGTH_LONG).show()
//

        val sharedPreferenceLA =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Address=sharedPreferenceLA.getString("Address","")

        val number=Contact1.toString()
        val number1=Contact2.toString()
        val number2=Contact3.toString()
        val message="Latitude:"+Lat.toString()+"\n"+"Longitude: "+Lng.toString()+"\n"+"Address:"+Address.toString()

            val mySmsManager: SmsManager = SmsManager.getDefault()
            mySmsManager.sendTextMessage(number,null,message,null,null)
            mySmsManager.sendTextMessage(number1,null,message,null,null)
            mySmsManager.sendTextMessage(number2,null,message,null,null)

            //Toast.makeText(this@ScreenOnOffReceiver,"Intent Successful"+Phone1, Toast.LENGTH_LONG).show()
           //Log.d(ScreenOnOffReceiver.SCREEN_TOGGLE_TAG, "SMS send successfully.")
            Log.d(SCREEN_TOGGLE_TAG, "Screen is turn off.")
            Log.d(SCREEN_TOGGLE_TAG, "SMS send successfully.")
//                            counter=0;
//                        }
        }
        else if (Intent.ACTION_SCREEN_ON == action) {
            Log.d(SCREEN_TOGGLE_TAG, "Screen is turn on.")
        }
        context.startService(Intent(context, ScreenOnOffBackgroundService::class.java))

    }

}