package com.example.dashboardkeuangan.ui.penelitian

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R
import com.example.dashboardkeuangan.ui.penelitian.Data
import com.example.dashboardkeuangan.ui.penelitian.PenelitianDetailed
import com.example.dashboardkeuangan.ui.penelitian.ListPenelitian2Adapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.text.SimpleDateFormat
import java.util.Locale

class PenelitianFragment : Fragment() {

    private lateinit var mydata: RecyclerView
    private val list = ArrayList<Data>()
    private lateinit var tahunVal: TextView
    private lateinit var tvIsiPenelitian: TextView
//    private lateinit var gambar: ImageView
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?)
    : View {
        return inflater.inflate(R.layout.fragment_penelitian, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mydata = view.findViewById(R.id.PenelitianItemList1)
        mydata.setHasFixedSize(true)

        tvIsiPenelitian = view.findViewById(R.id.tvIsiPenelitian)
        tahunVal = view.findViewById(R.id.tahunVal)
//        gambar = view.findViewById(R.id.gambar)

        db = FirebaseFirestore.getInstance()
        getProductList()
    }

    private fun getProductList(){
        db.collection("keuangan/penelitian/data")
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

                for ((year, totalAmountPerYear) in groupedData) {
                    list.add(Data(year, totalAmountPerYear))
                }

                tahunVal.text = totalAmount.toString()
                tvIsiPenelitian.text = totalEntries.toString()

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
            val nominal = getString("nominal")?.toIntOrNull()?: 0
            Data(year, nominal)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showRecyclerList() {
        mydata.layoutManager = LinearLayoutManager(context)
        val listmyPemasukanAdapter = ListPenelitian2Adapter(list)
        mydata.adapter = listmyPemasukanAdapter

        listmyPemasukanAdapter.setOnItemClickCallback(object : ListPenelitian2Adapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {
                showSelectedData(data)
            }
        })
    }

    private fun showSelectedData(data: Data) {
        val intent = Intent(context, PenelitianDetailed::class.java)
        intent.putExtra("DATA", data)
        Toast.makeText(context, data.tahun + " is selected", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}