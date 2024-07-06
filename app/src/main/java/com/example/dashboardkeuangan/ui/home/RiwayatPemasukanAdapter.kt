package com.example.dashboardkeuangan.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.ItempemasukanBinding

class RiwayatPemasukanAdapter(private val pemasukanList: List<Pemasukan>) :
    RecyclerView.Adapter<RiwayatPemasukanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItempemasukanBinding =
            ItempemasukanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pemasukan: Pemasukan = pemasukanList[position]
        holder.binding.tanggal.text = pemasukan.tanggal
        holder.binding.jenisPemasukan.text = pemasukan.jenisPemasukan
        holder.binding.nominal.text = pemasukan.nominal
    }

    override fun getItemCount(): Int {
        return pemasukanList.size
    }

    class ViewHolder(val binding: ItempemasukanBinding) : RecyclerView.ViewHolder(binding.root)
}
