package com.example.dashboardkeuangan.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.ItemFinancialBinding




class FinancialAdapter(private val items: List<FinancialItem>) :
    RecyclerView.Adapter<FinancialAdapter.FinancialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialViewHolder {
        val binding = ItemFinancialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinancialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinancialViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class FinancialViewHolder(private val binding: ItemFinancialBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FinancialItem) {
            binding.tvCategory.text = item.category
            binding.tvType.text = item.type
            binding.tvAmount.text = item.amount
        }
    }
}
