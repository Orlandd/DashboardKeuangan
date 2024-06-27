package com.example.dashboardkeuangan.ui.kerjasama

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class ListKerjasamaAdapter(private val listProduct: ArrayList<Data>) : RecyclerView.Adapter<ListKerjasamaAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTahunLabel: TextView = itemView.findViewById(R.id.tvTahunLabel)
        val tvTahunValue: TextView = itemView.findViewById(R.id.tvTahunValue)
        val tvTahun: TextView = itemView.findViewById(R.id.tvTahun)
        val cardTahun: CardView = itemView.findViewById(R.id.cardTahun)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_awal_kerjasama, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (tahun, jumlahpertahun) = listProduct[position]
        holder.tvTahunLabel.text = "Tahun" // Adjust if needed
        holder.tvTahunValue.text = jumlahpertahun
        holder.tvTahun.text = tahun// or jumlahpertahun if that's the right data
        holder.cardTahun.setOnClickListener {
            onItemClickCallback?.onItemClicked(listProduct[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Data)
    }
}
