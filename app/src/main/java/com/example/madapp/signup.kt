package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.Models.Item
import com.example.madapp.Models.UserModel
import com.example.madapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.btnSignUp.setOnClickListener {

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val mobile = binding.etMobile.text.toString()
            val password = binding.etPwd.text.toString()
            val confirmPwd = binding.etConfPwd.text.toString()

            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()) {
                if (name.isEmpty()) {
                    binding.etName.error = "Enter your name"
                }
                if (email.isEmpty()) {
                    binding.etEmail.error = "Enter your email"
                }
                if (mobile.isEmpty()) {
                    binding.etMobile.error = "Enter your phone"
                }
                if (password.isEmpty()) {
                    binding.etPwd.error = "Enter your password"
                }
                if (confirmPwd.isEmpty()) {
                    binding.etConfPwd.error = "Type your password again"
                }
            }
            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())) {
                binding.etEmail.error = "Enter a valid email address"
            }

            //validate phone number
            else if (mobile.length != 10) {
                binding.etMobile.error = "Enter a valid phone number"
            }

            //validate passwords
            else if (password.length < 7) {
                binding.etPwd.error = "Password must be at least 7 characters."
            }
            else if (confirmPwd != password) {
                binding.etConfPwd.error = "Passwords do not match. Please try again."
            }
            else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        //store user details in the database
                        val databaseRef =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val user: UserModel = UserModel(name,email,mobile)
                        databaseRef.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                //redirect user to login activity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                    } else {
                        Toast.makeText(this,  it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }



    }
}