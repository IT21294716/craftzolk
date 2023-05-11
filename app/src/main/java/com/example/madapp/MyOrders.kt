package com.example.madapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madapp.Adapters.MyOredrsAdapter
import com.example.madapp.Models.OrderModel
import com.example.madapp.databinding.ActivityMyOrdersBinding
import com.example.madapp.databinding.ActivityViewproductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/*private lateinit var itemName : TextView
private lateinit var descrption : TextView
private lateinit var price : TextView
private lateinit var peoImg : ImageView*/


class MyOrders : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<OrderModel>()
    private lateinit var adapter: MyOredrsAdapter


    /*  private  lateinit var database :DatabaseReference
     private  lateinit var storage :FirebaseStorage*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseRef = FirebaseDatabase.getInstance().reference.child("orders")


        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val data = snapshot.getValue(OrderModel::class.java)!!
                    if( data != null){
                        mList.add(data)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyOrders, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = MyOredrsAdapter(this, mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: MyOredrsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {

            }
        })
    }
}
