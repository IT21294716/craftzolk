package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class seller1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller1)

        val vp = findViewById<Button>(R.id.button3)
        vp.setOnClickListener {
            val Intent = Intent(this,viewproduct::class.java)
            startActivity(Intent)
        }

        val add = findViewById<Button>(R.id.button4)
        add.setOnClickListener {
            val Intent = Intent(this,addnew::class.java)
            startActivity(Intent)
        }


    }
}