package com.example.dashboardkeuangan.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class FinancialReportAdapter(private val reportList: List<FinancialReport>) :
    RecyclerView.Adapter<FinancialReportAdapter.FinancialReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_financial_report, parent, false)
        return FinancialReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinancialReportViewHolder, position: Int) {
        val report = reportList[position]
        holder.tvTanggal.text = report.tanggal
        holder.tvCategory.text = report.category
        holder.tvJenis.text = report.jenis
        holder.tvNominal.text = "${report.nominal} juta"
    }

    override fun getItemCount() = reportList.size

    class FinancialReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvJenis: TextView = itemView.findViewById(R.id.tvJenis)
        val tvNominal: TextView = itemView.findViewById(R.id.tvNominal)
    }
}
