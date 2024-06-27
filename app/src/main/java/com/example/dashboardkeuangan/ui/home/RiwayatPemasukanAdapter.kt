package com.example.homelayoutprojectpabbaru.ui.home

// RiwayatPemasukanAdapter.java
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homelayoutprojectpabbaru.databinding.ItempemasukanBinding


class RiwayatPemasukanAdapter(pemasukanList: List<Pemasukan>) :
    RecyclerView.Adapter<RiwayatPemasukanAdapter.ViewHolder>() {
    private val pemasukanList: List<Pemasukan>

    init {
        this.pemasukanList = pemasukanList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItempemasukanBinding =
            ItempemasukanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pemasukan: Pemasukan = pemasukanList[position]
        holder.binding.tanggal.setText(pemasukan.tanggal)
        holder.binding.jenisPemasukan.setText(pemasukan.jenisPemasukan)
        holder.binding.nominal.setText(pemasukan.nominal)
    }

    override fun getItemCount(): Int {
        return pemasukanList.size
    }

    class ViewHolder(binding: ItempemasukanBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItempemasukanBinding

        init {
            this.binding = binding
        }
    }
}

