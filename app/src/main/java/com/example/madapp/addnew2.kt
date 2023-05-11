package com.example.madapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID

private var storageReference = Firebase.storage
private lateinit var productImage: ImageView
private lateinit var uri: Uri
private lateinit var linkImage : Uri
private lateinit var randomuid : String



class addnew2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew2)
    }
}

     /*   storageReference = FirebaseStorage.getInstance()
        productImage = findViewById(R.id.imageView16)

        val adn1 = findViewById<Button>(R.id.button10)
        adn1.setOnClickListener {
            galleryImage.launch("image/*")
        }
        findViewById<Button>(R.id.button7).setOnClickListener(View.OnClickListener {
            //if(linkImage !=null){
                // Create a new user with a first and last name
                val user = hashMapOf(
                    "Image Url" to randomuid,

                )*/
// Add a new document with a generated ID
/*                db.collection("Download_Link Item")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(this, "data save success", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,adddone::class.java))
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            *//*}else{
                Toast.makeText(this, "Not uploaded image", Toast.LENGTH_LONG).show()
            }*//*
        })*/

        findViewById<ImageView>(R.id.imageView5).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@addnew2, addnew::class.java))
        })
        findViewById<Button>(R.id.button6).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,seller1::class.java))
        })
    }

    val galleryImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            productImage.setImageURI(it)
            if (it != null) {
                uri = it
                upload()
            }

        }

    )

    private fun upload() {
         randomuid = UUID.randomUUID().toString()
        storageReference.getReference("Images/ItemImage/"+ randomuid)
            .child("Item Image")
            .putFile(uri)
            .addOnSuccessListener { task ->
                Toast.makeText(this, "Image Upload Success", Toast.LENGTH_LONG).show()
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        linkImage = uri
                    }
            }
    }
}
*/