package com.example.womensafetyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_contact_information.*

class ContactInformation : AppCompatActivity() {

    lateinit var phoneText1:EditText
    lateinit var phoneText2:EditText
    lateinit var phoneText3:EditText
    lateinit var submitButton:Button
    lateinit var yourPhoneText: EditText

    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_information)

        phoneText1=findViewById(R.id.phoneNoText1)
        phoneText2=findViewById(R.id.phoneNoText2)
        phoneText3=findViewById(R.id.phoneNoText3)
        submitButton=findViewById(R.id.submitbtn)
        yourPhoneText=findViewById(R.id.YourPhoneText)


        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone1=sharedPreference.getString(Register2.Phone_Numnber,"")


        yourPhoneText.setText(
            Phone1
        )
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
               // val PhoneNo2:String=intent.getStringExtra("Phone_No")
                //YourPhoneText.setText(String.format(
                  //  "Your Phone No:"+PhoneNo2

//                ))
//                Toast.makeText(this@ContactInformation,"PhoneNo"+PhoneNo2, Toast.LENGTH_LONG).show()
                val PhoneNo:String=YourPhoneText.text.toString().trim()
                val Contact1:String=phoneText1.text.toString().trim()
                val Contact2:String=phoneText2.text.toString().trim()
                val Contact3:String=phoneText3.text.toString().trim()

                if (TextUtils.isEmpty(PhoneNo)) {
                    // Toast.makeText(this@Register2, "Phone Number is required", Toast.LENGTH_SHORT) .show()
                    YourPhoneText.setError("Please Confirm Your Phone Number")
                    return
                }
                if (TextUtils.isEmpty(Contact1)) {
                    // Toast.makeText(this@Register2, "Phone Number is required", Toast.LENGTH_SHORT) .show()
                    phoneText1.setError("Atleast 1 Phone Number is required")
                    return
                }
                saveData()

                startActivity(Intent(this@ContactInformation, Joining::class.java))
            }
            })

//        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Phone_No")
//        ref.child("Contacts").child("Contact No#1").setValue(phoneText1.text.toString())
//        ref.child("Contacts").child("Contact No#2").setValue(phoneText2.text.toString())
//        ref.child("Contacts").child("Contact No#3").setValue(phoneText3.text.toString())
    }
    private fun saveData() {
        Toast.makeText(this@ContactInformation,"Enter saveData Function", Toast.LENGTH_SHORT).show()
//        textViewArtist.setText(intent.getStringExtra(MainActivity.ARTIST_NAME))
       //val callerIntent:Intent=intent
        //val packageFromCaller:Bundle=callerIntent.getBundleExtra("MyPackage")
       // val PhoneNo:String= packageFromCaller.getString("PhoneNo")!!
       // val Address:String=intent.getStringExtra("Adress")
        //Toast.makeText(this@ContactInformation,"PhoneNo"+Address, Toast.LENGTH_SHORT).show()
        //val LatLong:String=intent.getStringExtra("Lat&Long")
        //Toast.makeText(this@ContactInformation,"LatLong"+LatLong, Toast.LENGTH_SHORT).show()

        val PhoneNo:String=YourPhoneText.text.toString().trim()
        val Contact1:String=phoneText1.text.toString().trim()
        val Contact2:String=phoneText2.text.toString().trim()
        val Contact3:String=phoneText3.text.toString().trim()


        myRef=FirebaseDatabase.getInstance().getReference()
       // myRef.child("Users").child(PhoneNo).setValue(YourPhoneText)

       val dataId =myRef.push().key
            val data=Data(PhoneNo,dataId.toString(), Contact1, Contact2, Contact3 )
        myRef.child("Users").child(PhoneNo).setValue(data).addOnCompleteListener{
                  Toast.makeText(this@ContactInformation,"Data saved Successfully", Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(this@ContactInformation, Joining::class.java))
                Toast.makeText(this@ContactInformation, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
