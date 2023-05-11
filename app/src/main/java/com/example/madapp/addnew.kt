package com.example.madapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.madapp.Models.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID


class addnew : AppCompatActivity() {

    private  var storageReference = Firebase.storage
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextTextPersonName5: EditText
    private lateinit var editTextTextPersonName11: EditText
    private lateinit var editTextTextPersonName12: EditText
    private lateinit var editTextTextPersonName8: EditText
    private lateinit var userImage: ImageView
    private lateinit var uri : Uri
    private lateinit var linkImage : Uri
    private lateinit var randomUUID : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew)

        storageReference = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()



        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5)
        editTextTextPersonName11 = findViewById(R.id.editTextTextPersonName11)
        editTextTextPersonName12 = findViewById(R.id.editTextTextPersonName12)
        editTextTextPersonName8 = findViewById(R.id.editTextTextPersonName8)

        userImage = findViewById(R.id.imageView15)

        findViewById<Button>(R.id.button9).setOnClickListener(View.OnClickListener {
           galleryImage.launch("image/*")
        })

        findViewById<Button>(R.id.button5).setOnClickListener(View.OnClickListener {
            val itemName = editTextTextPersonName5.text.toString()
            val description = editTextTextPersonName11.text.toString()
            val price = editTextTextPersonName12.text.toString()
            val mobileNo = editTextTextPersonName8.text.toString()

            val database = FirebaseDatabase.getInstance().getReference("Items")
            var id = database.push().key!!
            val item = Item(id,uid,itemName,description,price,mobileNo)
            database.child(id).setValue(item).addOnSuccessListener {
                editTextTextPersonName5.text.clear()
                editTextTextPersonName11.text.clear()
                editTextTextPersonName12.text.clear()
                editTextTextPersonName8.text.clear()
                Toast.makeText(this,"Item Save Success",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,adddone::class.java))
            }.addOnFailureListener {
                Toast.makeText(this,"Item Not save",Toast.LENGTH_LONG).show()
            }

            /*if(!itemName.isEmpty() && !description.isEmpty() && !price.isEmpty() && !mobileNo.isEmpty()){
                val itemSave = hashMapOf(
                    "Item Name" to itemName,
                    "Description" to description,
                    "Price" to price,
                    "Mobile No" to mobileNo,
                    "Item Image" to randomUUID
                )
                db.collection("Add Item")
                    .add(itemSave)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                       Toast.makeText(this,"Item Save Success",Toast.LENGTH_LONG).show()
                }.addOnFailureListener { e ->
                        Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()

                }
            }else{
                Toast.makeText(this,"Item Save Success",Toast.LENGTH_LONG).show()
            }*/

        })
    }

  val galleryImage = registerForActivityResult(
      ActivityResultContracts.GetContent(),
      ActivityResultCallback {
          userImage.setImageURI(it)
          if (it != null) {
              uri = it
              upload()
          }

      }
  )

    private fun upload() {
        randomUUID = UUID.randomUUID().toString()
        storageReference.getReference("ProductImages")
            .child(randomUUID)
            .putFile(uri)
            .addOnSuccessListener {task ->
                Toast.makeText(this,"Image Upload Success",Toast.LENGTH_LONG).show()
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        linkImage = uri
                    }
        }
    }
}