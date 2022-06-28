package com.example.bukitasam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R

class AdapterCheckin (private val dataList: ArrayList<data_checkin>): RecyclerView.Adapter<AdapterCheckin.ViewHolderData>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_layout,parent,false)

        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val item=dataList[position]
        holder.tanggal.text=item.created_at
        holder.nama.text=item.nama_lokasi
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tanggal : TextView = itemView.findViewById(R.id.tv_tanggal)
        val nama: TextView = itemView.findViewById(R.id.tv_lokasi)
    }
}