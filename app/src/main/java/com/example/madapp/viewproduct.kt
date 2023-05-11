package com.example.madapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madapp.Adapters.MyProductsAdapter
import com.example.madapp.databinding.ActivityViewproductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/*private lateinit var itemName : TextView
private lateinit var descrption : TextView
private lateinit var price : TextView
private lateinit var peoImg : ImageView*/


class viewproduct : AppCompatActivity() {

    private lateinit var binding: ActivityViewproductBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<com.example.madapp.Models.Item>()
    private lateinit var adapter: MyProductsAdapter


   /*  private  lateinit var database :DatabaseReference
    private  lateinit var storage :FirebaseStorage*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewproductBinding.inflate(layoutInflater)
       setContentView(binding.root)

       auth = FirebaseAuth.getInstance()
       uid = auth.currentUser?.uid.toString()

       databaseRef = FirebaseDatabase.getInstance().reference.child("Items")
       val query = databaseRef.orderByChild("sellerId").equalTo(uid)

       var recyclerView = binding.recyclerView
       recyclerView.setHasFixedSize(true)
       recyclerView.layoutManager = LinearLayoutManager(this);

       query.addValueEventListener(object : ValueEventListener {
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
               Toast.makeText(this@viewproduct, error.message, Toast.LENGTH_SHORT).show()
           }
       })

       adapter = MyProductsAdapter(this, mList)
       recyclerView.adapter = adapter


       //Setting onclick on recyclerView each item
       adapter.setOnItemClickListner(object: MyProductsAdapter.onItemClickListner {
           override fun onItemClick(position: Int) {

           }
       })
   }
}


        /*storage = Firebase.storage
        val storageRef = storage.reference



        itemName = findViewById(R.id.textView7)
        descrption = findViewById(R.id.textView8)
        price = findViewById(R.id.textView9)
        peoImg = findViewById(R.id.imageView33)

        var arrayList = ArrayList<String>()
        val catagory = itemName.text.toString()
        database = FirebaseDatabase.getInstance().getReference("Items")
        database.get().addOnSuccessListener {
            if(it.exists()){
                val fName = it.child("catagary").toString()
                val dName = it.child("description").toString()
                val pNAme = it.child("price").toString()
                val image = it.child("imageUrl").toString()
                Toast.makeText(this,"Data Read Successfully",Toast.LENGTH_LONG).show()

                itemName.text = fName.toString()
                descrption.text = dName.toString()
                price.text = pNAme.toString()

                storageRef.child(image).downloadUrl.addOnSuccessListener {
                   Glide.with(this)
                       .load(it)
                       .into(peoImg)
                }


            }
        }
*/
       /* db.collection("Add Item").get().addOnSuccessListener { result ->
            for (document in result){
                arrayList.add(document.data.get("Item Name").toString())
                arrayList.add(document.data.get("Description").toString())
                arrayList.add(document.data.get("Price").toString())
                arrayList.add(document.data.get("Item Image").toString())



                itemName.setText(arrayList.get(0))
                descrption.setText(arrayList.get(1))
                price.setText("LKR :"+arrayList.get(2))
                val imageurl = arrayList.get(

                /*storageRef.("name").downloadUrl.addOnSuccessListener {
                       Glide.with(this@viewproduct)
                           .load(it)
                           .into(peoImg)
                        // Got the download URL for 'users/me/profile.png'
                }.addOnFailureListener {
                    // Handle an
                }*/




            }
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/


