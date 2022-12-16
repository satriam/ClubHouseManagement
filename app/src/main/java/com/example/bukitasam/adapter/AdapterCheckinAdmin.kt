package com.example.bukitasam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.bukitasam.Admin.HistoryDataActivity
import com.example.bukitasam.Models.data_checkin_admin
import com.example.bukitasam.R

class AdapterCheckinAdmin (private val dataList: ArrayList<data_checkin_admin>): RecyclerView.Adapter<AdapterCheckinAdmin.ViewHolderData>() {
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

        Glide.with(holder.context).load(item.gambar)
            .transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions())
            .placeholder(R.color.colorPrimaryDark)
            .into(holder.gambar)
        holder.itemView.setOnClickListener{
            val ctx = holder.context
            val intent = Intent(ctx, HistoryDataActivity::class.java)
            val a :String= item.gambar

            intent.putExtra("id",item.id.toString())
            intent.putExtra("tanggal",item.created_at.toString())
            intent.putExtra("lokasi",item.nama_lokasi.toString())
            intent.putExtra("nama",item.nama.toString())

            intent.putExtra("gambar",a)
            ctx.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tanggal : TextView = itemView.findViewById(R.id.tv_tanggal)
        val nama: TextView = itemView.findViewById(R.id.tv_lokasi)

        val namauser:TextView=itemView.findViewById(R.id.tv_nama)
        val gambar: ImageView =itemView.findViewById(R.id.tv_img)
        val context = itemView.context
    }
}