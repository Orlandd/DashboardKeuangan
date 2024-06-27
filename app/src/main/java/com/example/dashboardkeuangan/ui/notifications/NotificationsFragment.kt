package com.example.dashboardkeuangan.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R

class NotificationsFragment : Fragment() {

    private lateinit var rvFinancialReport: RecyclerView
    private lateinit var financialReportAdapter: FinancialReportAdapter
    private val reportList = listOf(
        FinancialReport("01-01-2023", "Pemasukan", "Gaji", 10),
        FinancialReport("02-01-2023", "Pengeluaran", "Makanan", 5),
        // Add more sample data here
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_financial_report, container, false)

        rvFinancialReport = view.findViewById(R.id.rvFinancialReport)
        rvFinancialReport.layoutManager = LinearLayoutManager(context)
        financialReportAdapter = FinancialReportAdapter(reportList)
        rvFinancialReport.adapter = financialReportAdapter

        return view
    }
}
