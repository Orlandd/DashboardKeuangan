package com.example.dashboardkeuangan.ui.kerjasama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.text.SimpleDateFormat
import java.util.Locale

class KerjasamaFragment : Fragment() {

    private lateinit var datatrend: RecyclerView
    private val list = ArrayList<Data>()
    private lateinit var db: FirebaseFirestore
    private lateinit var tvTahunValue: TextView
    private lateinit var tvIsiKerjasama: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kerjasama, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datatrend = view.findViewById(R.id.KerjasamaItemListAwal)
        datatrend.setHasFixedSize(true)

        tvTahunValue = view.findViewById(R.id.tvTahunValue)
        tvIsiKerjasama = view.findViewById(R.id.tvIsiKerjasama)

        db = FirebaseFirestore.getInstance()

        getProductList()
    }

    private fun getProductList() {
        db.collection("keuangan/kerjasama/data")
            .get()
            .addOnSuccessListener { documents ->
                list.clear()
                val groupedData = mutableMapOf<String, Int>()
                var totalAmount = 0
                var totalEntries = 0

                for (document in documents) {
                    val data = document.toData()
                    if (data != null) {
                        val year = data.tahun
                        val amount = data.jumlahpertahun.toInt()
                        groupedData[year] = groupedData.getOrDefault(year, 0) + amount
                        totalAmount += amount
                        totalEntries++
                    }
                }

                // Convert grouped data to list
                for ((year, totalAmountPerYear) in groupedData) {
                    list.add(Data(year, totalAmountPerYear.toString()))
                }

                // Update the TextViews with the overall totals
                tvTahunValue.text = totalAmount.toString()
                tvIsiKerjasama.text = totalEntries.toString()

                showRecyclerList()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error getting documents: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun QueryDocumentSnapshot.toData(): Data? {
        return try {
            val timestamp = getTimestamp("tanggal")
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val year = timestamp?.toDate()?.let { yearFormat.format(it) } ?: ""
            val nominal = getString("nominal") ?: ""
            Data(year, nominal)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showRecyclerList() {
        datatrend.layoutManager = LinearLayoutManager(context)
        val listPemasukanAdapter = ListKerjasamaAdapter(list)
        datatrend.adapter = listPemasukanAdapter

        listPemasukanAdapter.setOnItemClickCallback(object : ListKerjasamaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {
                showSelectedData(data)
            }
        })
    }

    private fun showSelectedData(data: Data) {
        val intent = Intent(context, KerjasamaDetailed::class.java)
        intent.putExtra("DATA", data)
        Toast.makeText(context, data.tahun + " is selected", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}
