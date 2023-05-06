package com.example.madapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private lateinit var fullName: EditText
private lateinit var userEmail: EditText
private lateinit var mobileNo: EditText
private lateinit var userPassword: EditText
private lateinit var userComPass: EditText

private lateinit var auth: FirebaseAuth

// Access a Cloud Firestore instance from your Activity
val db = Firebase.firestore
lateinit var  user :String

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth


        fullName = findViewById(R.id.editTextTextPersonName)
        userEmail = findViewById(R.id.editTextTextEmailAddress)
        mobileNo = findViewById(R.id.editTextPhone)
        userPassword = findViewById(R.id.editpassword)
        userComPass = findViewById(R.id.editTextconformpassword)

        findViewById<Button>(R.id.buttonnew).setOnClickListener(View.OnClickListener {
            if (!fullName.text.toString().isEmpty() && !userEmail.text.toString()
                    .isEmpty() && !mobileNo.text.toString()
                    .isEmpty() && !userPassword.text.toString().isEmpty()
            ) {
                if (userPassword.text.toString() == userComPass.text.toString()) {
                    auth.createUserWithEmailAndPassword(userEmail.text.toString(), userPassword.text.toString()).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            user = auth.currentUser!!.uid
                            startActivity(Intent(this,MainActivity::class.java))
                            updatedata(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", it.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }

                    }.addOnFailureListener {

                    }
                } else {
                    Toast.makeText(
                        baseContext,
                        "Password is not match .",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "TextField is Empty .",
                    Toast.LENGTH_SHORT,
                ).show()
            }

        })

    }

    private fun updatedata(user: String) {
        if(user != null){
            val user = hashMapOf(
                "userId" to auth.currentUser!!.uid,
                "FUll Name" to fullName.text.toString(),
                "Email" to userEmail.text.toString(),
                "MobileNo" to mobileNo.text.toString(),
            )
            db.collection("user")
                .add(user).addOnSuccessListener { docunentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${docunentReference.id}")
                    Toast.makeText(
                        baseContext,
                        "added with Data.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(
                        baseContext,
                        "Error adding.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
        }

    }
}