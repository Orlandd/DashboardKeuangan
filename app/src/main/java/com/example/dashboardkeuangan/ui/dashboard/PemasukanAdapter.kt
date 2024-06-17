package com.example.dashboardkeuangan.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.RowDataBinding

class PemasukanAdapter(private val context: Context, private val listData: ArrayList<Pemasukan>) : RecyclerView.Adapter<PemasukanAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {


        val binding = RowDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val (tahun, sumber, jenis, nominal) = listData[position]

        holder.binding.year.text = tahun
        holder.binding.sumber.text = sumber
        holder.binding.tipe.text = jenis
        holder.binding.nominal.text = nominal

    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    class DataViewHolder(val binding: RowDataBinding) : RecyclerView.ViewHolder(binding.root)

}