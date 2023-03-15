package com.example.githubapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: ArrayList<String>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: List<String>)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto = view.findViewById<ImageView>(R.id.img_item_photo)
        val tvUserName = view.findViewById<TextView>(R.id.tv_item_username)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserAdapter.ViewHolder = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: UserAdapter.ViewHolder, position: Int) {
        //memisahkan antara avatar photo dan username karena listUser berbentuk "${user.avatarUrl};${user.login};${user.name}"
        val arrayUser = listUser[position].split(";")
        Glide.with(viewHolder.itemView.context)
            .load(arrayUser[0])
            .into(viewHolder.imgPhoto)
        viewHolder.tvUserName.text = arrayUser[1]
        viewHolder.itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(arrayUser) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}