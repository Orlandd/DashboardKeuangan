package com.example.dashboardkeuangan.ui.penelitian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.example.dashboardkeuangan.ui.penelitian.ListPenelitian1Adapter.ViewHolder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListPenelitian1Adapter(private val list: List<DataDetailed>) : RecyclerView.Adapter<ListPenelitian1Adapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kegiatan: TextView = itemView.findViewById(R.id.mytextView)
        val divisi: TextView = itemView.findViewById(R.id.mytextView1)
        val tanggal: TextView = itemView.findViewById(R.id.mytextView2)
        val status: TextView = itemView.findViewById(R.id.mytextView3)
        val nominal: TextView = itemView.findViewById(R.id.mytextView4)
        val keterangan: TextView = itemView.findViewById(R.id.mytextView5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.dashboardkeuangan.ui.penelitian.ListPenelitian1Adapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_penelitian, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: com.example.dashboardkeuangan.ui.penelitian.ListPenelitian1Adapter.ViewHolder, position: Int) {
        val dataDetailed = list[position]
        holder.kegiatan.text = dataDetailed.kegiatan
        holder.divisi.text = dataDetailed.divisi
        holder.tanggal.text = dataDetailed.tanggal?.toDate()?.let { formatDate(it) } ?: "N/A"
        holder.status.text = dataDetailed.status
        holder.nominal.text = dataDetailed.nominal
        holder.keterangan.text = dataDetailed.keterangan
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}