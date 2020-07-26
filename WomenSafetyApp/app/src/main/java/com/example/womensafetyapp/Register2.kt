package com.example.womensafetyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class Register2 : AppCompatActivity() {

    companion object{
        val Phone_Numnber="Phone#"
    }

    lateinit var register_Text:TextView
    lateinit var sendCodeBtn:Button
    lateinit var registerBtn:Button
    lateinit var vericodeText:EditText
    lateinit var mPhone_No:EditText
    lateinit var progressBar: ProgressBar
    lateinit var storedVerificationId:String
    lateinit var resendToken:PhoneAuthProvider.ForceResendingToken
    lateinit var fAuth : FirebaseAuth
    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var myRef : DatabaseReference
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        mPhone_No=findViewById(R.id.register_phoneNo)
        vericodeText=findViewById<EditText>(R.id.verificationCode)
        sendCodeBtn=findViewById(R.id.sendVerificationCodebtn)
        registerBtn=findViewById(R.id.RegisterBtn)

        progressBar=findViewById(R.id.progressBar)

        fAuth=FirebaseAuth.getInstance()

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if(sharedPreference.getString(Phone_Numnber," ")!=" "){
            startActivity(Intent(this@Register2, Joining::class.java))
            finish()
        }

        sendCodeBtn.setOnClickListener(object : View.OnClickListener
        { override fun onClick(v: View) {

            val Phone_No: String = mPhone_No.text.toString().trim()

            if (TextUtils.isEmpty(Phone_No)) {
               // Toast.makeText(this@Register2, "Phone Number is required", Toast.LENGTH_SHORT) .show()
                 mPhone_No.setError("Phone Number is required")
                return
            } else {

                sharedPreference.edit().putString(Phone_Numnber,Phone_No).apply()

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    Phone_No,
                    60,
                    TimeUnit.SECONDS,
                    this@Register2,
                    callbacks
                )
            }
        }})
        registerBtn.setOnClickListener(object : View.OnClickListener
        { override fun onClick(v: View) {

            sendVerificationCodebtn.visibility=View.GONE
            mPhone_No.visibility=View.GONE

            val VerificationCode:String=verificationCode.text.toString()

            if(TextUtils.isEmpty(VerificationCode)){
                Toast.makeText(this@Register2,"Please Write Verification Code First",Toast.LENGTH_SHORT).show()
            }
            else{
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, VerificationCode)
                signInWithPhoneAuthCredential(credential)

            }
        }
        })

        callbacks=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)

            }

            override fun onVerificationFailed(p0: FirebaseException?) {

                Toast.makeText(this@Register2,"Invalid Phone Number",Toast.LENGTH_SHORT).show()
                sendVerificationCodebtn.visibility=View.VISIBLE
                mPhone_No.visibility=View.VISIBLE

                registerBtn.visibility=View.GONE
                vericodeText.visibility=View.GONE

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                storedVerificationId = verificationId
                resendToken = token

                Toast.makeText(this@Register2,"Code has been sent",Toast.LENGTH_SHORT).show()
                sendVerificationCodebtn.visibility=View.GONE
                mPhone_No.visibility=View.GONE

                registerBtn.visibility=View.VISIBLE
                vericodeText.visibility=View.VISIBLE
            }
        }
}
    public fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this@Register2,"Congragulations!! You've moved in successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Register2, Joining::class.java))
                    finish()

                }
                else {
                    Toast.makeText(this@Register2,"Error! "+task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

}