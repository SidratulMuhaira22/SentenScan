package com.hera.sentenscan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hera.sentenscan.R
import com.hera.sentenscan.data.api.model.User

interface OnUserItemClickListener {
    fun onUserItemClick(userName: String)
}

class UserAdapter(
    private var userList: List<User>,
    private var onUserItemClickListener: OnUserItemClickListener

) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var selectedUserName: String? = null

    fun setSelectedUserName(userName: String?) {
        selectedUserName = userName
        notifyDataSetChanged()
    }

    fun updateData(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.fullNameTextView.text = "${user.firstName} ${user.lastName}"
        holder.emailTextView.text = user.email

        Glide.with(holder.itemView)
            .load(user.avatar)
            .circleCrop()
            .into(holder.avatarImageView)

        holder.itemView.setOnClickListener {
            onUserItemClickListener.onUserItemClick("${user.firstName} ${user.lastName}")
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.user_img)
        val fullNameTextView: TextView = itemView.findViewById(R.id.tv_fullname)
        val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
    }
}

