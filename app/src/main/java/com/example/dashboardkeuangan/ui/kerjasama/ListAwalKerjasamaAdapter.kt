package com.example.dashboardkeuangan.ui.kerjasama

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListAwalKerjasamaAdapter(private val list: List<DataDetailed>) : RecyclerView.Adapter<ListAwalKerjasamaAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sumber: TextView = itemView.findViewById(R.id.textView)
        val keterangan: TextView = itemView.findViewById(R.id.textView2)
        val tanggal: TextView = itemView.findViewById(R.id.textView3)
        val nominal: TextView = itemView.findViewById(R.id.textView4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kerjasama, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataDetailed = list[position]
        holder.sumber.text = dataDetailed.sumber
        holder.keterangan.text = dataDetailed.keterangan
        holder.tanggal.text = dataDetailed.tanggal?.toDate()?.let { formatDate(it) } ?: "N/A"
        holder.nominal.text = dataDetailed.nominal
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}
