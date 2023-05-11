package com.example.madapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.madapp.Models.Item
import com.example.madapp.R
import com.example.madapp.UpdateProduct
import com.google.firebase.database.FirebaseDatabase

class MyProductsAdapter(var context: Context, var mList: List<Item>) :
    RecyclerView.Adapter<MyProductsAdapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class ViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val des: TextView = itemView.findViewById(R.id.tvDes)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val btnUpdate: ImageView = itemView.findViewById(R.id.ivEdit)
        val btnDlt: ImageView = itemView.findViewById(R.id.ivDlt)


        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item_my_products, parent, false)



        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mList[position].catagary
        holder.des.text = mList[position].description
        holder.price.text = mList[position].price

        holder.btnUpdate.setOnClickListener {
            val intent1 = Intent(context, UpdateProduct::class.java).also {
                it.putExtra("name", mList[position].catagary)
                it.putExtra("description", mList[position].description)
                it.putExtra("price", mList[position].price)
                it.putExtra("mobile", mList[position].mobile)
                it.putExtra("id", mList[position].itemId)
                context.startActivity(it)
            }
        }

        holder.btnDlt.setOnClickListener {
            val databaseRef = FirebaseDatabase.getInstance().reference.child("Items")
            var id = mList[position].itemId!!
            databaseRef.child(id).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}