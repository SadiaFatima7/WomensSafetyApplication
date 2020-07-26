package com.example.womensafetyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_location.*
import java.lang.StringBuilder

class showContactInfo : AppCompatActivity() {

  // lateinit var listView:ListView
    //var myArrayList: ArrayList<String> = ArrayList()
    //lateinit var myArrayAdapter:ArrayAdapter<String>
    lateinit var textView:TextView
    lateinit var PhoneText:TextView
    lateinit var Contact1Text:TextView
    lateinit var Contact2Text:TextView
    lateinit var Contact3Text:TextView
    lateinit var ref: DatabaseReference
    lateinit var progressBar: ProgressBar
   // lateinit var dataList:MutableList<Data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact_info)

        ref= FirebaseDatabase.getInstance().getReference("Users")
        PhoneText=findViewById(R.id.textView)
        Contact1Text=findViewById(R.id.textView2)
        Contact2Text=findViewById(R.id.textView3)
        Contact3Text=findViewById(R.id.textView4)
      //  progressBar=findViewById(R.id.progressBar)
        //dataList= mutableListOf()
        //listView=findViewById(R.id.listView)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val Phone=sharedPreference.getString(Register2.Phone_Numnber,"")
        // val Phone=intent.getStringExtra(Register2.Phone_Numnber)
       // Toast.makeText(this@showContactInfo,"Intent Successful"+Phone, Toast.LENGTH_LONG).show()


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
                        sb.append("${i.key} \n")
                        sb1.append("$Contact1 ")
                        sb2.append("$Contact2 ")
                        sb3.append("$Contact3 ")
                    }
                }
                PhoneText.setText(sb)
                Contact1Text.setText(sb1)
                Contact2Text.setText(sb2)
                Contact3Text.setText(sb3)

//                val sharedPreferenceSCI =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                sharedPreferenceSCI.edit().putString("Contact1",Contact1Text.text.toString()).apply()
//                sharedPreferenceSCI.edit().putString("Contact2",Contact2Text.text.toString()).apply()
//                sharedPreferenceSCI.edit().putString("Contact3",Contact3Text.text.toString()).apply()
            }
        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)


//        logout.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View) {
//                startActivity(Intent(this@showContactInfo, Joining::class.java))
//                Toast.makeText(this@showContactInfo, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
//            }
//        })


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater =menuInflater
        mi.inflate(R.menu.actionbar2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.back->{
                startActivity(Intent(this@showContactInfo, Joining::class.java))
                Toast.makeText(this@showContactInfo, "Moved to JOINING Successfully", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
