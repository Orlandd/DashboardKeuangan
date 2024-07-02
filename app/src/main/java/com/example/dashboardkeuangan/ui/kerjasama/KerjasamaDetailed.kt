package com.example.dashboardkeuangan.ui.kerjasama

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class KerjasamaDetailed : AppCompatActivity() {

    private lateinit var datatrend: RecyclerView
    private val list = ArrayList<DataDetailed>()
    private lateinit var db: FirebaseFirestore
    private var selectedYear: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kerjasama_detailed)

        datatrend = findViewById(R.id.KerjasamaItemList)
        datatrend.setHasFixedSize(true)

        db = FirebaseFirestore.getInstance()

        // Get the selected year from the intent
        val product = intent.getParcelableExtra<Data>("DATA")
        if (product != null) {
            val textTahun: TextView = findViewById(R.id.tvTahun)
            val textNominalPertahun: TextView = findViewById(R.id.tvTahunValue)

            textTahun.text = product.tahun
            textNominalPertahun.text = product.jumlahpertahun

            selectedYear = product.tahun
        }

        fetchProductListFromFirestore()
    }

    private fun fetchProductListFromFirestore() {
        db.collection("keuangan").document("kerjasama").collection("data")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val dataDetailed = document.toObject(DataDetailed::class.java)
                    list.add(dataDetailed)
                    Log.d("FirestoreData", "Fetched data: ${dataDetailed}")
                }
                showRecyclerList()
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error getting documents: ", exception)
            }
    }

    private fun showRecyclerList() {
        // Filter the list based on the selected year
        val filteredList = list.filter { dataDetailed ->
            dataDetailed.tanggal?.toDate()?.let { date ->
                val sdf = SimpleDateFormat("yyyy", Locale.getDefault())
                val year = sdf.format(date)
                year == selectedYear
            } ?: false
        }

        datatrend.layoutManager = LinearLayoutManager(this)
        val listProductAwalAdapter = ListAwalKerjasamaAdapter(filteredList)
        datatrend.adapter = listProductAwalAdapter
    }
}
