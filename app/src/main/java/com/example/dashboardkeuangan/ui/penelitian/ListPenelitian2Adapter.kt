package com.example.dashboardkeuangan.ui.penelitian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class ListPenelitian2Adapter (private val listproduct: ArrayList<Data>) : RecyclerView.Adapter<ListPenelitian2Adapter.ListViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tahunKet: TextView = itemView.findViewById(R.id.tahunKet)
        val tahunVal: TextView = itemView.findViewById(R.id.tahunVal)
        val tvTahun: TextView = itemView.findViewById(R.id.tvTahun)
        val card_Nominal_Tahun: CardView = itemView.findViewById(R.id.card_Nominal_Tahun)
        val myimageView: ImageView = itemView.findViewById(R.id.myimageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_awal_penelitian, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listproduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (tahun, jumlahpertahun) = listproduct[position]
        holder.tahunKet.text = "Tahun"
        holder.tahunVal.text = jumlahpertahun.toString()
        holder.tvTahun.text = tahun
        holder.card_Nominal_Tahun.setOnClickListener {
            onItemClickCallback?.onItemClicked(listproduct[holder.adapterPosition])
        }

        // Set background drawable based on position
        val backgrounds = arrayOf(
            R.drawable.background1,
            R.drawable.background2,
            R.drawable.background3
        )
        val backgroundIndex = position % backgrounds.size
        holder.card_Nominal_Tahun.setBackgroundResource(backgrounds[backgroundIndex])

        val icons = arrayOf(
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3
        )
        val iconIndex = position % icons.size
        holder.myimageView.setImageResource(icons[iconIndex])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: com.example.dashboardkeuangan.ui.penelitian.Data)
    }
}