package com.example.dashboardkeuangan.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_financial_report, container, false)

        rvPemasukan = view.findViewById(R.id.rvPemasukan)
        rvPengeluaran = view.findViewById(R.id.rvPengeluaran)
        tvSaldo = view.findViewById(R.id.tvSaldo)
        tvPemasukan = view.findViewById(R.id.tvPemasukan)
        tvPengeluaran = view.findViewById(R.id.tvPengeluaran)

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

                    totalPemasukan += nominal.toDouble()

                    FinancialReport(
                        formattedDate,
                        document.getString("sumber") ?: "",
                        document.getString("jenis") ?: "",
                        formattedNominal
                    )
                }

                // Fetch kerjasama data
                db.collection("keuangan").document("kerjasama").collection("data")
                    .get()
                    .addOnSuccessListener { result ->
                        val kerjasamaList = result.documents.map { document ->
                            val timestamp = document.getTimestamp("tanggal")
                            val date = timestamp?.toDate()
                            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                            val nominal = document.getString("nominal") ?: "0"
                            val formattedNominal = formatRupiah(nominal.toDouble())

                            totalPemasukan += nominal.toDouble()

                            FinancialReport(
                                formattedDate,
                                document.getString("sumber") ?: "",
                                document.getString("keterangan") ?: "",
                                formattedNominal
                            )
                        }

                        // Combine pemasukanList with kerjasamaList
                        val combinedList = mutableListOf<FinancialReport>()
                        combinedList.addAll(pemasukanList)
                        combinedList.addAll(kerjasamaList)

                        pemasukanAdapter.setItems(combinedList)
                        updateSaldo()
                    }
                    .addOnFailureListener { exception ->
                        // Handle error fetching kerjasama data
                    }

            }
            .addOnFailureListener { exception ->
                // Handle error fetching pemasukan data
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

                    totalPengeluaran += nominal.toDouble()

                    FinancialReport(
                        formattedDate,
                        "", // Menyesuaikan dengan data yang ada di Firestore
                        document.getString("jenis") ?: "",
                        formattedNominal
                    )
                }

                // Fetch penelitian data
                db.collection("keuangan").document("penelitian").collection("data")
                    .get()
                    .addOnSuccessListener { result ->
                        val penelitianList = result.documents.map { document ->
                            val timestamp = document.getTimestamp("tanggal")
                            val date = timestamp?.toDate()
                            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

                            val nominal = document.getString("nominal") ?: "0"
                            val formattedNominal = formatRupiah(nominal.toDouble())

                            totalPengeluaran += nominal.toDouble()

                            FinancialReport(
                                formattedDate,
                                document.getString("kegiatan") ?: "",
                                document.getString("keterangan") ?: "",
                                formattedNominal
                            )
                        }

                        // Combine pengeluaranList with penelitianList
                        val combinedList = mutableListOf<FinancialReport>()
                        combinedList.addAll(pengeluaranList)
                        combinedList.addAll(penelitianList)

                        pengeluaranAdapter.setItems(combinedList)
                        updateSaldo()
                    }
                    .addOnFailureListener { exception ->
                        // Handle error fetching penelitian data
                    }

            }
            .addOnFailureListener { exception ->
                // Handle error fetching pengeluaran data
            }
    }


    private fun updateSaldo() {
        val saldo = totalPemasukan - totalPengeluaran
        tvSaldo.text = formatRupiah(saldo)
        tvPemasukan.text = formatRupiah(totalPemasukan)
        tvPengeluaran.text = formatRupiah(totalPengeluaran)
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

