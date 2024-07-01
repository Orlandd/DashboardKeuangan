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

    private lateinit var rvPemasukan: RecyclerView
    private lateinit var rvPengeluaran: RecyclerView
    private lateinit var pemasukanAdapter: FinancialReportAdapter
    private lateinit var pengeluaranAdapter: FinancialReportAdapter

    private val pemasukanList = listOf(
        FinancialReport("01-01-2023", "Pemasukan", "Gaji", 10),
        // Add more sample pemasukan data here
    )

    private val pengeluaranList = listOf(
        FinancialReport("02-01-2023", "Pengeluaran", "Makanan", 5),
        // Add more sample pengeluaran data here
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_financial_report, container, false)

        rvPemasukan = view.findViewById(R.id.rvPemasukan)
        rvPemasukan.layoutManager = LinearLayoutManager(context)
        pemasukanAdapter = FinancialReportAdapter(pemasukanList)
        rvPemasukan.adapter = pemasukanAdapter

        rvPengeluaran = view.findViewById(R.id.rvPengeluaran)
        rvPengeluaran.layoutManager = LinearLayoutManager(context)
        pengeluaranAdapter = FinancialReportAdapter(pengeluaranList)
        rvPengeluaran.adapter = pengeluaranAdapter

        return view
    }
}
