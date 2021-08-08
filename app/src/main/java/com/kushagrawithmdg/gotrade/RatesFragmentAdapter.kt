package com.kushagrawithmdg.gotrade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.Models.Rate
import com.kushagrawithmdg.gotrade.ui.Rates.RatesFragment

class RatesFragmentAdapter(): RecyclerView.Adapter<OnRateViewHolder>() {

    private val items: ArrayList<Rate> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnRateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment,parent,false)

        return OnRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnRateViewHolder, position: Int) {
        val currentItem =items[position]
        holder.titleview.text = currentItem.asset_id_quote
        holder.rateview.text=currentItem.rate.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updaterate(updatedlist:ArrayList<Rate>){
        items.clear()
        items.addAll(updatedlist)
        notifyDataSetChanged()
    }

}



class OnRateViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview){
    val titleview : TextView = itemview.findViewById(R.id.View)
    val rateview : TextView = itemview.findViewById(R.id.View2)
}

