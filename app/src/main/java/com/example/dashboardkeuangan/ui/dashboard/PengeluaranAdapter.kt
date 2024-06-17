package com.example.dashboardkeuangan.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.RowData2Binding

class PengeluaranAdapter (private val context: Context, private val listData: ArrayList<Pengeluaran>) : RecyclerView.Adapter<PengeluaranAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {


        val binding = RowData2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val (tahun, jenis, nominal) = listData[position]

        holder.binding.year.text = tahun
        holder.binding.jenis.text = jenis
        holder.binding.nominal.text = nominal

    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    class DataViewHolder(val binding: RowData2Binding) : RecyclerView.ViewHolder(binding.root)

}