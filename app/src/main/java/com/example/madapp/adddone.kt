package com.example.madapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class adddone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adddone)

        val sel = findViewById<Button>(R.id.button8)
        sel.setOnClickListener {
            val Intent = Intent(this,viewproduct::class.java)
            startActivity(Intent)
        }
    }
}