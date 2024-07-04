package com.example.dashboardkeuangan.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class FinancialReportAdapter(private var reportList: List<FinancialReport>) :
    RecyclerView.Adapter<FinancialReportAdapter.FinancialReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_financial_report, parent, false)
        return FinancialReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinancialReportViewHolder, position: Int) {
        val report = reportList[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int = reportList.size

    fun setItems(items: List<FinancialReport>) {
        reportList = items
        notifyDataSetChanged()
    }

    class FinancialReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvJenis: TextView = itemView.findViewById(R.id.tvJenis)
        private val tvNominal: TextView = itemView.findViewById(R.id.tvNominal)

        fun bind(item: FinancialReport) {
            tvTanggal.text = item.tanggal
            tvCategory.text = item.category
            tvJenis.text = item.jenis
            tvNominal.text = "${item.nominal}"
        }
    }
}
