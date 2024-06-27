package com.example.dashboardkeuangan.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardkeuangan.databinding.FragmentNotificationsBinding

class MainFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var pemasukanAdapter: FinancialAdapter
    private lateinit var pengeluaranAdapter: FinancialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pemasukanAdapter = FinancialAdapter(getDummyData())
        pengeluaranAdapter = FinancialAdapter(getDummyData())

        binding.rvPemasukan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPemasukan.adapter = pemasukanAdapter

        binding.rvPengeluaran.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPengeluaran.adapter = pengeluaranAdapter
    }

    private fun getDummyData(): List<FinancialItem> {
        return listOf(
            FinancialItem("Part Time", "Pemasukan", "Rp.1,500,000"),
            FinancialItem("Part Time", "Pengeluaran", "Rp.1,500,000")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
