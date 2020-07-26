package com.example.womensafetyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class Login : AppCompatActivity() {

    lateinit var loginPhone:EditText
    lateinit var loginButton:Button
    lateinit var loginText: TextView
    lateinit var progressBar: ProgressBar
    lateinit var ref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPhone=findViewById(R.id.login_phoneNo)
        loginButton=findViewById(R.id.login_button)
        loginText=findViewById(R.id.Login_Text)
        progressBar=findViewById(R.id.progressBar)



        loginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {


                val PhoneNo2 : String = loginPhone.text.toString().trim()
               val PhoneNo:String=intent.getStringExtra("Phone_No")



                if(TextUtils.isEmpty(PhoneNo2)){

                    loginPhone.setError("Phone Number is required")
                    return
                }
                else{
                    if(PhoneNo2.equals(PhoneNo)){
//Check it
                       // val ref:DatabaseReference=FirebaseDatabase.getInstance().getReference()
                        //val PhoneData:String= ref.child("Phone_No").orderByChild("Phone Number").toString()
                        //Toast.makeText(this@Login,"Phone From DataBase:"+PhoneData,Toast.LENGTH_LONG).show()

                      //  intent = Intent(applicationContext, Joining::class.java)
                       // startActivity(intent)


                        val intent2:Intent= Intent(this@Login,ContactInformation::class.java)
                        intent2.putExtra("Phone_No",PhoneNo)
                        startActivity(intent2)



                       // Toast.makeText(this@Login,"Value of Phone:"+PhoneNo,Toast.LENGTH_SHORT).show()
                    }
                    else{

                       Toast.makeText(this@Login,"Phone Number: "+PhoneNo2+" doesn't exist in database",Toast.LENGTH_SHORT).show()
                    }
                   // ref=FirebaseDatabase.getInstance().getReference("Phone_No")
                   // val query:Query=FirebaseDatabase.getInstance().getReference("Phone_No").orderByChild("Phone Number").equalTo(PhoneNo2)

                }

                progressBar.visibility = View.VISIBLE
            }
        })

        loginText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                intent = Intent(applicationContext, Register2::class.java)
                startActivity(intent)
                Toast.makeText(this@Login,"Went to Register",Toast.LENGTH_SHORT).show()
            }
        })

    }
}
