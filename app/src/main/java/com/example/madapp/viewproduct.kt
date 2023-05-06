package com.example.madapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

private lateinit var itemName : TextView
private lateinit var descrption : TextView
private lateinit var price : TextView
private lateinit var peoImg : ImageView


class viewproduct : AppCompatActivity() {
 val storageRef = Firebase.storage.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewproduct)




        itemName = findViewById(R.id.textView7)
        descrption = findViewById(R.id.textView8)
        price = findViewById(R.id.textView9)
        peoImg = findViewById(R.id.imageView33)

        var arrayList = ArrayList<String>()

        db.collection("Add Item").get().addOnSuccessListener { result ->
            for (document in result){
                arrayList.add(document.data.get("Item Name").toString())
                arrayList.add(document.data.get("Description").toString())
                arrayList.add(document.data.get("Price").toString())
                arrayList.add(document.data.get("Item Image").toString())



                itemName.setText(arrayList.get(0))
                descrption.setText(arrayList.get(1))
                price.setText("LKR :"+arrayList.get(2))
                val imageurl = arrayList.get(3)

                storageRef.("").downloadUrl.addOnSuccessListener {
                       Glide.with(this@viewproduct)
                           .load(it)
                           .into(peoImg)
                        // Got the download URL for 'users/me/profile.png'
                }.addOnFailureListener {
                    // Handle an
                }




            }
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        }
    }
