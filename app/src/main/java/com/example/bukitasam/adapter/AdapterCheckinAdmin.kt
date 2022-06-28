package com.example.bukitasam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.Admin.HistoryDataActivity
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R

class AdapterCheckinAdmin (private val dataList: ArrayList<data_checkin>): RecyclerView.Adapter<AdapterCheckinAdmin.ViewHolderData>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_layout_admin,parent,false)

        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val item=dataList[position]
        holder.tanggal.text=item.created_at
        holder.nama.text=item.nama_lokasi
        holder.namauser.text=item.nama

        holder.itemView.setOnClickListener{
            val ctx = holder.context
            val intent = Intent(ctx, HistoryDataActivity::class.java)
            intent.putExtra("id",item.id.toString())
            intent.putExtra("tanggal",item.created_at.toString())
            intent.putExtra("lokasi",item.nama_lokasi.toString())
            intent.putExtra("nama",item.nama.toString())
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tanggal : TextView = itemView.findViewById(R.id.tv_tanggal)
        val nama: TextView = itemView.findViewById(R.id.tv_lokasi)
        val namauser:TextView=itemView.findViewById(R.id.tv_nama)
        val context = itemView.context
    }
}