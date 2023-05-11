package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapp.Adapters.AllProductsAdapter
import com.example.madapp.Models.OrderModel
import com.example.madapp.databinding.ActivityPlaceOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PlaceOrder : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceOrderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name").toString()
        val id = intent.getStringExtra("id").toString()

        binding.tvName.text = name

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("orders")

        binding.btnPlace.setOnClickListener {
            var address = binding.etAddress.text.toString()
            var qty = binding.etQty.text.toString()
            var mobile = binding.etMobile.text.toString()

            if( address.isEmpty() || qty.isEmpty() || mobile.isEmpty()){
                Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show()
            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a object
                val data = OrderModel( id,name,address,qty, mobile)
                databaseRef.child(id).setValue(data).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, catagory::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }




    }
}