package com.example.madapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText

    private lateinit var signUp: TextView
    private lateinit var newUser: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        userEmail = findViewById(R.id.etEmail)
        userPassword = findViewById(R.id.etPwd)
        newUser = findViewById(R.id.textView)


        findViewById<Button>(R.id.btnLogIn).setOnClickListener {
            val uEmail = userEmail.text.toString()
            val uPassword = userPassword.text.toString()

            if (!uEmail.isEmpty() && !uPassword.isEmpty()) {

                auth.signInWithEmailAndPassword(uEmail, uPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this@MainActivity,catagory::class.java))
                        // Toast.makeText(this, "Home eka set karanna", Toast.LENGTH_LONG).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }else{
                Toast.makeText(
                    baseContext,
                    "TextFeild isEmpty..!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        signUp = findViewById(R.id.tvSignUp)
        signUp.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, signup::class.java))
        })
        newUser.setOnClickListener(View.OnClickListener {
           startActivity(Intent(this@MainActivity,viewproduct::class.java))
           // Toast.makeText(this,"set Activity",Toast.LENGTH_LONG).show()
        })
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this,catagory::class.java))
        }
    }
}