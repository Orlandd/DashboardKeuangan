package com.example.dashboardkeuangan.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var yearSpinner: Spinner
    private lateinit var rvPemasukan: RecyclerView
    private lateinit var rvPengeluaran: RecyclerView
    private lateinit var pemasukanAdapter: FinancialReportAdapter
    private lateinit var pengeluaranAdapter: FinancialReportAdapter

    private lateinit var db: FirebaseFirestore

    private lateinit var tvSaldo: TextView
    private lateinit var tvPemasukan: TextView
    private lateinit var tvPengeluaran: TextView

    private var totalPemasukan: Double = 0.0
    private var totalPengeluaran: Double = 0.0

    private var pemasukanList = mutableListOf<FinancialReport>()
    private var pengeluaranList = mutableListOf<FinancialReport>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_financial_report, container, false)

        yearSpinner = view.findViewById(R.id.yearSpinner)
        rvPemasukan = view.findViewById(R.id.rvPemasukan)
        rvPengeluaran = view.findViewById(R.id.rvPengeluaran)
        tvSaldo = view.findViewById(R.id.tvSaldo)
        tvPemasukan = view.findViewById(R.id.tvPemasukan)
        tvPengeluaran = view.findViewById(R.id.tvPengeluaran)

        rvPemasukan.layoutManager = LinearLayoutManager(context)
        rvPengeluaran.layoutManager = LinearLayoutManager(context)

        pemasukanAdapter = FinancialReportAdapter(pemasukanList)
        pengeluaranAdapter = FinancialReportAdapter(pengeluaranList)

        rvPemasukan.adapter = pemasukanAdapter
        rvPengeluaran.adapter = pengeluaranAdapter

        db = FirebaseFirestore.getInstance()

        fetchYears()

        return view
    }

    private fun fetchYears() {
        db.collectionGroup("data")
            .get()
            .addOnSuccessListener { result ->
                val years = result.documents
                    .mapNotNull { document ->
                        val timestamp = document.getTimestamp("tanggal")
                        val date = timestamp?.toDate()
                        date?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
                    }
                    .distinct()
                    .sortedDescending()

                setupYearSpinner(years)
                fetchPemasukanData()
                fetchPengeluaranData()
            }
            .addOnFailureListener { exception ->
                // Handle error fetching years
            }
    }

    private fun setupYearSpinner(years: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = adapter

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = yearSpinner.selectedItem.toString()
                filterDataByYear(selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun fetchPemasukanData() {
        db.collection("keuangan").document("pemasukan").collection("data")
            .get()
            .addOnSuccessListener { result ->
                pemasukanList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val nominalValue = nominal.toDouble()
                    val formattedNominal = formatRupiah(nominalValue)

                    FinancialReport(
                        formattedDate,
                        document.getString("sumber") ?: "",
                        document.getString("jenis") ?: "",
                        formattedNominal,
                        nominalValue
                    )
                }.toMutableList()

                fetchKerjasamaData()
            }
            .addOnFailureListener { exception ->
                // Handle error fetching pemasukan data
            }
    }

    private fun fetchKerjasamaData() {
        db.collection("keuangan").document("kerjasama").collection("data")
            .get()
            .addOnSuccessListener { result ->
                val kerjasamaList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val nominalValue = nominal.toDouble()
                    val formattedNominal = formatRupiah(nominalValue)

                    FinancialReport(
                        formattedDate,
                        document.getString("sumber") ?: "",
                        document.getString("keterangan") ?: "",
                        formattedNominal,
                        nominalValue
                    )
                }

                pemasukanList.addAll(kerjasamaList)
                filterDataByYear(yearSpinner.selectedItem.toString())
            }
            .addOnFailureListener { exception ->
                // Handle error fetching kerjasama data
            }
    }

    private fun fetchPengeluaranData() {
        db.collection("keuangan").document("pengeluaran").collection("data")
            .get()
            .addOnSuccessListener { result ->
                pengeluaranList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val nominalValue = nominal.toDouble()
                    val formattedNominal = formatRupiah(nominalValue)

                    FinancialReport(
                        formattedDate,
                        "",
                        document.getString("jenis") ?: "",
                        formattedNominal,
                        nominalValue
                    )
                }.toMutableList()

                fetchPenelitianData()
            }
            .addOnFailureListener { exception ->
                // Handle error fetching pengeluaran data
            }
    }

    private fun fetchPenelitianData() {
        db.collection("keuangan").document("penelitian").collection("data")
            .get()
            .addOnSuccessListener { result ->
                val penelitianList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val nominalValue = nominal.toDouble()
                    val formattedNominal = formatRupiah(nominalValue)

                    FinancialReport(
                        formattedDate,
                        document.getString("kegiatan") ?: "",
                        document.getString("keterangan") ?: "",
                        formattedNominal,
                        nominalValue
                    )
                }

                pengeluaranList.addAll(penelitianList)
                filterDataByYear(yearSpinner.selectedItem.toString())
            }
            .addOnFailureListener { exception ->
                // Handle error fetching penelitian data
            }
    }

    private fun filterDataByYear(year: String) {
        val filteredPemasukan = pemasukanList.filter { it.tanggal.endsWith(year) }
        pemasukanAdapter.setItems(filteredPemasukan)

        val filteredPengeluaran = pengeluaranList.filter { it.tanggal.endsWith(year) }
        pengeluaranAdapter.setItems(filteredPengeluaran)

        totalPemasukan = filteredPemasukan.sumOf { it.nominal }
        totalPengeluaran = filteredPengeluaran.sumOf { it.nominal }

        updateSaldo()
    }

    private fun updateSaldo() {
        val saldo = totalPemasukan - totalPengeluaran
        tvSaldo.text = formatRupiah(saldo)
        tvPemasukan.text = formatRupiah(totalPemasukan)
        tvPengeluaran.text = formatRupiah(totalPengeluaran)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvPemasukan.adapter = null
        rvPengeluaran.adapter = null
    }

    private fun formatRupiah(nominal: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(nominal).replace("Rp", "Rp ")
    }
}