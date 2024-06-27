package com.example.dashboardkeuangan.ui.kerjasama

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class ListAwalKerjasamaAdapter(private val listProduct: ArrayList<DataDetailed>) : RecyclerView.Adapter<ListAwalKerjasamaAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sumber: TextView = itemView.findViewById(R.id.textView)
        val keterangan: TextView = itemView.findViewById(R.id.textView2)
        val tanggal: TextView = itemView.findViewById(R.id.textView3)
        val nominal: TextView = itemView.findViewById(R.id.textView4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kerjasama, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (tahun, sumber, jenis, nominal) = listProduct[position]
        holder.sumber.text = tahun
        holder.keterangan.text = sumber
        holder.tanggal.text = jenis
        holder.nominal.text = nominal
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listProduct[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataDetailed)
    }
}
