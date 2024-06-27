package com.example.homelayoutprojectpabbaru.ui.home

// RiwayatPengeluaranAdapter.java
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homelayoutprojectpabbaru.databinding.ItempengeluaranBinding


class RiwayatPengeluaranAdapter(pengeluaranList: List<Pengeluaran>) :
    RecyclerView.Adapter<RiwayatPengeluaranAdapter.ViewHolder>() {
    private val pengeluaranList: List<Pengeluaran>

    init {
        this.pengeluaranList = pengeluaranList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItempengeluaranBinding =
            ItempengeluaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengeluaran: Pengeluaran = pengeluaranList[position]
        holder.binding.tanggal.setText(pengeluaran.tanggal)
        holder.binding.jenisPengeluaran.setText(pengeluaran.jenisPengeluaran)
        holder.binding.nominal.setText(pengeluaran.nominal)
    }

    override fun getItemCount(): Int {
        return pengeluaranList.size
    }

    class ViewHolder(binding: ItempengeluaranBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItempengeluaranBinding

        init {
            this.binding = binding
        }
    }
}

