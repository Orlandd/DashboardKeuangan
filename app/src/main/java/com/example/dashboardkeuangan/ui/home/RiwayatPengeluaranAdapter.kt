package com.example.dashboardkeuangan.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.ItempengeluaranBinding

class RiwayatPengeluaranAdapter(private val pengeluaranList: List<Pengeluaran>) :
    RecyclerView.Adapter<RiwayatPengeluaranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItempengeluaranBinding =
            ItempengeluaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengeluaran: Pengeluaran = pengeluaranList[position]
        holder.binding.tanggal.text = pengeluaran.tanggal
        holder.binding.jenisPengeluaran.text = pengeluaran.jenisPengeluaran
        holder.binding.nominal.text = pengeluaran.nominal
    }

    override fun getItemCount(): Int {
        return pengeluaranList.size
    }

    class ViewHolder(val binding: ItempengeluaranBinding) : RecyclerView.ViewHolder(binding.root)
}
