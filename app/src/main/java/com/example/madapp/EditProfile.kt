package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.databinding.ActivityEditProfileBinding
import com.example.madapp.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")


        var name = intent.getStringExtra("name").toString()
        var mobile = intent.getStringExtra("mobile").toString()
        var email = intent.getStringExtra("email").toString()

        binding.etName.setText(name)
        binding.etEmail.setText(email)
        binding.etMobile.setText(mobile)

        binding.button.setOnClickListener {
            var name = binding.etName.text.toString()
            var email = binding.etName.text.toString()
            var mobile = binding.etMobile.text.toString()

            if( name.isEmpty() || email.isEmpty() || mobile.isEmpty()){
                Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show()
            } else {
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["name"] = name
                map["email"] = email
                map["mobile"] = mobile

                //update database from hashMap
                databaseRef.child(uid).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, UserProfile::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }



    }
}