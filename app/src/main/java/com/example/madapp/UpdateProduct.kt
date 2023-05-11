package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.databinding.ActivityUpdateProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateProduct : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().reference.child("Items")


        var name = intent.getStringExtra("name").toString()
        var description = intent.getStringExtra("description").toString()
        var price = intent.getStringExtra("price").toString()
        var mobile = intent.getStringExtra("mobile").toString()
        var id = intent.getStringExtra("id").toString()

        binding.etTitle.setText(name)
        binding.etDes.setText(description)
        binding.etPrice.setText(price)
        binding.etMobile.setText(mobile)

        binding.btnUpdate.setOnClickListener {

            name = binding.etTitle.text.toString()
            description = binding.etDes.text.toString()
            price = binding.etPrice.text.toString()
            mobile = binding.etMobile.text.toString()


            val map = HashMap<String,Any>()

            //add data to hashMap
            map["catagary"] = name
            map["description"] = description
            map["price"] = price
            map["mobile"] = mobile


            //update record
            databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, viewproduct::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}