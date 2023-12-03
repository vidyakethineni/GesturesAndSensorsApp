package com.example.project10

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying a list of gestures in a RecyclerView.
 */
class GestureAdapter(private val itemList:MutableList<String>) :
    RecyclerView.Adapter<GestureAdapter.ViewHolder>() {

    private val TAG = GestureAdapter::class.java.simpleName

    /**
     * Submits a new list of gestures and notifies the adapter of the changes.
     */
    fun submitList(list: List<String>){
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }
    /**
     * ViewHolder for holding views of individual gesture items.
     */
    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val itemTv = itemView.findViewById<TextView>(R.id.logTextView)
            fun bind(item: String?) {
                Log.i(TAG, "Item triggered")
                itemTv.text = item
            }
    }
    /**
     * Creates a new ViewHolder by inflating the item view from the specified parent.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_log,parent,false)
        return ViewHolder(itemView)
    }
    /**
     * Binds the data at the specified position to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return itemList.size
    }
}