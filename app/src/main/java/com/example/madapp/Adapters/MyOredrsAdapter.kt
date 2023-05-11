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
import com.example.madapp.Models.OrderModel
import com.example.madapp.R
import com.example.madapp.UpdateOrder
import com.example.madapp.UpdateProduct
import com.google.firebase.database.FirebaseDatabase

class MyOredrsAdapter(var context: Context, var mList: List<OrderModel>) :
    RecyclerView.Adapter<MyOredrsAdapter.ViewHolder>() {

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
        val qty: TextView = itemView.findViewById(R.id.tvQty)
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
            .inflate(R.layout.each_item_orders, parent, false)



        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mList[position].pname
        holder.qty.text = mList[position].qty

        holder.btnUpdate.setOnClickListener {
            val intent1 = Intent(context, UpdateOrder::class.java).also {
                it.putExtra("name", mList[position].pname)
                it.putExtra("qty", mList[position].qty)
                it.putExtra("mobile", mList[position].mobile)
                it.putExtra("orderId", mList[position].orderId)
                it.putExtra("address", mList[position].address)
                context.startActivity(it)
            }
        }

        holder.btnDlt.setOnClickListener {
            val databaseRef = FirebaseDatabase.getInstance().reference.child("orders")
            var id = mList[position].orderId!!
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