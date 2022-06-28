package com.example.bukitasam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.Admin.EditRole
import com.example.bukitasam.Models.UserModel
import com.example.bukitasam.R
import com.example.bukitasam.user.edituser

class AdapterUser (private val dataList: ArrayList<UserModel>): RecyclerView.Adapter<AdapterUser.ViewHolderData>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_layout_user,parent,false)

        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val item=dataList[position]
        holder.nama.text=item.nama
        holder.email.text=item.email
        holder.role.text=item.role



        holder.itemView.setOnClickListener{
            val ctx = holder.context
            val intent = Intent(ctx,EditRole::class.java)
            intent.putExtra("id",item.id.toString())
            intent.putExtra("nama",item.nama.toString())
            intent.putExtra("role",item.role.toString())
            intent.putExtra("role_id",item.role_id.toString())
            ctx.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nama : TextView = itemView.findViewById(R.id.tv_nama)
        val email: TextView = itemView.findViewById(R.id.tv_email)
        val role: TextView = itemView.findViewById(R.id.tv_role)
        val context = itemView.context
    }
}
