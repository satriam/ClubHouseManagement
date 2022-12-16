package com.example.bukitasam.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
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
        holder.lokasi.text=item.nama_lokasi
        holder.namauser.text=item.nama
        Glide.with(holder.context).load(item.gambar)
            .transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions())
            .placeholder(R.color.colorPrimaryDark)
            .into(holder.gambar)
        Log.d("data",item.toString())


    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View): RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val tanggal : TextView = itemView.findViewById(R.id.tv_tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val gambar: ImageView=itemView.findViewById(R.id.tv_img)
        val namauser :TextView=itemView.findViewById(R.id.tv_nama)
    }
}