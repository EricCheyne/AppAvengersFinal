package com.appavengersfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appavengersfinal.R
import kotlin.math.abs


// We are going to need three functions in order to make this work
// 1. onCreateViewHolder()
// 2. onBIndViewHolder()
// 3. getItemCount()

class Transactions_Adapter(private var mList: List<Data_Transaction>) : RecyclerView.Adapter<Transactions_Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val Data_Transaction = mList[position]

        // set the image to th imageview from out itemHolder class
        val context: Context = holder.amount.context

        holder.item.text = Data_Transaction.item

        if (Data_Transaction.amount >= 0){
            holder.amount.text = "+ $%.2f".format(Data_Transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.blue))
        }
        else {
            holder.amount.text = "-$%.2f".format(abs(Data_Transaction.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item : TextView = itemView.findViewById(R.id.item)
        val amount : TextView = itemView.findViewById(R.id.amount)
    }


}