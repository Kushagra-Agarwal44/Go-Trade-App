package com.kushagrawithmdg.gotrade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Models.User

class CoinSelectingAdapter( private val listener: LikeClicked, private val user: User = User()): RecyclerView.Adapter<CoinSelectingAdapter.CoinViewHolder>() {
    private val items: ArrayList<Asset> = ArrayList()


    class CoinViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview){
        val nameview : TextView = itemview.findViewById(R.id.postTitle)
        val imageView: ImageView =itemview.findViewById(R.id.likekaro)
        val idview: TextView = itemview.findViewById(R.id.currencykanaam)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_selection,parent,false)
        val viewHolder = CoinViewHolder(view)
        viewHolder.imageView.setOnClickListener {
            listener.onLikeClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder:CoinViewHolder, position: Int) {
        val currentItem =items[position]
        holder.nameview.text = currentItem.name
        holder.idview.text = currentItem.asset_id
        val isLiked = user.likedcrypto.contains(currentItem.asset_id)
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
        notifyDataSetChanged()

    }

}




interface LikeClicked{
    fun onLikeClicked(item: Asset)
}