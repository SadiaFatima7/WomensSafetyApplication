package com.example.womensafetyapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.ResultReceiver
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_location.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class SendSMS2 : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE_LOCATION_PERMISSION: Int=1

    }

    lateinit var editTextNumber: TextView
    lateinit var editTextNumber2: TextView
    lateinit var editTextNumber3: TextView
    lateinit var editTextMessage: TextView
    lateinit var button: Button
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms2)

        ActivityCompat.requestPermissions(this@SendSMS2, arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS),
            PackageManager.PERMISSION_GRANTED)

        Location.fAuth = FirebaseAuth.getInstance()
        editTextMessage= this.findViewById(R.id.editText)
        editTextNumber=findViewById(R.id.editTextNumber)
           editTextNumber2=findViewById(R.id.editTextNumber2)
         editTextNumber3=findViewById(R.id.editTextNumber3)
        button=findViewById(R.id.button)
        ref= FirebaseDatabase.getInstance().getReference("Users")


        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SendSMS2, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), SendSMS2.REQUEST_CODE_LOCATION_PERMISSION
            )
        } else {
            getCurrentLocation()
        }


        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")

        //Toast.makeText(this@SendSMS2,"Intent Successful"+Phone1, Toast.LENGTH_LONG).show()


        var getData1=object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                var sb = StringBuilder()
                var sb1 = StringBuilder()
                var sb2 = StringBuilder()
                var sb3 = StringBuilder()

                for (i in p0.children) {
                    var PhoneNo = i.child("phoneNo").getValue()
                    var Contact1 = i.child("contact1").getValue()
                    var Contact2 = i.child("contact2").getValue()
                    var Contact3 = i.child("contact3").getValue()
                    if (Phone1!!.equals(PhoneNo)) {
                        sb.append("Phone#${i.key} \n")
                        sb1.append("$Contact1 \n")
                        sb2.append("$Contact2 \n")
                        sb3.append("$Contact3 \n")
                    }
                }
                val sharedPreferenceSCI =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                sharedPreferenceSCI.edit().putString("Contact1",sb1.toString()).apply()
                sharedPreferenceSCI.edit().putString("Contact2",sb2.toString()).apply()
                sharedPreferenceSCI.edit().putString("Contact3",sb3.toString()).apply()
            }
        }

        ref.addValueEventListener(getData1)
        ref.addListenerForSingleValueEvent(getData1)

        val sharedPreferenceSCI =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Contact1=sharedPreferenceSCI.getString("Contact1","")
        val Contact2=sharedPreferenceSCI.getString("Contact2","")
        val Contact3=sharedPreferenceSCI.getString("Contact3","")

        val sharedPreferenceLLL =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Lat=sharedPreferenceLLL.getString("Lat","")
        val Lng=sharedPreferenceLLL.getString("Long","")
    //    Toast.makeText(this@SendSMS2,"Testing:: "+Lat, Toast.LENGTH_LONG).show()


        val sharedPreferenceLA =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Address=sharedPreferenceLA.getString("Address","")
    //    Toast.makeText(this@SendSMS2,"Testing:: "+Address, Toast.LENGTH_LONG).show()

        editTextMessage.setText(String.format(
            "HELP!!!Latitude: %s \nLongitude: %s \n Address: %s",
            Lat,
            Lng,
            Address

        ))
        editTextNumber.setText(
            Contact1
        )

        editTextNumber2.setText(
            Contact2
        )

        editTextNumber3.setText(
            Contact3
        )
    }

    public fun sendSMS(view: View){

        val message:String=editTextMessage.text.toString()
        val number:String=editTextNumber.text.toString()
        val number1:String=editTextNumber2.text.toString()
        val number2:String=editTextNumber3.text.toString()

        val mySmsManager: SmsManager = SmsManager.getDefault()
        mySmsManager.sendTextMessage(number,null,message,null,null)
        mySmsManager.sendTextMessage(number1,null,message,null,null)
        mySmsManager.sendTextMessage(number2,null,message,null,null)
        Toast.makeText(this@SendSMS2,"SMS send successfully", Toast.LENGTH_LONG).show()

    }


    //Location
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResults.size>0)
//        {
//            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                getCurrentLocation()
//            }
//            else{
//                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    private fun getCurrentLocation(){
//        progressBar.visibility= View.VISIBLE
        val locationRequest: LocationRequest = LocationRequest()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=10000
        locationRequest.fastestInterval=3000
        //locationRequest.smallestDisplacement=10f

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,
            object :  LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(this@SendSMS2).removeLocationUpdates(this)
                    if(locationResult!=null && locationResult.locations.size>0){
                        val latestLocationIndex:Int=locationResult.locations.size-1
                        val latitude:Double=locationResult.locations.get(latestLocationIndex).latitude
                        val longitude:Double=locationResult.locations.get(latestLocationIndex).longitude
//                        textLatLong.setText(
//                            String.format(
//                                "Latitude: %s \nLongitude: %s",
//                                latitude,
//                                longitude
//                            )
//                        )
                        val location: android.location.Location =
                            android.location.Location("providerNA")
                        location.latitude=latitude
                        location.longitude=longitude
                        val sharedPreferenceLLL =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                        sharedPreferenceLLL.edit().putString("Lat",latitude.toString()).apply()
                        sharedPreferenceLLL.edit().putString("Long",longitude.toString()).apply()
                        fetchAddressFromLatLong(location)
                    }
//                    else{
//                        progressBar.visibility= View.GONE
//                    }
                }
            }, Looper.getMainLooper())
    }
    fun fetchAddressFromLatLong(location: android.location.Location){

        var errorMessage:String=""

        if(location==null){
            return
        }
        else {
            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            } catch (exception: Exception) {
                errorMessage = exception.message.toString()
            }
            if (addresses == null || addresses.isEmpty()) {
//                 deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
            } else {
                val address: Address = addresses.get(0)
                var addressFragments: ArrayList<String> = arrayListOf()
                var i: Int = 0
                while (i <= address.maxAddressLineIndex) {
                    addressFragments.add(address.getAddressLine(i))
                    i++
                }
//                Location.TextAddress.setText(
//                    String.format(
//                        "Address:"+addressFragments
//
//                    )
//                )

                val sharedPreferenceLA =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                sharedPreferenceLA.edit().putString("Address",addressFragments.toString()).apply()


            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater =menuInflater
        mi.inflate(R.menu.actionbar2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.back->{
                startActivity(Intent(this@SendSMS2, Joining::class.java))
                Toast.makeText(this@SendSMS2, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}


