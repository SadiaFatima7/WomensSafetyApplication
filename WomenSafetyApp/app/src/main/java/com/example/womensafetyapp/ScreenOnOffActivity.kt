package com.example.womensafetyapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.womensafetyapp.ScreenOnOffReceiver.Companion.SCREEN_TOGGLE_TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ScreenOnOffActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE_LOCATION_PERMISSION: Int=1
        lateinit var ref:DatabaseReference
    }

    val screenOnOffReceiver: ScreenOnOffReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_on_off)

        ref=FirebaseDatabase.getInstance().reference

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            ),
            PackageManager.PERMISSION_GRANTED
        )

        title = "dev2qa.com - Keep BroadcastReceiver Running After App Exit."

        val backgroundService = Intent(applicationContext, ScreenOnOffBackgroundService::class.java)
        startService(backgroundService)


        Log.d(SCREEN_TOGGLE_TAG, "Activity onCreate")
        startActivity(Intent(this@ScreenOnOffActivity, Joining::class.java))
       // val i:Intent =  Intent(this, ScreenOnOffActivity::class.java)
        //     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //startActivity(i)

//        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")
//        var getData1=object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//            override fun onDataChange(p0: DataSnapshot) {
//                var sb = StringBuilder()
//                var sb1 = StringBuilder()
//                var sb2 = StringBuilder()
//                var sb3 = StringBuilder()
//
//                for (i in p0.children) {
//                    var PhoneNo = i.child("phoneNo").getValue()
//                    var Contact1 = i.child("contact1").getValue()
//                    var Contact2 = i.child("contact2").getValue()
//                    var Contact3 = i.child("contact3").getValue()
//                    if (Phone1!!.equals(PhoneNo)) {
//                        sb.append("Phone#${i.key} \n")
//                        sb1.append("Contact1#$Contact1 \n")
//                        sb2.append("Contact2#$Contact2 \n")
//                        sb3.append("Contact3#$Contact3 \n")
//                    }
//                }
//                val sharedPreferenceSCI =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                sharedPreferenceSCI.edit().putString("Contact1",sb1.toString()).apply()
//                sharedPreferenceSCI.edit().putString("Contact2",sb2.toString()).apply()
//                sharedPreferenceSCI.edit().putString("Contact3",sb3.toString()).apply()
//            }
//        }
//
//        ref.addValueEventListener(getData1)
//        ref.addListenerForSingleValueEvent(getData1)
//
//        val sharedPreferenceSCI =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        val Contact1=sharedPreferenceSCI.getString("Contact1","")
//        val Contact2=sharedPreferenceSCI.getString("Contact2","")
//        val Contact3=sharedPreferenceSCI.getString("Contact3","")
//
//        val sharedPreferenceLLL =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        val Lat=sharedPreferenceLLL.getString("Lat","")
//        val Lng=sharedPreferenceLLL.getString("Long","")
//            Toast.makeText(this@SendSMS2,"Testing:: "+Lat, Toast.LENGTH_LONG).show()


//        val sharedPreferenceLA =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        val Address=sharedPreferenceLA.getString("Address","")

//        val intent:Intent= Intent(this@ScreenOnOffActivity,ScreenOnOffReceiver::class.java)
//        intent.putExtra("Contact1",Contact1.toString())
//        intent.putExtra("Contact2",Contact3.toString())
//        intent.putExtra("Contact3",Contact3.toString())
//        intent.putExtra("Lat",Lat.toString())
//        intent.putExtra("Lng",Lng.toString())
//        intent.putExtra("Address",Address.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(SCREEN_TOGGLE_TAG, "Activity onDestroy")
    }

}