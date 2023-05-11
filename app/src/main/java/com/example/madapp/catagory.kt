package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madapp.Adapters.AllProductsAdapter
import com.example.madapp.databinding.ActivityCatagoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class catagory : AppCompatActivity() {

    private lateinit var binding: ActivityCatagoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<com.example.madapp.Models.Item>()
    private lateinit var adapter: AllProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatagoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseRef = FirebaseDatabase.getInstance().reference.child("Items")

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val data = snapshot.getValue(com.example.madapp.Models.Item::class.java)!!
                    if( data != null){
                        mList.add(data)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@catagory, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = AllProductsAdapter(this, mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: AllProductsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {

            }
        })

        /*binding.imageView6.setOnClickListener {
            startActivity(Intent(this,catagory::class.java))
        }*/
        binding.imageView5.setOnClickListener {
            startActivity(Intent(this,seller1::class.java))
        }
        binding.imageView9.setOnClickListener {
            startActivity(Intent(this,UserProfile::class.java))
        }
    }
}