package com.example.womensafetyapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.Marker
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var PhoneText:TextView
    lateinit var Contact1Text:TextView
    lateinit var Contact2Text:TextView
    lateinit var Contact3Text:TextView
    lateinit var ref: DatabaseReference

   // private var permissions= arrayOf(Manifest.permission.ACESS_FINE_LOCATION,Manifest.permission.ACESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref= FirebaseDatabase.getInstance().getReference("Users")
        PhoneText=findViewById(R.id.textView)
        Contact1Text=findViewById(R.id.textView2)
        Contact2Text=findViewById(R.id.textView3)
        Contact3Text=findViewById(R.id.textView4)
        //dataList= mutableListOf()
        //listView=findViewById(R.id.listView)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone=sharedPreference.getString(Register2.Phone_Numnber,"")
     // val Phone=intent.getStringExtra(Register2.Phone_Numnber)
       Toast.makeText(this@MainActivity,"Intent Successful"+Phone, Toast.LENGTH_LONG).show()


        var getData=object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                var sb= StringBuilder()
                var sb1=StringBuilder()
                var sb2=StringBuilder()
                var sb3=StringBuilder()

                for(i in p0.children){


                      var PhoneNo=i.child("phoneNo").getValue()
                        var Contact1 = i.child("contact1").getValue()
                        var Contact2 = i.child("contact2").getValue()
                        var Contact3 = i.child("contact3").getValue()
                    if(Phone!!.equals(PhoneNo)) {
//                      sb.append("Phone: $PhoneNo \n Contact1: $Contact1\n Contact2: $Contact2 \n Contact3: $Contact3 \n")
                       //   sb.append(" Phone#${i.key}\n Contact1:$Contact1\n Contact2:$Contact2\n Contact3:$Contact3 \n\n\n")
                        sb.append("Phone#${i.key} \n")
                        sb1.append("Contact1#$Contact1 \n")
                        sb2.append("Contact2#$Contact2 \n")
                        sb3.append("Contact3#$Contact3 \n")
                   }
                }
                PhoneText.setText(sb)
                Contact1Text.setText(sb1)
                Contact2Text.setText(sb2)
                Contact3Text.setText(sb3)
            }
        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)
    }
    /*
    @SuppressLint("MissingPermission")
    private fun getLocation(){
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if(hasGps||hasNetwork){
            if(hasGps){
                Log.d("CodeAndroidLocation","hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0F,object:
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                       if(location!=null){
                           locationGps=location
                           tv_result.append("\nNetwork ")
                           tv_result.append("\nLatitude : " + locationGps!!.latitude)
                           tv_result.append("\nLongitude : " + locationGps!!.longitude)
                           Log.d("CodeAndroidLocation","Latitude: "+locationGps!!.latitude)
                           Log.d("CodeAndroidLocation","Longitude: "+locationGps!!.longitude)
                       }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }
                })
             val localGpsLocation=   locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if(localGpsLocation!=null){
                        locationGps=localGpsLocation
                    }

            }
            if(hasNetwork){
                Log.d("CodeAndroidLocation","hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0F,object:
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if(location!=null){
                            locationNetwork=location
                            tv_result.append("\nNetwork ")
                            tv_result.append("\nLatitude : " + locationNetwork!!.latitude)
                            tv_result.append("\nLongitude : " + locationNetwork!!.longitude)
                            Log.d("CodeAndroidLocation","Latitude: "+locationNetwork!!.latitude)
                            Log.d("CodeAndroidLocation","Longitude: "+locationNetwork!!.longitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }
                })
                val localNetworkLocation=   locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(localNetworkLocation!=null){
                    locationNetwork=localNetworkLocation
                }
                if(locationGps!=null && locationNetwork!=null){
                    if(locationGps!!.accuracy>locationNetwork!!.accuracy)
                        tv_result.append("\nNetwork ")
                        tv_result.append("\nLatitude : " + locationNetwork!!.latitude)
                    tv_result.append("\nLongitude : " + locationNetwork!!.longitude)
                        Log.d("CodeAndroidLocation","Latitude: "+locationNetwork!!.latitude)
                    Log.d("CodeAndroidLocation","Longitude: "+locationNetwork!!.longitude)
                }
                else{
                    tv_result.append("\nNetwork ")
                    tv_result.append("\nLatitude : " + locationGps!!.latitude)
                    tv_result.append("\nLongitude : " + locationGps!!.longitude)
                    Log.d("CodeAndroidLocation","Latitude: "+locationGps!!.latitude)
                    Log.d("CodeAndroidLocation","Longitude: "+locationGps!!.longitude)
                }
            }
        }else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }*/
}

