package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.databinding.ActivityUpdateOrderBinding
import com.example.madapp.databinding.ActivityUpdateProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateOrder : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateOrderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().reference.child("orders")


        var name = intent.getStringExtra("name").toString()
        var address = intent.getStringExtra("address").toString()
        var qty = intent.getStringExtra("qty").toString()
        var mobile = intent.getStringExtra("mobile").toString()
        var orderId = intent.getStringExtra("orderId").toString()

        binding.tvName.text = name
        binding.etAddress.setText(address)
        binding.etQty.setText(qty)
        binding.etMobile.setText(mobile)

        binding.btnUpdate.setOnClickListener {

            address = binding.etAddress.text.toString()
            qty = binding.etQty.text.toString()
            mobile = binding.etMobile.text.toString()


            val map = HashMap<String,Any>()

            //add data to hashMap
            map["address"] = address
            map["qty"] = qty
            map["mobile"] = mobile


            //update record
            databaseRef.child(orderId).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, MyOrders::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}