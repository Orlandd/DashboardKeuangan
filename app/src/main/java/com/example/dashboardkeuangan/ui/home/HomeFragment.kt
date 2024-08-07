package com.example.dashboardkeuangan.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardkeuangan.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var pengeluaranAdapter: RiwayatPengeluaranAdapter? = null
    private var pemasukanAdapter: RiwayatPemasukanAdapter? = null

    private val pengeluaranList: MutableList<Pengeluaran> = ArrayList()
    private val pemasukanList: MutableList<Pemasukan> = ArrayList()

    private lateinit var db: FirebaseFirestore

    private var totalPemasukan: Double = 0.0
    private var totalPengeluaran: Double = 0.0
    private var totalSaldo: Double = 0.0

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
        db = FirebaseFirestore.getInstance()
        fetchPemasukanData()
        fetchPengeluaranData()
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

    private fun fetchPemasukanData() {
        db.collection("keuangan").document("pemasukan").collection("data")
            .get()
            .addOnSuccessListener { result ->
                Log.d("fetchPemasukanData", "Pemasukan data fetched successfully")
                pemasukanList.clear()
                totalPemasukan = 0.0
                for (document in result) {
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = date?.let {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                    } ?: "Unknown Date"

                    val nominal = document.getString("nominal")?.toDoubleOrNull() ?: 0.0
                    totalPemasukan += nominal

                    pemasukanList.add(
                        Pemasukan(
                            formattedDate,
                            document.getString("jenis") ?: "Unknown",
                            formatRupiah(nominal)
                        )
                    )

//                    Log.d("fetchPemasukanData", "Added pemasukan: $formattedDate, $nominal")
                }

//                Log.d("fetchPemasukanData", "Total Pemasukan: $totalPemasukan")

                // Fetch data from "kerjasama" collection
                db.collection("keuangan").document("kerjasama").collection("data")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val timestamp = document.getTimestamp("tanggal")
                            val date = timestamp?.toDate()
                            val formattedDate = date?.let {
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                            } ?: "Unknown Date"

                            val nominal = document.getString("nominal")?.toDoubleOrNull() ?: 0.0
                            totalPemasukan += nominal

                            pemasukanList.add(
                                Pemasukan(
                                    formattedDate, "Kerja Sama",
                                    formatRupiah(nominal)
                                )
                            )

//                            Log.d("fetchPemasukanData", "Added kerjasama: $formattedDate, $nominal")
                        }

//                        Log.d("fetchPemasukanData", "Total Pemasukan after Kerja Sama: $totalPemasukan")

                        updateSaldoPemasukanPengeluaran()
                        pemasukanAdapter?.notifyDataSetChanged()
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure for "kerjasama" collection
                        Log.e("Firestore", "Error getting documents: ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                // Handle failure for "pemasukan" collection
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }

    private fun fetchPengeluaranData() {
        db.collection("keuangan").document("pengeluaran").collection("data")
            .get()
            .addOnSuccessListener { result ->
                Log.d("fetchPengeluaranData", "Pengeluaran data fetched successfully")
                pengeluaranList.clear()
                totalPengeluaran = 0.0
                for (document in result) {
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal")?.toDoubleOrNull() ?: 0.0
                    totalPengeluaran += nominal

                    pengeluaranList.add(
                        Pengeluaran(
                            formattedDate,
                            document.getString("jenis") ?: "",
                            formatRupiah(nominal)
                        )
                    )

//                    Log.d("fetchPengeluaranData", "Added pengeluaran: $formattedDate, $nominal")
                }

//                Log.d("fetchPengeluaranData", "Total Pengeluaran: $totalPengeluaran")

                db.collection("keuangan").document("penelitian").collection("data")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val timestamp = document.getTimestamp("tanggal")
                            val date = timestamp?.toDate()
                            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                            val nominal = document.getString("nominal")?.toDoubleOrNull() ?: 0.0
                            totalPengeluaran += nominal

                            pengeluaranList.add(
                                Pengeluaran(
                                    formattedDate,
                                    document.getString("kegiatan") ?: "",
                                    formatRupiah(nominal)
                                )
                            )

//                            Log.d("fetchPengeluaranData", "Added penelitian: $formattedDate, $nominal")
                        }

//                        Log.d("fetchPengeluaranData", "Total Pengeluaran after Penelitian: $totalPengeluaran")

                        updateSaldoPemasukanPengeluaran()
                        pengeluaranAdapter?.notifyDataSetChanged()
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure
                        Log.e("Firestore", "Error getting documents: ", exception)
                    }
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }

    private fun updateSaldoPemasukanPengeluaran() {
        totalSaldo = totalPemasukan - totalPengeluaran
        Log.d("UpdateSaldo", "Total Pemasukan: $totalPemasukan, Total Pengeluaran: $totalPengeluaran, Total Saldo: $totalSaldo")
        binding.saldo.text = "Saldo\n" + formatRupiah(totalSaldo)
        binding.pemasukan.text = "Pemasukan\n" + formatRupiah(totalPemasukan)
        binding.pengeluaran.text = "Pengeluaran\n" + formatRupiah(totalPengeluaran)
    }


    private fun formatRupiah(nominal: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(nominal).replace("Rp", "Rp ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
