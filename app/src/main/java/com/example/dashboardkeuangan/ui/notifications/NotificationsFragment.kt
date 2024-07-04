package com.example.dashboardkeuangan.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var rvPemasukan: RecyclerView
    private lateinit var rvPengeluaran: RecyclerView
    private lateinit var pemasukanAdapter: FinancialReportAdapter
    private lateinit var pengeluaranAdapter: FinancialReportAdapter

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_financial_report, container, false)

        rvPemasukan = view.findViewById(R.id.rvPemasukan)
        rvPengeluaran = view.findViewById(R.id.rvPengeluaran)

        rvPemasukan.layoutManager = LinearLayoutManager(context)
        rvPengeluaran.layoutManager = LinearLayoutManager(context)

        pemasukanAdapter = FinancialReportAdapter(emptyList())
        pengeluaranAdapter = FinancialReportAdapter(emptyList())

        rvPemasukan.adapter = pemasukanAdapter
        rvPengeluaran.adapter = pengeluaranAdapter

        db = FirebaseFirestore.getInstance()

        fetchPemasukanData()
        fetchPengeluaranData()

        return view
    }

    private fun fetchPemasukanData() {
        db.collection("keuangan").document("pemasukan").collection("data")
            .get()
            .addOnSuccessListener { result ->
                val pemasukanList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val formattedNominal = formatRupiah(nominal.toDouble())

                    FinancialReport(
                        formattedDate,
                        document.getString("sumber") ?: "",
                        document.getString("jenis") ?: "",
                        formattedNominal
                    )
                }
                pemasukanAdapter.setItems(pemasukanList)
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    private fun fetchPengeluaranData() {
        db.collection("keuangan").document("pengeluaran").collection("data")
            .get()
            .addOnSuccessListener { result ->
                val pengeluaranList = result.documents.map { document ->
                    val timestamp = document.getTimestamp("tanggal")
                    val date = timestamp?.toDate()
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                    val nominal = document.getString("nominal") ?: "0"
                    val formattedNominal = formatRupiah(nominal.toDouble())

                    FinancialReport(
                        formattedDate,
                        "", // Menyesuaikan dengan data yang ada di Firestore
                        document.getString("jenis") ?: "",
                        formattedNominal
                    )
                }
                pengeluaranAdapter.setItems(pengeluaranList)
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up references to avoid memory leaks
        rvPemasukan.adapter = null
        rvPengeluaran.adapter = null
    }

    private fun formatRupiah(nominal: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(nominal).replace("Rp", "Rp ")
    }
}
