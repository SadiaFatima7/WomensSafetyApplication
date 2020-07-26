package com.example.womensafetyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activityy_joining2.*

class Joining : AppCompatActivity() {
    lateinit var mapBtn:CardView
    lateinit var LatLongAddressBtn:CardView
    lateinit var contactsInfoBtn:CardView
    lateinit var mainButton:CardView
    lateinit var sendMessageBtn:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityy_joining2)

        mapBtn=findViewById(R.id.Show_Current_Location)
        LatLongAddressBtn=findViewById(R.id.Show_LatLongAddress)
        contactsInfoBtn=findViewById(R.id.AddContactInfo)
        mainButton=findViewById(R.id.Main_Activity)


       //startActivity(Intent(this@Joining, ScreenOnOffActivity::class.java))

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")

//       logout.setOnClickListener(object : View.OnClickListener {
//           override fun onClick(v: View) {
//               sharedPreference.edit()
//              var editor= sharedPreference.edit().putString(Register2.Phone_Numnber," ")
//               editor.commit()
//               FirebaseAuth.getInstance().signOut()
//               startActivity(Intent(this@Joining, Register2::class.java))
//               Toast.makeText(this@Joining, "Logged out Successfully", Toast.LENGTH_SHORT).show()
//               finish()
//           }
//       })
        mapBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@Joining, MapsActivity::class.java))
            }
        })

       LatLongAddressBtn.setOnClickListener(object : View.OnClickListener {
           override fun onClick(v: View) {
               startActivity(Intent(this@Joining, Location::class.java))
           }
       })

        contactsInfoBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@Joining, ContactInformation::class.java))
            }
        })

        mainButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@Joining, showContactInfo::class.java))
            }
        })

        sendSMSBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@Joining, SendSMS2::class.java))
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi:MenuInflater=menuInflater
        mi.inflate(R.menu.actionbar1,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")

        return when(item.itemId){
           R.id.tblogout->{
               var editor= sharedPreference.edit().putString(Register2.Phone_Numnber," ")
               editor.commit()
//               FirebaseAuth.getInstance().signOut()
               startActivity(Intent(this@Joining, Register2::class.java))
               Toast.makeText(this@Joining, "Logged out Successfully", Toast.LENGTH_SHORT).show()
               finish()
               true
           }
            R.id.backgroundServices->{
                startActivity(Intent(this@Joining, ScreenOnOffActivity::class.java))
                Toast.makeText(this@Joining, "Background Services Started Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
       }

    }


    //  fun logout(view: View) {}

}
