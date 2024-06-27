package com.example.dashboardkeuangan.ui.kerjasama

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R


class KerjasamaDetailed : AppCompatActivity() {

    private lateinit var datatrend: RecyclerView
    private val list = ArrayList<DataDetailed>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kerjasama_detailed)

        datatrend = findViewById(R.id.KerjasamaItemList)
        datatrend.setHasFixedSize(true)

        list.addAll(getProductList())
        showRecyclerList()
        val product = intent.getParcelableExtra<Data>("DATA")
        if (product != null) {
            val textTahun: TextView = findViewById(R.id.tvTahun)
            val textNominalPertahun: TextView = findViewById(R.id.tvTahunValue)

            textTahun.text = product.tahun
            textNominalPertahun.text = product.jumlahpertahun
        }
    }

    private fun getProductList(): ArrayList<DataDetailed> {
        val productSumber = resources.getStringArray(R.array.data_sumber)
        val productKeterangan = resources.getStringArray(R.array.data_keterangan)
        val productTanggal = resources.getStringArray(R.array.data_tanggal)
        val productNominal = resources.getStringArray(R.array.data_nominal)
        val listmasuk = ArrayList<DataDetailed>()
        for (i in productTanggal.indices) {
            val masuk = DataDetailed(productSumber[i], productKeterangan[i], productTanggal[i], productNominal[i])
            listmasuk.add(masuk)
        }
        return listmasuk
    }


    private fun showRecyclerList() {
        datatrend.layoutManager = LinearLayoutManager(this)
        val listProductAwalAdapter = ListAwalKerjasamaAdapter(list)
        datatrend.adapter = listProductAwalAdapter
    }
}
