package com.example.womensafetyapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.ResultReceiver
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_joining.*
import kotlinx.android.synthetic.main.activity_location.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class Location : AppCompatActivity() {

        companion object{
            //  lateinit var textLatLong:TextView
            lateinit var TextAddress: TextView
            lateinit var fAuth : FirebaseAuth
            lateinit var mFirebaseDatabase: FirebaseDatabase
            lateinit var myRef : DatabaseReference
            //lateinit var progressBar: ProgressBar
          //  lateinit var resultReceiver: ResultReceiver

          private const val REQUEST_CODE_LOCATION_PERMISSION: Int=1
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_location)

//        resultReceiver=AddressResultReceiver(Handler() )

//            logout.setOnClickListener(object : View.OnClickListener {
//                override fun onClick(v: View) {
//                    startActivity(Intent(this@Location, Joining::class.java))
//                    Toast.makeText(this@Location, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
//                }
//            })


            fAuth= FirebaseAuth.getInstance()

            val textLatLong: TextView =findViewById(R.id.textLatLong)
            val progressBar: ProgressBar =findViewById(R.id.progressBar)
            TextAddress=findViewById(R.id.textAddress)

            findViewById<ImageButton>(R.id.buttonGetCurrentLocation).setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (ContextCompat.checkSelfPermission(
                            applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@Location, arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ), REQUEST_CODE_LOCATION_PERMISSION
                        )
                    } else {
                        getCurrentLocation()
                    }
                    //startActivity(Intent(this@Location, com.example.womensafetyapp.Joining::class.java))
                }})


        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResults.size>0)
            {
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation()
                }
                else{
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        private fun getCurrentLocation(){
            progressBar.visibility= View.VISIBLE
            val locationRequest: LocationRequest = LocationRequest()
            locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval=10000
            locationRequest.fastestInterval=3000
            //locationRequest.smallestDisplacement=10f

            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,
                object :  LocationCallback(){
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@Location).removeLocationUpdates(this)
                        if(locationResult!=null && locationResult.locations.size>0){
                            val latestLocationIndex:Int=locationResult.locations.size-1
                            val latitude:Double=locationResult.locations.get(latestLocationIndex).latitude
                            val longitude:Double=locationResult.locations.get(latestLocationIndex).longitude
                            textLatLong.setText(
                                String.format(
                                    "Latitude: %s \nLongitude: %s",
                                    latitude,
                                    longitude
                                )
                            )
                            val location: Location = Location("providerNA")
                            location.latitude=latitude
                            location.longitude=longitude

//                            val sharedPreferenceLLL =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                            sharedPreferenceLLL.edit().putString("Lat",latitude.toString()).apply()
//                            sharedPreferenceLLL.edit().putString("Long",longitude.toString()).apply()
                            fetchAddressFromLatLong(location)
                        }else{
                            progressBar.visibility= View.GONE
                        }
                    }
                }, Looper.getMainLooper())
        }
        fun fetchAddressFromLatLong(location: Location){

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
                    TextAddress.setText(
                        String.format(
                            "Address:"+addressFragments

                        )
                    )

//                    val sharedPreferenceLA =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                    sharedPreferenceLA.edit().putString("Address",addressFragments.toString()).apply()
                    progressBar.visibility= View.GONE

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
                startActivity(Intent(this@Location, Joining::class.java))
                Toast.makeText(this@Location, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    }
