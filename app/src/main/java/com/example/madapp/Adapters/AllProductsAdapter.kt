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
import com.example.madapp.AddFeedback
import com.example.madapp.Models.Item
import com.example.madapp.PlaceOrder
import com.example.madapp.R
import com.example.madapp.UpdateProduct
import com.google.firebase.database.FirebaseDatabase

class AllProductsAdapter(var context: Context, var mList: List<Item>) :
    RecyclerView.Adapter<AllProductsAdapter.ViewHolder>() {

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
        val btnOrder: Button = itemView.findViewById(R.id.btnOrder)
        val btnRate: Button = itemView.findViewById(R.id.btnRate)


        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item_products, parent, false)



        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mList[position].catagary
        holder.des.text = mList[position].description
        holder.price.text = mList[position].price

       holder.btnOrder.setOnClickListener {
            val intent1 = Intent(context, PlaceOrder::class.java).also {
                it.putExtra("name", mList[position].catagary)
                it.putExtra("id", mList[position].itemId)
                context.startActivity(it)
            }
        }

        holder.btnRate.setOnClickListener {
            val intent1 = Intent(context, AddFeedback::class.java)
            context.startActivity(intent1)
        }
    }
}