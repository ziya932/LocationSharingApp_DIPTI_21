package com.mrpaul.LocationSharingApp_DIPTI_21.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrpaul.LocationSharingApp_DIPTI_21.Model.User
import com.mrpaul.LocationSharingApp_DIPTI_21.databinding.ItemUserBinding

class UserAdapter(private var userList: List<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.apply {
                displayNameTxt.text = user.displayname
                emailTxt.text = user.email
                locationTxt.text = user.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.bind(user)

    }
    fun updateData(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }
}