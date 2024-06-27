package com.example.dashboardkeuangan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardkeuangan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var pengeluaranAdapter: RiwayatPengeluaranAdapter? = null
    private var pemasukanAdapter: RiwayatPemasukanAdapter? = null

    private val pengeluaranList: MutableList<Pengeluaran> = ArrayList()
    private val pemasukanList: MutableList<Pemasukan> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupSampleData()
    }

    private fun setupRecyclerViews() {
        // Setup RecyclerView untuk riwayat pengeluaran
        binding.recyclerViewRiwayatPengeluaran.layoutManager = LinearLayoutManager(context)
        pengeluaranAdapter = RiwayatPengeluaranAdapter(pengeluaranList)
        binding.recyclerViewRiwayatPengeluaran.adapter = pengeluaranAdapter

        // Setup RecyclerView untuk riwayat pemasukan
        binding.recyclerViewRiwayatPemasukan.layoutManager = LinearLayoutManager(context)
        pemasukanAdapter = RiwayatPemasukanAdapter(pemasukanList)
        binding.recyclerViewRiwayatPemasukan.adapter = pemasukanAdapter
    }

    private fun setupSampleData() {
        // Menambahkan contoh data untuk pengeluaran
        pengeluaranList.clear()
        pengeluaranList.add(Pengeluaran("01/01/2024", "Pembelian Buku", "150,000"))
        // Menambahkan contoh data untuk pemasukan
        pemasukanList.clear()
        pemasukanList.add(Pemasukan("01/01/2024", "Gaji", "5,000,000"))
        pemasukanList.add(Pemasukan("01/01/2024", "Gaji", "5,000,000"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}