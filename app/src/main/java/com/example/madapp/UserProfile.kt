package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.Models.UserModel
import com.example.madapp.databinding.ActivityUpdateProductBinding
import com.example.madapp.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")

        databaseRef.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(UserModel::class.java)!!

                binding.tvName.text = user.name
                binding.tvMobile.text = user.mobile
                binding.tvEmail.text = user.email

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserProfile, "Failed to fetch user", Toast.LENGTH_SHORT).show()

            }
        })



        binding.btnMyOrders.setOnClickListener {
            startActivity(Intent(this,MyOrders::class.java))
        }

        binding.btnEditProfile.setOnClickListener {
            intent = Intent(applicationContext, EditProfile::class.java).also {
                it.putExtra("name", user.name)
                it.putExtra("mobile", user.mobile)
                it.putExtra("email", user.email)
                startActivity(it)
            }
        }

        /*binding.ivDltProfile.setOnClickListener {
            databaseRef.child(uid).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }*/
        binding.btnMyRatings.setOnClickListener {
            startActivity(Intent(this,ViewAllFeedbacks::class.java))
        }


        binding.btnLogout.setOnClickListener {
            binding.btnLogout.setOnClickListener {
                Firebase.auth.signOut()

                //redirect user to login page
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)

                //toast message
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
            }
        }









    }
}