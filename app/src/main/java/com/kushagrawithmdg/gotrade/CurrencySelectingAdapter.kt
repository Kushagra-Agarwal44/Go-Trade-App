package com.kushagrawithmdg.gotrade

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Models.User


class CurrencySelectingAdapter( private val listener: ONLikeClicked, private val user: User = User()): RecyclerView.Adapter<CurrencySelectingAdapter.CurrencyViewHolder>() {
    private val items: ArrayList<Asset> = ArrayList()


    class CurrencyViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview){
        val titleview : TextView = itemview.findViewById(R.id.postTitle)
val currencyview: TextView = itemview.findViewById(R.id.currencykanaam)
    val imageView: ImageView = itemview.findViewById(R.id.likekaro)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_selection,parent,false)
        val viewHolder = CurrencyViewHolder(view)


        viewHolder.imageView.setOnClickListener {
            listener.onLikeClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder:CurrencyViewHolder, position: Int) {
        val currentItem =items[position]
        holder.currencyview.text = currentItem.asset_id
        holder.titleview.text = currentItem.name

        val isLiked = (user.currencycode==currentItem.asset_id)
        if(isLiked) {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(holder.imageView.context, R.drawable.liked))
        } else {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(holder.imageView.context, R.drawable.unliked))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun updatel(updatedlist:ArrayList<Asset>){
        items.clear()
        items.addAll(updatedlist)
        Log.d("CSR","adapter"+updatedlist[0].asset_id)

        notifyDataSetChanged()

    }

}




interface ONLikeClicked{
    fun onLikeClicked(item: Asset)
}