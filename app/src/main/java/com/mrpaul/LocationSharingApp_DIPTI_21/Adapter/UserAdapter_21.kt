package com.mrpaul.LocationSharingApp_DIPTI_21.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrpaul.LocationSharingApp_DIPTI_21.Model.User_21
import com.mrpaul.LocationSharingApp_DIPTI_21.databinding.ItemUserBinding

class UserAdapter_21(private var user21List: List<User_21>): RecyclerView.Adapter<UserAdapter_21.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user21: User_21){
            binding.apply {
                displayNameTxt.text = user21.displayname
                emailTxt.text = user21.email
                locationTxt.text = user21.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return user21List.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = user21List[position]

        holder.bind(user)

    }
    fun updateData(newList: List<User_21>) {
        user21List = newList
        notifyDataSetChanged()
    }
}